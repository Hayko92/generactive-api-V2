package com.example.generative_api_v2.db.hibernate;

import com.example.generative_api_v2.dao.GroupDAO;
import com.example.generative_api_v2.dao.GroupDAOImpl;
import com.example.generative_api_v2.model.Group;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupHibernateRepository {
    private GroupDAO groupDAO;

    public GroupHibernateRepository() {
    }

    @Autowired
    public void setGroupDAO(GroupDAOImpl groupDAO) {
        this.groupDAO = groupDAO;
    }

    public List<Group> getAll() {
        return groupDAO.getAll();
    }

    public Group add(Group group) {
        groupDAO.add(group);
        return group;
    }

    public void removeById(int id) {
        groupDAO.deleteById(id);
    }

    public Group getById(int id) {
        return groupDAO.getById(id);
    }

    public void updateById(Group group) {
        groupDAO.updateById(group);
    }

    public void clear() {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionfactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete  from Group").executeUpdate();
        session.close();
    }

    public Group getLastAdded() {
        return groupDAO.getLastAdded();
    }

}
