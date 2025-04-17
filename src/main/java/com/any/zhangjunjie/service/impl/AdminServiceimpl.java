package com.any.zhangjunjie.service.impl;

import com.any.zhangjunjie.dao.AdminDao;
import com.any.zhangjunjie.dao.OrderDao;
import com.any.zhangjunjie.dao.imple.AdminDaoimpl;
import com.any.zhangjunjie.dao.imple.OrderDaoimpl;
import com.any.zhangjunjie.entity.Movie;
import com.any.zhangjunjie.entity.Order;
import com.any.zhangjunjie.entity.Result;
import com.any.zhangjunjie.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhangjunjie
 * 负责管理员权限业务
 */
public class AdminServiceimpl implements AdminService {
    private final AdminDao adminDao = new AdminDaoimpl();
    private final OrderDao orderDao = new OrderDaoimpl();
    /**
     * 修改电影信息：时间，价格，介绍
     * @param request
     * @param response
     * @return
     */
    @Override
    public Result change(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Movie movie = mapper.readValue(request.getInputStream(), Movie.class);
            if(adminDao.change(movie)){
                return Result.ok("修改成功");
            }else{
                return Result.fail("修改失败");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查看最近三天的订票信息
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<Order> orders(HttpServletRequest request, HttpServletResponse response) {
        LocalDateTime now=LocalDateTime.now();
        List<Order>orderList=orderDao.orders(now);
        return orderList;
    }
}
