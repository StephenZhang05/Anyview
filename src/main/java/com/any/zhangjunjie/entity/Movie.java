package com.any.zhangjunjie.entity;

/**
 * @author zhangjunjie
 * 电影实体类
 */
public class Movie {
    private int movieId;
    private String beginTime;
    private String endTime;
    private int price;
    private String detail;
    public Movie() {
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Movie(int movieId, String beginTime,String endTime, int price,String detail) {
        this.movieId = movieId;
        this.beginTime=beginTime;
        this.endTime=endTime;
        this.price = price;
        this.detail=detail;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String time) {
        this.beginTime = time;
    }

    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
