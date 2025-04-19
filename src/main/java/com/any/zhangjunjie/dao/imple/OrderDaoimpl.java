package com.any.zhangjunjie.dao.imple;

import com.any.zhangjunjie.dao.OrderDao;
import com.any.zhangjunjie.entity.Movie;
import com.any.zhangjunjie.entity.Order;
import com.any.zhangjunjie.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoimpl implements OrderDao {
    @Override
    public void createOrder(Connection conn, Order newOrder) {
        String sql="insert into 'order'(movieId,userId,price,paymentId,status,createTime) values(?,?,?,?,?,?)";
        try {
            conn= JdbcUtils.getConnection();
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,newOrder.getMovieId());
            pstmt.setInt(2,newOrder.getUserId());
            pstmt.setInt(3,newOrder.getPrice());
            pstmt.setInt(4,newOrder.getPaymentId());
            pstmt.setInt(5,newOrder.getStatus());
            pstmt.setString(6,newOrder.getCreateTime());
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * 获取用户的订单列表，按创建时间倒序
     * @param userId
     * @return
     */
    @Override
    public List<Order> history(int userId) {
        Connection conn=null;
        List<Order>list=new ArrayList<>();
        try{
            conn=JdbcUtils.getConnection();
            String sql="select * from order where userId=? order by createTime desc";
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,userId);
            try(ResultSet rs=pstmt.executeQuery()){
                while(rs.next()){
                    Order order=new Order();
                    order.setOrderId(rs.getInt("orderId"));
                    order.setMovieId(rs.getInt("movieId"));
                    order.setUserId(rs.getInt("userId"));
                    order.setPrice(rs.getInt("price"));
                    order.setPaymentId(rs.getInt("paymentId"));
                    order.setStatus(rs.getInt("status"));
                    order.setCreateTime(rs.getString("createTime"));
                    list.add(order);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JdbcUtils.close(conn);
        }
        return list;
    }

    /**
     * 通过movieId获取电影信息
     *
     * @param movieId
     * @return
     */
    @Override
    public Movie getMovieById(int movieId) {
        String sql="select * from movie where movieId=?  ";
        Connection connection=null;
        Movie movie=new Movie();
        try{
            connection=JdbcUtils.getConnection();
            PreparedStatement pstmt=connection.prepareStatement(sql);
            pstmt.setInt(1,movieId);
            try(ResultSet rs=pstmt.executeQuery()){
                if(rs.next()){
                    movie.setMovieId(rs.getInt("movieId"));
                    movie.setBeginTime(rs.getString("time"));
                    movie.setEndTime(rs.getString("endTime"));
                    movie.setPrice(rs.getInt("price"));
                    movie.setDetail(rs.getString("detail"));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JdbcUtils.close(connection);
        }
        return movie;
    }

    /**
     *
     * 查找最近三天内的订单
     * @param now
     * @return
     */
    @Override
    public List<Order> orders(LocalDateTime now) {
        String sql="select * from 'order' where createTime>? and createTime<?";
        Connection connection=null;
        List<Order>list=new ArrayList<>();
        try{
            connection=JdbcUtils.getConnection();
            PreparedStatement pstmt=connection.prepareStatement(sql);
            pstmt.setString(1,now.minusDays(3).toString());
            pstmt.setString(2,now.toString());
            try(ResultSet rs=pstmt.executeQuery()){
                while(rs.next()){
                    Order order=new Order();
                    order.setOrderId(rs.getInt("orderId"));
                    order.setMovieId(rs.getInt("movieId"));
                    order.setUserId(rs.getInt("userId"));
                    order.setPrice(rs.getInt("price"));
                    order.setPaymentId(rs.getInt("paymentId"));
                    order.setStatus(rs.getInt("status"));
                    order.setCreateTime(rs.getString("createTime"));
                    list.add(order);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 悲观锁模拟高并发
     * @param conn
     * @param movieId
     * @return
     */
    @Override
    public Movie getMovieByIdForUpdate(Connection conn, int movieId) {
        String sql="select * from movie where movieId=? for update ";
        Movie movie=new Movie();
        try{
            conn=JdbcUtils.getConnection();
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,movieId);
            try(ResultSet rs=pstmt.executeQuery()){
                if(rs.next()){
                    movie.setMovieId(rs.getInt("movieId"));
                    movie.setBeginTime(rs.getString("time"));
                    movie.setEndTime(rs.getString("endTime"));
                    movie.setPrice(rs.getInt("price"));
                    movie.setDetail(rs.getString("detail"));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return movie;
    }
}
