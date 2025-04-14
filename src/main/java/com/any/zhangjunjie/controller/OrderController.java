package com.any.zhangjunjie.controller;

import com.any.zhangjunjie.entity.Movie;
import com.any.zhangjunjie.entity.Order;
import com.any.zhangjunjie.entity.Result;
import com.any.zhangjunjie.service.OrderService;
import com.any.zhangjunjie.service.impl.OrderServiceimpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * @author zhangjunjie
 * 订单控制器类
 */
@WebServlet("/order/*")
public class OrderController extends BaseController{
    private final OrderService orderService = new OrderServiceimpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    /**
     * 下单业务，根据传入的电影id，用户id，下单
     * @param req
     * @param resp
     * @throws
     */
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
          orderService.createOrder(req, resp);
    }

    /**
     * 查看历史订单，请求头中包含用户id，返回该用户的所有订单
     * @param req
     * @param resp
     * @throws IOException
     */
    protected List<Order> history(HttpServletRequest req, HttpServletResponse resp)throws IOException{
        return orderService.history(req, resp);
    }

    /**
     * 查看电影详情
     * @param req
     * @param resp
     * @throws IOException
     */
    protected Movie detail(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        return orderService.detail(req, resp);
    }
}
