package com.any.zhangjunjie.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/user","/order","/pay","/admin"},filterName = "loginFilter")
public class LoginFilter implements Filter {
    private static final String SECRET_KEY = "ZhangJunJie";

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String authorizationHeader = req.getHeader("Authorization");
        //TODO 使用Redis实现刷新有效期
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // 去掉Bearer前缀
            try {
                // 验证JWT
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token)
                        .getBody();
                // 验证通过，继续请求
                chain.doFilter(request, response);
            } catch (Exception e) {
                // 验证失败，返回401错误
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            }
        } else {
            // 没有提供token，返回401错误
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is missing");
        }
    }
}
