package com.any.zhangjunjie.controller;

import com.any.zhangjunjie.entity.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author zhangjunjie
 * 控制器基类,路由分发
 */
public class BaseController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo(); // 获取如 "/login" 的路径
        String methodName =null;
        if(pathInfo==null||pathInfo.equals("/")){
            methodName="index";
        }else{
            methodName=pathInfo.replace("/","");
        }

        try {
            // 通过反射获取方法
            Method method = this.getClass().getDeclaredMethod(methodName,
                    HttpServletRequest.class, HttpServletResponse.class);

            // 处理返回类型差异
            method.setAccessible(true);
            Object result = method.invoke(this, req, resp);
            if (result instanceof Result||result instanceof List<?>) {
                resp.setContentType("application/json;charset=utf-8");
                resp.getWriter().write(new ObjectMapper().writeValueAsString(result));
            }
        } catch (NoSuchMethodException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "无效路径: " + pathInfo);
        } catch (Exception e) {
            throw new ServletException("路由执行错误", e);
        }
    }
}
