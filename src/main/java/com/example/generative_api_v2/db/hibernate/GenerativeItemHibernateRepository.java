package com.example.generative_api_v2.db.hibernate;

import com.example.generative_api_v2.dao.GenerativeItemDAO;
import com.example.generative_api_v2.dao.GenerativeItemDAOImpl;
import com.example.generative_api_v2.dao.ItemDAO;
import com.example.generative_api_v2.dao.ItemDAOImpl;
import com.example.generative_api_v2.model.Generative;
import com.example.generative_api_v2.model.Item;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public final class GenerativeItemHibernateRepository {
    private static final GenerativeItemDAO itemDAO = new GenerativeItemDAOImpl();

    private GenerativeItemHibernateRepository() {
    }

    public static void save(Generative item) {
        itemDAO.add(item);
    }

    public static List<Generative> getAll() {
        return itemDAO.getAll();
    }

    public static void deleteById(int id) {
        itemDAO.deleteById(id);
    }

    public static Generative getById(int id) {
        return itemDAO.getById(id);
    }

    public static void updateById(Generative item) {
        itemDAO.updateById(item);
    }

    public static List<Generative> getItemsWithPriceFromTo(int from, int to) {
        return itemDAO.getItemsWithPriceFromTo(from, to);
    }

    public static void clear( ) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.createQuery("delete  from Generative ").executeUpdate();
        session.close();
    }

    public static Generative findLastAdded() {
      return  itemDAO.getLastAdded();
    }
}
