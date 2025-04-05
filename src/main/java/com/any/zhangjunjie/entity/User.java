package com.any.zhangjunjie.entity;

/**
 * @author zhangjunjie
 * 用户实体类
 * 包含用户的基本信息，如用户名、密码、邮箱等
 * 用于存储和操作用户相关的数据
 */
public class User {
    private int userId;
    private String username;
    private String password;
    private int type;
    private Long phone;
    public User() {

    }
    public User(int userId, String username, String password, int type, Long phone) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.type = type;
        this.phone = phone;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
