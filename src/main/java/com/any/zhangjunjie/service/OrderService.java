package com.any.zhangjunjie.service;

import com.any.zhangjunjie.entity.Movie;
import com.any.zhangjunjie.entity.Order;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface OrderService {
    void createOrder(HttpServletRequest req, HttpServletResponse resp);

    List<Order> history(HttpServletRequest req, HttpServletResponse resp);

    Movie detail(HttpServletRequest req, HttpServletResponse resp);
}
