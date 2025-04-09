package com.any.zhangjunjie.dao;

import com.any.zhangjunjie.entity.User;
import com.zaxxer.hikari.HikariConfig;

public interface UserDao {
    User getUserById(int userId);

    void changePassword(int userId, String newPassword);

    boolean saveUser(User newUser);

    User getUserByUsername(String username);
}
