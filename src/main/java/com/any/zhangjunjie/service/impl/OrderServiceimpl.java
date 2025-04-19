package com.any.zhangjunjie.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.any.zhangjunjie.dao.OrderDao;
import com.any.zhangjunjie.dao.imple.OrderDaoimpl;
import com.any.zhangjunjie.entity.Movie;
import com.any.zhangjunjie.entity.Order;
import com.any.zhangjunjie.service.OrderService;
import com.any.zhangjunjie.utils.JdbcUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.SQLException;
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
    public void createOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            conn.setAutoCommit(false);
            int movieId = Integer.parseInt(req.getParameter("movieId"));
            Movie movie = orderDao.getMovieByIdForUpdate(conn,movieId);
            Order newOrder = new Order();
            newOrder.setMovieId(movieId);
            newOrder.setUserId(Integer.parseInt(req.getParameter("userId")));
            newOrder.setPrice(movie.getPrice());
            newOrder.setPaymentId(RandomUtil.randomInt());
            newOrder.setCreateTime(LocalDateTime.now().toString());
            newOrder.setStatus(0);
            orderDao.createOrder(conn, newOrder);
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new RuntimeException("订单创建失败：" + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ignored) {}
            }
        }
    }
    /**
     * 根据用户id获取历史订单
     * @param req
     * @param resp
     * @return
     */
    @Override
    public List<Order> history(HttpServletRequest req, HttpServletResponse resp) {
        int userId= Integer.parseInt(req.getParameter("userId"));
        List<Order> orders=orderDao.history(userId);
        return orders;
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
        Movie movie =orderDao.getMovieById( movieId);
        return movie;
    }

}
