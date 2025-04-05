package com.any.zhangjunjie.entity;

/**
 * @author zhangjunjie
 * 电影实体类
 */
public class Movie {
    private int movieId;
    private String time;
    private int price;
    public Movie() {

    }
    public Movie(int movieId, String time, int price) {
        this.movieId = movieId;
        this.time = time;
        this.price = price;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
