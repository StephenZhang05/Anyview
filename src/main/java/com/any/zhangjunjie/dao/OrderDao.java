package com.any.zhangjunjie.dao;

import com.any.zhangjunjie.entity.Movie;
import com.any.zhangjunjie.entity.Order;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderDao {

    void createOrder(Connection conn, Order newOrder);

    List<Order> history(int userId);

    Movie getMovieById(int movieId);

    List<Order> orders(LocalDateTime now);
    Movie getMovieByIdForUpdate(Connection conn, int movieId);
}
