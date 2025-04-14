package com.any.zhangjunjie.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.any.zhangjunjie.dao.OrderDao;
import com.any.zhangjunjie.dao.imple.OrderDaoimpl;
import com.any.zhangjunjie.entity.Movie;
import com.any.zhangjunjie.entity.Order;
import com.any.zhangjunjie.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhangjunjie
 * 负责订单业务
 */
public class OrderServiceimpl implements OrderService {

    private final OrderDao orderDao=new OrderDaoimpl();
    /**
     * 传入电影id和用户id
     * @param req
     * @param resp
     */
    //根据电影id获取电影信息后的价格，然后默认paymentId为0
    @Override
    public void createOrder(HttpServletRequest req, HttpServletResponse resp) {
        int movieId= Integer.parseInt(req.getParameter("movieId"));
        int userId= Integer.parseInt(req.getParameter("userId"));
        Order newOrder=new Order();
        newOrder.setMovieId(movieId);
        newOrder.setUserId(userId);
        Movie movie =orderDao.getOrderById(movieId);
        newOrder.setPrice(movie.getPrice());
        newOrder.setPaymentId(RandomUtil.randomInt());
        LocalDateTime now=LocalDateTime.now();
        newOrder.setCreateTime(now.toString());
        newOrder.setStatus(0);
        orderDao.createOrder(newOrder);
    }

    @Override
    public List<Order> history(HttpServletRequest req, HttpServletResponse resp) {
        int userId= Integer.parseInt(req.getParameter("userId"));
        List<Order> orders=orderDao.history(userId);


        return null;
    }

    /**
     *
     * 传入电影Id获取详情
     * @param
     * @param resp
     * @return
     */
    @Override
    public Movie detail(HttpServletRequest req, HttpServletResponse resp) {
        int movieId= Integer.parseInt(req.getParameter("movieId"));
        Movie movie =orderDao.getMovieById(movieId);
        return movie;
    }

}
