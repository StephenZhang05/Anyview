package com.any.zhangjunjie.filter;

import com.any.zhangjunjie.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import redis.clients.jedis.Jedis;

import java.io.IOException;

@WebFilter(urlPatterns = {"/order","/pay","/admin"},filterName = "loginFilter")
public class LoginFilter implements Filter {
    private static final String SECRET_KEY = "ZhangJunJie";


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String header= req.getHeader("authorization");
        final int TOKEN_TTL = 1800;
        final String REDIS_TOKEN_PREFIX = "token:";
        if (header   != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try (Jedis jedis = RedisUtil.getConnect()) { // 自动释放连接
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token)
                        .getBody();

                String redisKey = REDIS_TOKEN_PREFIX + token;
                if (jedis.exists(redisKey)) {
                    // 刷新有效期
                    jedis.expire(redisKey, TOKEN_TTL);
                    chain.doFilter(request, response);
                } else {
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                }
            } catch (Exception e) {
                if (e instanceof ServletException) {
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                } else {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Redis error");
                }
            }
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is missing");
        }
    }
}
