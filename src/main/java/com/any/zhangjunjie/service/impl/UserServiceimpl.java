package com.any.zhangjunjie.service.impl;

import com.any.zhangjunjie.dao.UserDao;
import com.any.zhangjunjie.dao.imple.UserDaoimpl;
import com.any.zhangjunjie.entity.Result;
import com.any.zhangjunjie.entity.User;
import com.any.zhangjunjie.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UserServiceimpl implements UserService {
    UserDao userDao = new UserDaoimpl();

    public Result changePassword(HttpServletRequest req, HttpServletResponse rep) throws IOException {
        String username = req.getParameter("username");
        String newPassword = req.getParameter("Password");
        User usr = userDao.getUserByUsername(username);
        String old = usr.getPassword();
        if (old.equals(newPassword)) {
            return Result.fail("密码不能与原密码相同");
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
        return null;
    }
}