package com.any.zhangjunjie.entity;

/**
 * @author zhangjunjie
 * 订单实体类
 */
public class Order {
    private int orderId;
    private int userId;
    private int price;
    private int movieId;
    private int paymentId;
    private int status;
    private String createTime;
      public Order() {

      }
      public Order(int orderId, int userId, int price, int movieId, int paymentId, int status, String createTime) {
          this.orderId = orderId;
          this.userId = userId;
          this.price = price;
          this.movieId = movieId;
          this.paymentId = paymentId;
          this.status = status;
      }
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
