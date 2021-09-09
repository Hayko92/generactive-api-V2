package com.example.generactive_api_v2.db;

import com.example.generactive_api_v2.dto.GroupDTO;
import com.example.generactive_api_v2.model.Configuration;
import com.example.generactive_api_v2.model.Group;
import com.example.generactive_api_v2.model.Item;
import com.example.generactive_api_v2.model.Resolution;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupJDBCRepository {


    public static List<Group> getGroupList() {
        List<Group> result = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()){
            String query = "SELECT Id FROM \"group\"";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Group group = getGroupById(resultSet.getInt(1));
                result.add(group);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static Group getGroupById(int parentid) {
        Group group = null;
        List<Item> items = new ArrayList<>();
        List<Group> groups = new ArrayList<>();
        try(Connection connection = DBConnection.getConnection()) {
            String query1 = "SELECT * FROM \"group\" AS G LEFT JOIN Item AS I ON G.Id=I.Parent WHERE G.Id=?;";
            PreparedStatement statement = connection.prepareStatement(query1);
            statement.setInt(1, parentid);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if (group == null) {
                    group = new Group();
                    group.setId(resultSet.getInt(1));
                    group.setTitle(resultSet.getString(2));
                    group.setParent(resultSet.getInt(3));

                }
                Item item = new Item();
                item.setId(resultSet.getInt(4));
                item.setTitle(resultSet.getString(5));
                item.setPrice(resultSet.getInt(6));
                item.setImage_url(resultSet.getString(7));
                item.setCurrency(resultSet.getString(8));
                item.setParent(resultSet.getInt(9));
                int conf_id = resultSet.getInt(10);
                String query = "SELECT * FROM configuration WHERE id=?";
                PreparedStatement statement1 = connection.prepareStatement(query);
                statement1.setInt(1,conf_id);
                ResultSet conf = statement1.executeQuery();
                String resolution = null;
                if(conf.next())     resolution = conf.getString(2);
                Configuration configuration = null;
                if(resolution!=null)    configuration = new Configuration(Resolution.valueOf(resolution));
                item.setConfiguration(configuration);
                if (item.getId() != 0) items.add(item);

            }
            String query2 = "SELECT * FROM \"group\" AS G  WHERE G.Parent=?;";
            PreparedStatement statement2 = connection.prepareStatement(query2);
            statement2.setInt(1, parentid);
            Group group1;
            ResultSet resultSet1 = statement2.executeQuery();

            while (resultSet1.next()) {
                group1 = new Group();
                group1.setId(resultSet1.getInt(1));
                group1.setTitle(resultSet1.getString(2));
                group1.setParent(resultSet1.getInt(3));
                groups.add(group1);
            }
            if (group != null) {
                group.setItems(items);
                group.setGroups(groups);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return group;

    }

    public static void add(Group group) {
        try(Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO \"group\"(Title,Parent) VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, group.getTitle());
            if (group.getParent() != 0) statement.setInt(2, group.getParent());
            else statement.setInt(2, 0);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                group.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeById(int id) {
        try (Connection connection = DBConnection.getConnection()){
            String query = "DELETE FROM \"group\" WHERE Id=?";
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Group updateById(int id, GroupDTO groupDTO) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "UPDATE \"group\" SET  Title=?, Parent=? WHERE Id=?";
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, groupDTO.getTitle());
            if (groupDTO.getParent() != null) statement.setInt(2, groupDTO.getParent().getId());
            else statement.setInt(2, 0);
            statement.setInt(3, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getGroupById(id);
    }
}
