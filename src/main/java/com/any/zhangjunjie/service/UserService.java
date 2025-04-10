package com.any.zhangjunjie.service;

import com.any.zhangjunjie.entity.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface UserService {
    Result changePassword(HttpServletRequest req, HttpServletResponse rep) throws IOException;

    void register(HttpServletRequest req, HttpServletResponse rep) throws IOException;

    Result login(HttpServletRequest req, HttpServletResponse rep);

    Result logout(HttpServletRequest req, HttpServletResponse rep);
}
