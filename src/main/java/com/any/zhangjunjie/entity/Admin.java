package com.any.zhangjunjie.entity;

/**
 * @author zhangjunjie
 * 管理员实体类
 */
public class Admin {
    private Integer id;
    private String username;
    private String password;
    private Integer phone;
    public Admin() {

    }
    public Admin(Integer id, String username, String password, Integer phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
    }
    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
