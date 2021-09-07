package com.example.generactive_api_v2.db;

import com.example.generactive_api_v2.model.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupJDBCReposotory {
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
                group.setParent(GroupJDBCReposotory.getGroupById(resultSet.getInt(3)));
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
            String query = "SELECT * from \"group\" where id=?";
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
}
