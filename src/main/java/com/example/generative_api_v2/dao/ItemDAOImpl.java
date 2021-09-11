package com.example.generative_api_v2.dao;

import com.example.generative_api_v2.db.hibernate.HibernateSessionFactoryUtil;
import com.example.generative_api_v2.dto.ItemDTO;
import com.example.generative_api_v2.model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
        return null;
    }

    @Override
    public List<Item> getItemsWithPriceFromTo(int from, int to) {
        return null;
    }

    @Override
    public Item getById(int id) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void updateById(int id, ItemDTO itemDTO) {

    }

    @Override
    public Item findLastItem() {
        return null;
    }
}
