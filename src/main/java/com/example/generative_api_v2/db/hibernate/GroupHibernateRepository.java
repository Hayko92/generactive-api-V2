package com.example.generative_api_v2.db.hibernate;

import com.example.generative_api_v2.dao.GroupDAO;
import com.example.generative_api_v2.dao.GroupDAOImpl;
import com.example.generative_api_v2.model.Group;
import org.hibernate.Session;

import java.util.List;

public class GroupHibernateRepository {
    static GroupDAO groupDAO = new GroupDAOImpl();

    public static List<Group> getAll() {
        return groupDAO.getAll();
    }

    public static void add(Group group) {
        groupDAO.add(group);
    }

    public static void removeById(int id) {
        groupDAO.deleteById(id);
    }

    public static Group getById(int id) {
        return groupDAO.getById(id);
    }

    public static void updateById(Group group) {
        groupDAO.updateById(group);
    }

    public static void clear() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.createQuery("delete  from Group").executeUpdate();
        session.close();
    }

    public static Group getLastAdded() {
       return groupDAO.getLastAdded();
    }
}
