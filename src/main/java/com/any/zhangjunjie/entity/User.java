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
    private String type;
    private Long phone;
    private int flag;
    public User() {

    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public User(String username, String password, String type){
        this.username = username;
        this.password = password;
        this.type = type;
    }
    public User(int userId, String username, String password, String type, Long phone) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
