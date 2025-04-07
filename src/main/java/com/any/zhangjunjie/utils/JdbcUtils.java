package com.any.zhangjunjie.utils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {
    private static final HikariDataSource dataSource;
    private static final String url = "jdbc:mysql://localhost:3306/anyview";
    private static final String user = "root";
    private static final String password = "724083800";
    static {
        HikariConfig config = new HikariConfig();
        // 基础配置
        config.setJdbcUrl("jdbc:mysql://localhost:3306/anyview");
        config.setUsername("root");
        config.setPassword("724083800");

        // 连接池优化配置（根据实际需求调整）
        config.setMaximumPoolSize(20);          // 最大连接数
        config.setMinimumIdle(5);               // 最小空闲连接
        config.setConnectionTimeout(30000);     // 连接超时时间30s
        config.setIdleTimeout(600000);          // 空闲连接存活时间10分钟
        config.setMaxLifetime(1800000);         // 连接最大存活时间30分钟
        config.setAutoCommit(true);             // 自动提交事务

        // MySQL专属配置
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
