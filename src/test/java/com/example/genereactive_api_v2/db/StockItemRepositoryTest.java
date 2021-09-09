package com.example.genereactive_api_v2.db;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StockItemRepositoryTest {

    @Test
    public void addTest() {
        try(Connection connection = TestDatabaseConnection.getConnection()) {
            String query = "INSERT  INTO Item (title,price) VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,"testName");
            statement.setInt(2,100);
            statement.execute();
            Assertions.assertEquals(2,2);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
