package com.any.zhangjunjie.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.any.zhangjunjie.dao.OrderDao;
import com.any.zhangjunjie.dao.UserDao;
import com.any.zhangjunjie.dao.imple.OrderDaoimpl;
import com.any.zhangjunjie.dao.imple.UserDaoimpl;
import com.any.zhangjunjie.entity.Movie;
import com.any.zhangjunjie.entity.Order;
import com.any.zhangjunjie.entity.Result;
import com.any.zhangjunjie.entity.User;
import com.any.zhangjunjie.service.UserService;
import com.any.zhangjunjie.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class UserServiceimpl implements UserService {
    UserDao userDao = new UserDaoimpl();
    OrderDao orderDao = new OrderDaoimpl();
    public Result changePassword(HttpServletRequest req, HttpServletResponse rep) throws IOException {
        String username = req.getParameter("username");
        String newPassword = req.getParameter("Password");
        newPassword=BCrypt.hashpw(newPassword);
        User usr = userDao.getUserByUsername(username);
        String old = usr.getPassword();
        if (BCrypt.checkpw(newPassword, old)) {
            return Result.fail("新旧密码相同");
        } else {
            userDao.changePassword(usr.getUserId(), newPassword);
            return Result.ok("修改成功");
        }

    }

    @Override//注册
    public void register(HttpServletRequest req, HttpServletResponse rep) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String type = req.getParameter("type");
        rep.setContentType("text/json");
        PrintWriter writer = rep.getWriter();
        if (userDao.getUserByUsername(username) == null) {
            User user = new User(username, BCrypt.hashpw(password), type);
            if (userDao.saveUser(user)) {
                rep.setStatus(HttpServletResponse.SC_OK); // 200
                writer.write("注册成功");
            }else {
                rep.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
                writer.write("数据库错误");
            }
        } else {
            rep.setStatus(HttpServletResponse.SC_CONFLICT); // 409
            writer.write("用户名已存在");
        }
    }

    @Override
    public Result login(HttpServletRequest req, HttpServletResponse rep) throws IOException {
        //获取用户密码，校验，存入Redis
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = userDao.getUserByUsername(username);
        if(user.getFlag()==1){
            return Result.fail("该用户已被屏蔽");
        }
        String userpassword=user.getPassword();
        if (user != null && BCrypt.checkpw(password, userpassword)) {
            String jwt = JwtUtils.generateToken(username);
            System.out.println(jwt);

            rep.addHeader("Authorization", "Bearer " + jwt);
            if (user.getType().equals("yes")) {
//                rep.sendRedirect("/admin.html");
                return Result.ok("管理员登录成功");
            }
//            rep.sendRedirect("/user.html");
            return Result.ok("登录成功"+jwt);
        }
        return user==null?Result.fail("用户名不存在"):Result.fail("密码错误");
}

    @Override
    public Result logout(HttpServletRequest req, HttpServletResponse rep) {
        String authHeader = req.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Result.fail("无效的认证信息");
        }
        String jwt = authHeader.substring(7); // 去掉"Bearer "前缀
        try {
            String username =  JwtUtils.parseToken(jwt).getSubject();
            try (Jedis jedis = new Jedis("127.0.0.1", 6379)) {
                Long delResult = jedis.del("jwt:" + username);
                if (delResult == 1) {
//                    rep.sendRedirect("/login.html");
                    return Result.ok("登出成功");
                }
                return Result.fail("系统错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("系统异常：" + e.getMessage());
        }
    }
    @Override
    public Result message(HttpServletRequest req, HttpServletResponse rep) {
        int userId=Integer.parseInt(req.getParameter("userId"));
        List<Order> history=orderDao.history(userId);
        LocalDateTime now=LocalDateTime.now();
        for (Order order : history) {
            Movie movie=orderDao.getMovieById(order.getMovieId());
            LocalDateTime beginTime=LocalDateTime.parse(movie.getBeginTime());
            LocalDateTime endTime=LocalDateTime.parse(movie.getEndTime());
            LocalDateTime tenMinutesBefore = beginTime.minusMinutes(10);

            if (now.isAfter(tenMinutesBefore) && now.isBefore(endTime)) {
                if (now.isBefore(beginTime)) {
                    // 开始前10分钟内
                    long minutesLeft = Duration.between(now, beginTime).toMinutes();
                    return Result.ok(movie.getMovieId() + "号电影将在" + minutesLeft + "分钟后开始");
                } else {
                    // 放映期间
                    long remainingMinutes = Duration.between(now, endTime).toMinutes();
                    return Result.ok(movie.getMovieId() + "号电影进行中，剩余" + remainingMinutes + "分钟");
                }
            }
        }
        return Result.ok();
    }

    @Override
    public List<Order> history(HttpServletRequest req, HttpServletResponse rep) {
        List<Order>lists=orderDao.history(Integer.parseInt(req.getParameter("userId")));
        return lists;
    }
}