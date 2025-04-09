package com.any.zhangjunjie.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.mysql.cj.conf.PropertyKey.logger;

public class JdbcUtils {
    private static final String url = "jdbc:mysql://localhost:3306/anyview";
    private static final String user = "root";
    private static final String password = "724083800";
    public static Connection getConnection() {
        Connection conn=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn= DriverManager.getConnection(url,user,password);
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return conn;
    }
    public static void close(Connection conn){
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

