package com.any.zhangjunjie.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {
    private static String url="jdbc:mysql://localhost:3306/anyview";
    private static String user="root";
    private static String password="724083800";

    public static Connection getConnection() {
        Connection connection = null;
        try {
             connection = DriverManager.getConnection(url, user, password);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
