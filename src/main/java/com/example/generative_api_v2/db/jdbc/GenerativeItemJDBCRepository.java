package com.example.generative_api_v2.db.jdbc;

import com.example.generative_api_v2.dto.GeneractiveDTO;
import com.example.generative_api_v2.model.Configuration;
import com.example.generative_api_v2.model.Generative;
import com.example.generative_api_v2.model.Group;
import com.example.generative_api_v2.model.Resolution;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class GenerativeItemJDBCRepository {

    private GenerativeItemJDBCRepository() {
    }

    public static void add(Generative item) {
        try (Connection connection = DBConnection.getConnection()) {

            PreparedStatement statement;
            String query = "INSERT INTO Generactive_Item (Title, Price, Image_Url, Currency, Parent, Configuration_Id, Complexity) VALUES (?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setDouble(7, item.getComplexity());
            statement.setString(1, item.getTitle());
            statement.setInt(2, item.getPrice());
            statement.setString(3, item.getImage_url());
            statement.setString(4, item.getCurrency());
            statement.setInt(5, item.getParent());

            if (item.getConfiguration() == null) {
                statement.setNull(6, Types.INTEGER);
            } else statement.setInt(6, item.getConfiguration().getResolution().ordinal());
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

    public static List<Generative> getAll() {
        List<Generative> rv = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM Generactive_Item";
            PreparedStatement statement = connection.prepareStatement(query);
            executeStatement(rv, statement);
            return rv;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rv;
    }

    private static void executeStatement(List<Generative> rv, PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Generative item = new Generative();
            item.setId(resultSet.getInt(1));
            item.setTitle(resultSet.getString(2));
            item.setPrice(resultSet.getInt(3));
            item.setImage_url(resultSet.getString(4));
            item.setCurrency(resultSet.getString(5));
            int parentid = resultSet.getInt(6);
         //   Group group = GroupJDBCRepository.getGroupById(parentid);
           // item.setParent(group.getId());
            int config_id = resultSet.getInt(7);
            Configuration configuration = getConfiguration(config_id);
            item.setConfiguration(configuration);
            item.setComplexity(resultSet.getDouble(8));
            rv.add(item);
        }
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


    public static Generative findItemById(int id) {
        Generative item = null;
        String query = "SELECT * FROM Generactive_Item WHERE Id=?";
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                item = new Generative();
                item.setComplexity(resultSet.getInt(8));
                item.setId(resultSet.getInt(1));
                item.setTitle(resultSet.getString(2));
                item.setPrice(resultSet.getInt(3));
                item.setImage_url(resultSet.getString(4));
                item.setCurrency(resultSet.getString(5));
         //       item.setParent(GroupJDBCRepository.getGroupById(resultSet.getInt(6)).getId());
                item.setConfiguration(getConfiguration(resultSet.getInt(7)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static List<Generative> getItemsWithPriceFromTo(int from, int to) {
        List<Generative> rv = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM Generactive_Item WHERE Price BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, from);
            statement.setInt(2, to);
            executeStatement(rv, statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rv;
    }

    public static void deleteById(int id) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "DELETE FROM Generactive_Item WHERE Id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateById(int id, GeneractiveDTO itemDTO) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "UPDATE  Generactive_Item SET Title=?, Price=?,Image_Url=?, Currency=?,Parent=?, Configuration_Id=?,Complexity=? WHERE Id=?";
            PreparedStatement statement;
            Generative item = findItemById(id);
            if (item != null) {
                statement = connection.prepareStatement(query);
                statement.setDouble(7, itemDTO.getComplexity());
                statement.setInt(8, id);
                statement.setString(1, itemDTO.getTitle());
                statement.setInt(2, itemDTO.getPrice());
                statement.setString(3, itemDTO.getImage_url());
                statement.setString(4, itemDTO.getCurrency());
             statement.setInt(5, itemDTO.getParent());

                if (itemDTO.getConfiguration() != null)
                    statement.setInt(6, itemDTO.getConfiguration().getResolution().ordinal());
                else statement.setNull(6, Types.INTEGER);
            } else return;
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
