package com.any.zhangjunjie.controller;

import com.any.zhangjunjie.dao.UserDao;
import com.any.zhangjunjie.dao.imple.UserDaoimpl;
import com.any.zhangjunjie.entity.Order;
import com.any.zhangjunjie.entity.Result;
import com.any.zhangjunjie.entity.User;
import com.any.zhangjunjie.service.UserService;
import com.any.zhangjunjie.service.impl.UserServiceimpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author zhangjunjie
 * 用户控制器类
 */
@WebServlet("/user/*")
public class UserController extends BaseController {

    private UserServiceimpl userService=new UserServiceimpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    /**
     * 修改密码
     * @param req
     * @param rep
     * @return Result
     * @throws IOException
     */
    protected Result changePassword(HttpServletRequest req, HttpServletResponse rep) throws IOException {
        return userService.changePassword(req,rep);
    }

    /**
     * 注册用户
     * @param req
     * @param rep
     * @throws IOException
     */
    protected void register(HttpServletRequest req, HttpServletResponse rep) throws IOException {

        userService.register(req,rep);
    }

    /**
     * 登录用户
     * @param req
     * @param rep
     * @return
     * @throws IOException
     */
    protected Result login(HttpServletRequest req, HttpServletResponse rep) throws IOException {

        return userService.login(req,rep);
    }

    /**
     * 登出（需要Redis正确连接)
     * @param req
     * @param rep
     * @return Result
     * @throws IOException
     */
    protected Result logout(HttpServletRequest req, HttpServletResponse rep) throws IOException {
        //登出用户
        return userService.logout(req,rep);
    }

    /**
     *  看电影提醒
     * @param req
     * @param rep
     * @return
     * @throws IOException
     */
    protected Result message(HttpServletRequest req, HttpServletResponse rep)throws IOException{

        return userService.message(req,rep);
    }

    /**
     *  查看订单信息
     * @param req
     * @param rep
     * @return list<Order>  </Order>
     */
    protected List<Order> history(HttpServletRequest req, HttpServletResponse rep){
        return userService.history(req,rep);
    }
}
