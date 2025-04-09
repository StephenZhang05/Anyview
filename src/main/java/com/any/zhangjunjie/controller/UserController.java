package com.any.zhangjunjie.controller;

import com.any.zhangjunjie.dao.UserDao;
import com.any.zhangjunjie.dao.imple.UserDaoimpl;
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

/**
 * @author zhangjunjie
 * 用户控制器类
 */
@WebServlet("/user/*")
public class UserController extends HttpServlet {
    /**
     * 需要实现的功能有：修改用户密码，登陆，注册，登出账户，查看历史订单
     *
     */
    private UserServiceimpl userService=new UserServiceimpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo(); // 获取如 "/login" 的路径
        String methodName = parseMethodName(pathInfo);

        try {
            // 通过反射获取方法
            Method method = this.getClass().getDeclaredMethod(methodName,
                    HttpServletRequest.class, HttpServletResponse.class);

            // 处理返回类型差异
            method.setAccessible(true);
            Object result = method.invoke(this, req, resp);
            if (result instanceof Result) {
                resp.getWriter().write(new ObjectMapper().writeValueAsString(result));
            }
        } catch (NoSuchMethodException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "无效路径: " + pathInfo);
        } catch (Exception e) {
            throw new ServletException("路由执行错误", e);
        }
    }

    // 路径解析逻辑
    private String parseMethodName(String pathInfo) {
        if (pathInfo == null || pathInfo.equals("/")) {
            return "index"; // 默认入口
        }
        return pathInfo.replace("/", ""); // 将 "/login" 转为 "login"
    }



    protected Result changePassword(HttpServletRequest req, HttpServletResponse rep) throws IOException {
      //校验旧密码，修改密码
        return userService.changePassword(req,rep);
    }

    protected void register(HttpServletRequest req, HttpServletResponse rep) throws IOException {
        //注册用户
        userService.register(req,rep);
    }
    protected Result login(HttpServletRequest req, HttpServletResponse rep) throws IOException {
        //登陆用户
        return userService.login(req,rep);
    }
    protected Result logout(HttpServletRequest req, HttpServletResponse rep) throws IOException {
        //登出用户
        return Result.ok();
    }
}
