package com.any.zhangjunjie.service.impl;

import com.any.zhangjunjie.dao.UserDao;
import com.any.zhangjunjie.dao.imple.UserDaoimpl;
import com.any.zhangjunjie.entity.Result;
import com.any.zhangjunjie.entity.User;
import com.any.zhangjunjie.service.UserService;
import com.any.zhangjunjie.utils.JwtUtils;
import com.any.zhangjunjie.utils.RedisUtil;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import redis.clients.jedis.Jedis;

import java.io.IOException;

public class UserServiceimpl implements UserService {
    UserDao userDao = new UserDaoimpl();

    public Result changePassword(HttpServletRequest req, HttpServletResponse rep) throws IOException {
        String username = req.getParameter("username");
        String newPassword = req.getParameter("Password");
        User usr = userDao.getUserByUsername(username);
        String old = usr.getPassword();
        if (old.equals(newPassword)) {
            return Result.fail("新旧密码相同");
        } else {
            return Result.ok("修改成功");
        }

    }

    @Override//注册
    public void register(HttpServletRequest req, HttpServletResponse rep) throws IOException {
         String username = req.getParameter("username");
         String password = req.getParameter("password");
         String type= req.getParameter("type");
         if (userDao.getUserByUsername(username) == null) {
             User user = new User(username, password, type);
             if (userDao.saveUser(user)) {
                 rep.sendRedirect("/loginSuccess.html");
             } else {
                 rep.setStatus(500);
             }
         }else{
             rep.sendRedirect("/loginFail.html");
         }
    }

    @Override
    public Result login(HttpServletRequest req, HttpServletResponse rep) {
        //获取用户密码，校验，存入Redis
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = userDao.getUserByUsername(username);

        try (Jedis jedis = new Jedis("127.0.0.1", 6379)) {  // 自动关闭连接
            if (user != null && user.getPassword().equals(password)) {
                String jwt = JwtUtils.generateToken(username);

                // 存储到Redis（有效期7天）
                jedis.setex("jwt:" + username, 7 * 24 * 60 * 60, jwt);  // 单位：秒
                rep.addHeader("Authorization", "Bearer " + jwt);
                if(user.getType().equals("yes")){
                    rep.sendRedirect("/admin.html");
                    return Result.ok("管理员登录成功");
                }
                rep.sendRedirect("/custom.html");
                return Result.ok("登录成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("系统异常");
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
                    rep.sendRedirect("/login.html");
                    return Result.ok("登出成功");
                }
                return Result.fail("系统错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("系统异常：" + e.getMessage());
        }
    }
}