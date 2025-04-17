package com.any.zhangjunjie.dao.imple;

import com.any.zhangjunjie.dao.AdminDao;
import com.any.zhangjunjie.entity.Movie;
import com.any.zhangjunjie.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminDaoimpl implements AdminDao {
    @Override
    public boolean change(Movie movie) {
        Connection conn=null;
        String sql="update movie set time=?,price=?,detail=? where movieId=?";
        int movieId=movie.getMovieId();
        try{
            conn= JdbcUtils.getConnection();
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,movie.getTime());
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
}
