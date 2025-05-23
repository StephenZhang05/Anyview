package com.any.zhangjunjie.dao.imple;

import com.any.zhangjunjie.dao.UserDao;
import com.any.zhangjunjie.entity.User;
import com.any.zhangjunjie.utils.JdbcUtils;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserDaoimpl implements UserDao {
    @Override
    public User getUserById(int userId) {
        String sql = "SELECT * FROM user WHERE userId = ?";
        Connection conn = null;
        User user = new User();
        try {
            conn = JdbcUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt("userId"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setType(rs.getString("type"));
                    user.setPhone(rs.getLong("phone"));
                    user.setFlag(rs.getInt("flag"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(conn);
        }
        return user;
    }

    @Override
    public void changePassword(int userId, String newPassword) {
         String sql = "UPDATE user SET password = ? WHERE userId = ?";
         Connection conn = null;
         try  {
             conn= JdbcUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             pstmt.setString(1, newPassword);
             pstmt.setInt(2, userId);
             pstmt.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();
         }finally {
             JdbcUtils.close(conn);
         }
    }


    @Override
    public boolean saveUser(User user) {
        String sql = "INSERT INTO user(username, password, type)  VALUES (?,?,?)";
        Connection conn = null;
        try  {
            conn= JdbcUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            String passwordX = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, passwordX);
            pstmt.setString(3, user.getType());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            JdbcUtils.close(conn);
        }

    }

    @Override
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        User user = new User();
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt("userId"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password")); // 存储的是 BCrypt 加密后的值
                    user.setType(rs.getString("type"));
                    user.setPhone(rs.getLong("phone"));
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JdbcUtils.close(conn);
        }
        return null;
    }
}
