package com.example.generative_api_v2.dao;

import com.example.generative_api_v2.conf.ApplicationContext;
import com.example.generative_api_v2.db.hibernate.HibernateSessionFactoryUtil;
import com.example.generative_api_v2.model.Group;
import com.example.generative_api_v2.model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class GroupDAOImpl implements GroupDAO{
    SessionFactory sessionFactory = ApplicationContext.context.getBean("getSessionfactory",SessionFactory.class);

    @Override
    public void add(Group group) {
       // SessionFactory sessionFactory = ApplicationContext.context.getBean("getSessionfactory",SessionFactory.class);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(group);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Group> getAll() {
      //  SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Group> allItems = session.createQuery("SELECT g  FROM Group g", Group.class).getResultList();
        transaction.commit();
        return allItems;
    }

    @Override
    public Group getById(int id) {
     //   SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Group group = session.get(Group.class, id);
        transaction.commit();
        session.close();
        return group;
    }

    @Override
    public void deleteById(int id) {
     //   SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Group group = session.get(Group.class, id);
        session.remove(group);
        transaction.commit();
        session.close();
    }

    @Override
    public void updateById(Group group) {
     //   SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(group);
        transaction.commit();
        session.close();
    }

    @Override
    public Group getLastAdded() {
      //  SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Group> query = session.createQuery("select g from Group g order by g.id desc ");
        query.setMaxResults(1);
        List<Group> resultList = query.getResultList();
        if (resultList != null) return resultList.get(0);
        else return null;
    }
}
