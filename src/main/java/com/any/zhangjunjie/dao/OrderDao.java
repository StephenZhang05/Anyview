package com.any.zhangjunjie.dao;

import com.any.zhangjunjie.entity.Movie;
import com.any.zhangjunjie.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDao {

    Movie getOrderById(int movieId);

    void createOrder(Order newOrder);

    List<Order> history(int userId);

    Movie getMovieById(int movieId);

    List<Order> orders(LocalDateTime now);
}
