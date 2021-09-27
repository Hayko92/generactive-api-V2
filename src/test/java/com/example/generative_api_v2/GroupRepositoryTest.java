package com.example.generative_api_v2;

import com.example.generative_api_v2.conf.ApplicationContext;
import com.example.generative_api_v2.db.hibernate.GroupHibernateRepository;
import com.example.generative_api_v2.db.hibernate.StockItemHibernateRepository;
import com.example.generative_api_v2.model.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupRepositoryTest {

    private GroupHibernateRepository groupHibernateRepository;
    @BeforeEach
    protected   void clearItemAndGroupTables() {
        groupHibernateRepository = ApplicationContext.context.getBean("groupHibernateRepository",GroupHibernateRepository.class);
        StockItemHibernateRepository.clear();
        groupHibernateRepository.clear();

    }

    @Test
    public void addTest() {
        groupHibernateRepository = ApplicationContext.context.getBean("groupHibernateRepository",GroupHibernateRepository.class);
        Group group = new Group("test_name");
        groupHibernateRepository.add(group);
        Group received = groupHibernateRepository.getLastAdded();
        assertEquals(group.getTitle(), received.getTitle());
    }

    @Test
    public void getAllTest() {
        groupHibernateRepository = ApplicationContext.context.getBean("groupHibernateRepository",GroupHibernateRepository.class);

        Group group1 = new Group("test_name");
        Group group2 = new Group("test_name");
        Group group3 = new Group("test_name");
        groupHibernateRepository.add(group1);
        groupHibernateRepository.add(group2);
        groupHibernateRepository.add(group3);
        List<Group> groups = groupHibernateRepository.getAll();
        assertEquals(3, groups.size());
    }

    @Test
    public void findGroupByIdTest() {
        Group group1 = new Group("test_name");
        Group group2 = new Group("test_name");
        groupHibernateRepository.add(group1);
        groupHibernateRepository.add(group2);
        Group groupReceived1 = groupHibernateRepository.getById(group1.getId());
        Group groupReceived2 = groupHibernateRepository.getById(group2.getId());
        assertEquals(group1.getTitle(), groupReceived1.getTitle());
        assertEquals(group2.getTitle(), groupReceived2.getTitle());
    }

    @Test
    public void removeByIdTest() {
        Group group = new Group("test_name");
        groupHibernateRepository.add(group);
        int generatedId = group.getId();
        assertNotNull(groupHibernateRepository.getById(generatedId));
        groupHibernateRepository.removeById(generatedId);
        assertNull(groupHibernateRepository.getById(generatedId));
    }

    @Test
    public void updateByIdTest() {
        Group group =new Group("Test_title");
        String nameBeforeChanging = group.getTitle();
        groupHibernateRepository.add(group);
        int generatedId = group.getId();
        group.setTitle("changedname");
        groupHibernateRepository.updateById(group);
        Group groupAfterUpdate = groupHibernateRepository.getById(generatedId);
        assertNotEquals(group.getTitle(), nameBeforeChanging);
        assertEquals(groupAfterUpdate.getTitle(),group.getTitle());
    }

}