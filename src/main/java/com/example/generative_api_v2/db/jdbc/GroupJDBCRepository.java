package com.example.generative_api_v2.db.jdbc;

import com.example.generative_api_v2.dto.GroupDTO;
import com.example.generative_api_v2.model.Configuration;
import com.example.generative_api_v2.model.Group;
import com.example.generative_api_v2.model.Item;
import com.example.generative_api_v2.model.Resolution;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupJDBCRepository {

    public static void clear(Connection connection) {
        try {
            String query1 = "delete FROM  \"group\"";
            String query2 = "ALTER SEQUENCE GENERATIVE_TEST_DB.PUBLIC.SYSTEM_SEQUENCE_9C415522_48E0_4911_A4A6_627EDCCCABCD RESTART WITH 1";
            String query3 = "ALTER SEQUENCE GENERATIVE_TEST_DB.PUBLIC.SYSTEM_SEQUENCE_77468E07_9A3C_4B20_8DFE_1D8EA10AE9B1  RESTART WITH 1";
            String query4 = "ALTER SEQUENCE GENERATIVE_TEST_DB.PUBLIC.SYSTEM_SEQUENCE_EE9DAC49_0BF9_43ED_8FF0_C8A38630C420 RESTART WITH 1";
            PreparedStatement statement1 = connection.prepareStatement(query1);
            PreparedStatement statement2 = connection.prepareStatement(query2);
            PreparedStatement statement3 = connection.prepareStatement(query3);
            PreparedStatement statement4 = connection.prepareStatement(query4);
            statement1.executeUpdate();
            statement2.executeUpdate();
            statement3.executeUpdate();
            statement4.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


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
               //     group.setParent(resultSet.getInt(3));

                }
                Item item = new Item();
                item.setId(resultSet.getInt(4));
                item.setTitle(resultSet.getString(5));
                item.setPrice(resultSet.getInt(6));
                item.setImage_url(resultSet.getString(7));
                item.setCurrency(resultSet.getString(8));
           //     item.setParent(resultSet.getInt(9));
                int conf_id = resultSet.getInt(10);
                String query = "SELECT * FROM configuration WHERE id=?";
                PreparedStatement statement1 = connection.prepareStatement(query);
                statement1.setInt(1,conf_id);
                ResultSet conf = statement1.executeQuery();
                String resolution = null;
                if(conf.next())     resolution = conf.getString(2);
                Configuration configuration = null;

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
             //   group1.setParent(resultSet1.getInt(3));
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
         //   if (group.getParent() != 0) statement.setInt(2, group.getParent());
         //   else statement.setNull(2, Types.INTEGER);
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
         //  if(groupDTO.getParent()!=0) statement.setInt(2, groupDTO.getParent());
        //   else statement.setNull(2,Types.INTEGER);
            statement.setInt(3, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getGroupById(id);
    }

    public static Group getLastAdded() {
        Group group = null;
        String query = "SELECT * FROM \"group\" ORDER BY Id DESC limit 1";
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                group = getGroupById(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return group;
    }
}

