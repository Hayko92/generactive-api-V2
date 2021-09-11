package com.example.generative_api_v2.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

final class DBConnection {

    public static final String URL = DatabaseConfigUtil.getConnectionProperties().getProperty("db.url");
    public static final String USER = DatabaseConfigUtil.getConnectionProperties().getProperty("db.user");
    public static final String PASSWORD = DatabaseConfigUtil.getConnectionProperties().getProperty("db.password");
    private DBConnection() {
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
