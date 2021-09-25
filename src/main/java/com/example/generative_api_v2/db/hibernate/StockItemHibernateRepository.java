package com.example.generative_api_v2.db.hibernate;

import com.example.generative_api_v2.dao.ItemDAOImpl;
import com.example.generative_api_v2.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class StockItemHibernateRepository {
    private ItemDAOImpl itemDAO;

    public StockItemHibernateRepository() {
    }

    @Autowired
    public void setItemDAO(ItemDAOImpl itemDAO) {
        this.itemDAO = itemDAO;
    }

    public void save(Item item) {
        itemDAO.add(item);
    }

    public List<Item> getAll() {
        return itemDAO.getAll();
    }

    public void deleteById(int id) {
        itemDAO.deleteById(id);
    }

    public Item getById(int id) {
        return itemDAO.getById(id);
    }

    public void updateById(Item item) {
        itemDAO.updateById(item);
    }

    public List<Item> getItemsWithPriceFromTo(int from, int to) {
        return itemDAO.getItemsWithPriceFromTo(from, to);
    }

//    public static void clear( ) {
//        SessionFactory sessionFactory = ApplicationContext.context.getBean("getSessionfactory",SessionFactory.class);
//        Session session =sessionFactory.openSession();
//        session.beginTransaction();
//        session.createQuery("delete  from Item").executeUpdate();
//        session.close();
//    }

    public   Item findLastAdded() {
        return itemDAO.getLastAdded();
    }
}
