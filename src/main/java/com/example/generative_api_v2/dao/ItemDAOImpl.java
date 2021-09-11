package com.example.generative_api_v2.dao;

import com.example.generative_api_v2.db.hibernate.HibernateSessionFactoryUtil;
import com.example.generative_api_v2.model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ItemDAOImpl implements ItemDAO {


    @Override
    public void add(Item item) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(item);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Item> getAll() {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Item> allItems = session.createQuery("SELECT a FROM Item a", Item.class).getResultList();
        transaction.commit();
        return allItems;

    }

    @Override
    public List<Item> getItemsWithPriceFromTo(int priceFrom, int to) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Item> query = session.createQuery("SELECT a FROM Item a where a .price between :from and :to", Item.class);
        query.setParameter("from", priceFrom);
        query.setParameter("to", to);
        List<Item> resultList = query.getResultList();
        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public Item getById(int id) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Item item = session.get(Item.class, id);
        transaction.commit();
        session.close();
        return item;
    }

    @Override
    public void deleteById(int id) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Item item = session.get(Item.class, id);
        session.remove(item);
        transaction.commit();
        session.close();
    }

    @Override
    public void updateById(Item item) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(item);
        transaction.commit();
        session.close();
    }

    @Override
    public Item getLastAdded() {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Item> query = session.createQuery("select i from Item i order by i.id desc ");
        query.setMaxResults(1);
        List<Item> resultList = query.getResultList();
        if (resultList != null) return resultList.get(0);
        else return null;


    }


    public Item findLastItem() {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Item> items = session.createQuery("SELECT a FROM Item a where a.id=max(id)", Item.class).getResultList();
        transaction.commit();
        session.close();
        if (items != null) return items.get(0);
        else return null;
    }
}
