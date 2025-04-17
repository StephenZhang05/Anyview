package com.any.zhangjunjie.controller;

import com.any.zhangjunjie.entity.Order;
import com.any.zhangjunjie.entity.Result;
import com.any.zhangjunjie.service.AdminService;
import com.any.zhangjunjie.service.impl.AdminServiceimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 *  @author zhangjunjie
 * 管理员控制器类
 * 用于处理管理员相关的请求，如用户管理、电影管理等
 */
@WebServlet("/admin/*")
public class AdminController extends BaseController{
    private final AdminService adminService=new AdminServiceimpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    /**
     * 修改电影信息
     * @return Result
     */
    protected Result change(HttpServletRequest request,HttpServletResponse response){

        return adminService.change(request,response);
    }

    /**
     * 查看三天内的所有订单
     * @param request
     * @param response
     * @return List<Order>
     */
    protected List<Order>orders(HttpServletRequest request,HttpServletResponse response){
        return adminService.orders(request,response);
    }
}
