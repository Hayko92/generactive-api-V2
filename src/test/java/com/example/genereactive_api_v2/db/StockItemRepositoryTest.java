package com.example.genereactive_api_v2.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StockItemRepositoryTest {
    @BeforeAll
    public static void truncatetables() {
        try (Connection connection = TestDatabaseConnection.getConnection()) {
            String query = "delete from ITEM;\n" +
                    "delete from GENERACTIVE_ITEM;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addTest() {
        try (Connection connection = TestDatabaseConnection.getConnection()) {
            String query = "INSERT  INTO  Item (title,price) VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "testName");
            statement.setInt(2, 100);
            int changedRowsCount = statement.executeUpdate();
            Assertions.assertEquals(1, changedRowsCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
