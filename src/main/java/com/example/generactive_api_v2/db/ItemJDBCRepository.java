package com.example.generactive_api_v2.db;

import com.example.generactive_api_v2.model.Generative;
import com.example.generactive_api_v2.model.Item;
import com.example.generactive_api_v2.model.Stock;

import java.sql.*;
import java.util.List;

public final class ItemJDBCRepository {

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

    private ItemJDBCRepository() {
    }

    public static void addItem(Item item) {
        try {
            Connection connection = getConnection();
            PreparedStatement statement;
            String queryForGeneractive = "INSERT INTO Generactive_Item (Title, Price, Image_Url, Currency, Parent, Configuration_Id, Complexity) VALUES (?,?,?,?,?,?,?)";
            String queryForStock = "INSERT INTO Item (Title, Price, Image_Url, Currency, Parent, Configuration_Id) VALUES (?,?,?,?,?,?)";
            if (item instanceof Stock) {
                statement = connection.prepareStatement(queryForStock);
            } else {
                statement = connection.prepareStatement(queryForGeneractive);
                statement.setDouble(7, ((Generative) item).getComplexity());
            }
            statement.setString(1, item.getTitle());
            statement.setInt(2, item.getPrice());
            statement.setString(3, item.getImage_url());
            statement.setString(4, item.getCurrency());
            if (item.getParent() == null) {
                statement.setNull(5, Types.INTEGER);
            } else statement.setInt(5, item.getParent().getId());
            if (item.getConfiguration() == null) {
                statement.setNull(6, Types.INTEGER);
            } else statement.setInt(6, item.getConfiguration().getResolution().getId());
            statement.execute();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static List<Item> getItemList() {
//        List<Item> rv = new ArrayList<>();
//        Connection connection = getConnection();
//        String query = "SELECT * FROM item";
        return null;
    }
}
