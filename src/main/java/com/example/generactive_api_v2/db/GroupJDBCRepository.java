package com.example.generactive_api_v2.db;

import com.example.generactive_api_v2.dto.GroupDTO;
import com.example.generactive_api_v2.model.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupJDBCRepository {
    public static List<Group> getGroupList() {
        List<Group> result = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM \"group\"";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt(1));
                group.setTitle(resultSet.getString(2));
                group.setParent(GroupJDBCRepository.getGroupById(resultSet.getInt(3)));
                result.add(group);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static Group getGroupById(int parentid) {
        Group group = null;
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM \"group\" WHERE Id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, parentid);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                group = new Group();
                group.setId(resultSet.getInt(1));
                group.setTitle(resultSet.getString(2));
                group.setParent(getGroupById(resultSet.getInt(3)));
            }
            return group;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return group;

    }

    public static void add(Group group) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO \"group\"(Title,Parent) VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, group.getTitle());
            if (group.getParent() != null) statement.setInt(2, group.getParent().getId());
            else statement.setNull(2, Types.INTEGER);
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
        try (Connection connection = DBConnection.getConnection()) {
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
            else statement.setNull(2, Types.INTEGER);
            statement.setInt(3, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getGroupById(id);
    }
}
