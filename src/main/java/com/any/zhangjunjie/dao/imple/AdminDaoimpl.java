package com.any.zhangjunjie.dao.imple;

import com.any.zhangjunjie.dao.AdminDao;
import com.any.zhangjunjie.entity.Movie;
import com.any.zhangjunjie.entity.Result;
import com.any.zhangjunjie.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminDaoimpl implements AdminDao {
    @Override
    public boolean change(Movie movie) {
        Connection conn=null;
        String sql="update movie set beginTime=?,endTime=?,price=?, detail=? where movieId=?";
        int movieId=movie.getMovieId();
        try{
            conn= JdbcUtils.getConnection();
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,movie.getBeginTime());
            pstmt.setString(2,movie.getEndTime());
            pstmt.setInt(2,movie.getPrice());
            pstmt.setString(3,movie.getDetail());
            pstmt.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }finally {
            JdbcUtils.close(conn);
        }
        return true;
    }

    @Override
    public Result black(int userId) {
        String sql="update user set flag=1 where userId=?";
        Connection conn=null;
        try{
            conn= JdbcUtils.getConnection();
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,userId);
            pstmt.executeQuery();
            return Result.ok("用户拉黑成功");
        }catch (SQLException e){
            e.printStackTrace();
            return Result.fail("数据库异常");
        }finally {
            JdbcUtils.close(conn);
        }

    }
}
