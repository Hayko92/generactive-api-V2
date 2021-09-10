package com.example.generative_api_v2.db;

import com.example.generative_api_v2.dto.ItemDTO;
import com.example.generative_api_v2.model.Configuration;
import com.example.generative_api_v2.model.Group;
import com.example.generative_api_v2.model.Item;
import com.example.generative_api_v2.model.Resolution;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class StockItemJDBCRepository {

    private StockItemJDBCRepository() {
    }

    public static void clear(Connection connection) {
        try {
            String query = "delete FROM  item";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            if (item.getParent() > 0) statement.setInt(5, item.getParent());
            else statement.setNull(5, Types.INTEGER);
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

    public static Configuration getConfiguration(int config_id) {
        Configuration configuration = null;

        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM Configuration WHERE Id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, config_id);
            ResultSet resultSet = statement.executeQuery();
            String resolution = "";
            if (resultSet.next()) {
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
            Group group = GroupJDBCRepository.getGroupById(parentid);
            if (group != null) item.setParent(group.getId());
            int config_id = resultSet.getInt(7);
            Configuration configuration = getConfiguration(config_id);
            if (configuration != null) item.setConfiguration(configuration);
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
                Group groupById = GroupJDBCRepository.getGroupById(resultSet.getInt(6));
                if (groupById != null) item.setParent(groupById.getId());
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
            if (itemDTO.getParent() > 0) statement.setInt(5, itemDTO.getParent());
            else statement.setNull(5, Types.INTEGER);

            if (itemDTO.getConfiguration() != null) {
                statement.setInt(6, itemDTO.getConfiguration().getResolution().getId());
            } else statement.setNull(6, Types.INTEGER);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Item findLastAdded() {
        Item item = null;
        String query = "SELECT * FROM Item ORDER BY Id DESC limit 1";
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                item = findItemById(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
}
