package com.example.generactive_api_v2.db;

import com.example.generactive_api_v2.dto.ItemDTO;
import com.example.generactive_api_v2.model.Configuration;
import com.example.generactive_api_v2.model.Group;
import com.example.generactive_api_v2.model.Item;
import com.example.generactive_api_v2.model.Resolution;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class StockItemJDBCRepository {

    private StockItemJDBCRepository() {
    }

    public static void add(Item item) {
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement statement;
            String query = "INSERT INTO Item (Title, Price, Image_Url, Currency, Parent, Configuration_Id) VALUES (?,?,?,?,?,?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int generated = resultSet.getInt(1);
                item.setId(generated);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static List<Item> getAll() {
        List<Item> rv = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM Item";
            PreparedStatement statement = connection.prepareStatement(query);
            rv = executePreparedStatment(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rv;
    }

    private static Configuration getConfiguration(int config_id) {
        Configuration configuration = null;

        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM Configuration WHERE Id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, config_id);
            ResultSet resultSet = statement.executeQuery();
            String resolution = "";
            while (resultSet.next()) {
                resolution = statement.executeQuery().getString(2);
            }
            String finalResolution = resolution;
            if (Arrays.stream(Resolution.values())
                    .anyMatch(e -> e.toString().equals(finalResolution))) {
                Resolution resolution1 = Resolution.valueOf(resolution);
                configuration = new Configuration(resolution1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return configuration;
    }

    private static Group getGroupById(int parentid) {
        Group group = null;
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM Group WHERE Id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, parentid);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                group = new Group();
                group.setId(resultSet.getInt(1));
                group.setTitle(resultSet.getString(2));
                while (resultSet.getInt(3) != 0) {
                    group.setParent(getGroupById(resultSet.getInt(3)));
                }
            }
            return group;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return group;

    }

    public static List<Item> getItemsWithPriceFromTo(int from, int to) {
        List<Item> rv = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM Item WHERE Price BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, from);
            statement.setInt(2, to);
            rv = executePreparedStatment(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rv;
    }

    private static List<Item> executePreparedStatment(PreparedStatement statement) throws SQLException {
        List<Item> rv = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Item item = new Item();
            item.setId(resultSet.getInt(1));
            item.setTitle(resultSet.getString(2));
            item.setPrice(resultSet.getInt(3));
            item.setImage_url(resultSet.getString(4));
            item.setCurrency(resultSet.getString(5));
            int parentid = resultSet.getInt(6);
            Group group = getGroupById(parentid);
            item.setParent(group);
            int config_id = resultSet.getInt(7);
            Configuration configuration = getConfiguration(config_id);
            item.setConfiguration(configuration);
            rv.add(item);
        }
        return rv;
    }

    public static Item findItemById(int id) {
        Item item = null;
        String query = "SELECT * FROM Item WHERE Id=?";
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                item = new Item();
                item.setId(resultSet.getInt(1));
                item.setTitle(resultSet.getString(2));
                item.setPrice(resultSet.getInt(3));
                item.setImage_url(resultSet.getString(4));
                item.setCurrency(resultSet.getString(5));
                item.setParent(getGroupById(resultSet.getInt(6)));
                item.setConfiguration(getConfiguration(resultSet.getInt(7)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static void deleteById(int id) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "DELETE FROM Item WHERE Id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateById(int id, ItemDTO itemDTO) {
        PreparedStatement statement;
        try (Connection connection = DBConnection.getConnection()) {
            String query = "UPDATE  Item SET Title=?, Price=?,Image_Url=?, Currency=?,Parent=?, Configuration_Id=? WHERE Id=?";
            statement = connection.prepareStatement(query);
            statement.setInt(7, id);
            statement.setString(1, itemDTO.getTitle());
            statement.setInt(2, itemDTO.getPrice());
            statement.setString(3, itemDTO.getImage_url());
            statement.setString(4, itemDTO.getCurrency());
            if (itemDTO.getParent() != null) statement.setInt(5, itemDTO.getParent().getId());
            else statement.setNull(5, Types.INTEGER);
            if (itemDTO.getConfiguration() != null)
                statement.setInt(6, itemDTO.getConfiguration().getResolution().getId());
            else statement.setNull(6, Types.INTEGER);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}