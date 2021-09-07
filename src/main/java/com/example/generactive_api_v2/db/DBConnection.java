package com.example.generactive_api_v2.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

final class DBConnection {
    private DBConnection() {
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection(PostgresCredentials.URL, PostgresCredentials.USER, PostgresCredentials.PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
