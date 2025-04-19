package com.any.zhangjunjie.service;

import com.any.zhangjunjie.entity.Order;
import com.any.zhangjunjie.entity.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface AdminService {
    Result change(HttpServletRequest request, HttpServletResponse response);

    List<Order> orders(HttpServletRequest request, HttpServletResponse response);

    Result black(HttpServletRequest request, HttpServletResponse response);
}
