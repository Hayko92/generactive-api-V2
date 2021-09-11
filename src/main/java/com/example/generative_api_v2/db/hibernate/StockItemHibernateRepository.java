package com.example.generative_api_v2.db.hibernate;

import com.example.generative_api_v2.dao.ItemDAO;
import com.example.generative_api_v2.dao.ItemDAOImpl;
import com.example.generative_api_v2.model.Item;
import org.hibernate.Session;

import java.sql.Connection;
import java.util.List;

public final class StockItemHibernateRepository {
    private static final ItemDAO itemDAO = new ItemDAOImpl();

    private StockItemHibernateRepository() {
    }

    public static void save(Item item) {
        itemDAO.add(item);
    }

    public static List<Item> getAll() {
        return itemDAO.getAll();
    }

    public static void deleteById(int id) {
        itemDAO.deleteById(id);
    }

    public static Item getById(int id) {
        return itemDAO.getById(id);
    }

    public static void updateById(Item item) {
        itemDAO.updateById(item);
    }

    public static List<Item> getItemsWithPriceFromTo(int from, int to) {
        return itemDAO.getItemsWithPriceFromTo(from, to);
    }

    public static void clear( ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.createQuery("delete  from Item").executeUpdate();
        session.close();
    }

    public static Item findLastAdded() {
      return  itemDAO.getLastAdded();
    }
}
