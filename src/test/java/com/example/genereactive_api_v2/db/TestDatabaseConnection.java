package com.example.genereactive_api_v2.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDatabaseConnection {
    public static final String URL = TestDatabaseConfigUtil.getConnectionProperties().getProperty("db.url");
    public static final String USER = TestDatabaseConfigUtil.getConnectionProperties().getProperty("db.user");
    public static final String PASSWORD = TestDatabaseConfigUtil.getConnectionProperties().getProperty("db.password");
    private TestDatabaseConnection() {
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.h2.Drive");
            connection = DriverManager
                    .getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
