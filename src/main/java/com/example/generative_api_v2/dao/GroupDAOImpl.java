package com.example.generative_api_v2.dao;

import com.example.generative_api_v2.db.hibernate.HibernateSessionFactoryUtil;
import com.example.generative_api_v2.model.Group;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupDAOImpl implements GroupDAO {

    private final SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionfactory();

    public GroupDAOImpl() {
    }

    @Override
    public void add(Group group) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(group);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Group> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Group> allItems = session.createQuery("SELECT g  FROM Group g", Group.class).getResultList();
        transaction.commit();
        return allItems;
    }

    @Override
    public Group getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Group group = session.get(Group.class, id);
        transaction.commit();
        session.close();
        return group;
    }

    @Override
    public void deleteById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Group group = session.get(Group.class, id);
        session.remove(group);
        transaction.commit();
        session.close();
    }

    @Override
    public void updateById(Group group) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(group);
        transaction.commit();
        session.close();
    }

    @Override
    public Group getLastAdded() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Group> query = session.createQuery("select g from Group g order by g.id desc ");
        query.setMaxResults(1);
        List<Group> resultList = query.getResultList();
        if (resultList != null) return resultList.get(0);
        else return null;
    }

}
