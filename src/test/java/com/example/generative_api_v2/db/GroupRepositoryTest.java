package com.example.generative_api_v2.db;

import com.example.generative_api_v2.db.jdbc.GroupJDBCRepository;
import com.example.generative_api_v2.db.jdbc.StockItemJDBCRepository;
import com.example.generative_api_v2.dto.GroupDTO;
import com.example.generative_api_v2.model.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupRepositoryTest {
    @BeforeEach
    protected   void clearItemAndGroupTables() {
        try(Connection connection = TestDatabaseConnection.getConnection()) {
            GroupJDBCRepository.clear(connection);
            StockItemJDBCRepository.clear(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addTest() {
        Group group = new Group("test_name");
        GroupJDBCRepository.add(group);
        Group received = GroupJDBCRepository.getLastAdded();
        assertEquals(group.getTitle(), received.getTitle());
    }

    @Test
    public void getAllTest() {
        Group group1 = new Group("test_name");
        Group group2 = new Group("test_name");
        Group group3 = new Group("test_name");
        GroupJDBCRepository.add(group1);
        GroupJDBCRepository.add(group2);
        GroupJDBCRepository.add(group3);
        List<Group> groups = GroupJDBCRepository.getGroupList();
        assertEquals(3, groups.size());
    }

    @Test
    public void findGroupByIdTest() {
        Group group1 = new Group("test_name");
        Group group2 = new Group("test_name");
        GroupJDBCRepository.add(group1);
        GroupJDBCRepository.add(group2);
        Group groupReceived1 = GroupJDBCRepository.getGroupById(group1.getId());
        Group groupReceived2 = GroupJDBCRepository.getGroupById(group1.getId());
        assertEquals(group1.getTitle(), groupReceived1.getTitle());
        assertEquals(group2.getTitle(), groupReceived2.getTitle());
    }

    @Test
    public void removeByIdTest() {
        Group group = new Group("test_name");
        GroupJDBCRepository.add(group);
        int generatedId = group.getId();
        assertNotNull(GroupJDBCRepository.getGroupById(generatedId));
        GroupJDBCRepository.removeById(generatedId);
        assertNull(GroupJDBCRepository.getGroupById(generatedId));
    }

    @Test
    public void updateByIdTest() {
        Group group =new Group("Test_title");
        GroupJDBCRepository.add(group);
        int generatedId = group.getId();
        GroupDTO groupDTO = new GroupDTO("changed_name");
        GroupJDBCRepository.updateById(generatedId,groupDTO);
        Group groupAfterUpdate = GroupJDBCRepository.getGroupById(generatedId);
        assertNotEquals(group, groupAfterUpdate);
        assertEquals(groupAfterUpdate.getTitle(),groupDTO.getTitle());
    }
}

