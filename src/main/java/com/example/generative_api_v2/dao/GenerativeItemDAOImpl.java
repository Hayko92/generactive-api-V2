package com.example.generative_api_v2.dao;

import com.example.generative_api_v2.db.hibernate.HibernateSessionFactoryUtil;
import com.example.generative_api_v2.model.Generative;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenerativeItemDAOImpl implements GenerativeItemDAO {

    private final SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionfactory();

    public GenerativeItemDAOImpl() {
    }

    @Override
    public void add(Generative item) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(item);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Generative> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Generative> allItems = session.createQuery("SELECT a FROM Generative a", Generative.class).getResultList();
        transaction.commit();
        return allItems;

    }

    @Override
    public List<Generative> getItemsWithPriceFromTo(int priceFrom, int to) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Generative> query = session.createQuery("SELECT a FROM Generative a where a .price between :from and :to", Generative.class);
        query.setParameter("from", priceFrom);
        query.setParameter("to", to);
        List<Generative> resultList = query.getResultList();
        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public Generative getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Generative item = session.get(Generative.class, id);
        transaction.commit();
        session.close();
        return item;
    }

    @Override
    public void deleteById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Generative item = session.get(Generative.class, id);
        session.remove(item);
        transaction.commit();
        session.close();
    }

    @Override
    public void updateById(Generative item) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(item);
        transaction.commit();
        session.close();
    }

    @Override
    public Generative getLastAdded() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Generative> query = session.createQuery("select i from Generative i order by i.id desc ");
        query.setMaxResults(1);
        List<Generative> resultList = query.getResultList();
        if (resultList != null) return resultList.get(0);
        else return null;
    }

    public Generative findLastItem() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Generative> items = session.createQuery("SELECT a FROM Generative a where a.id=max(id)", Generative.class).getResultList();
        transaction.commit();
        session.close();
        if (items != null) return items.get(0);
        else return null;
    }

}
