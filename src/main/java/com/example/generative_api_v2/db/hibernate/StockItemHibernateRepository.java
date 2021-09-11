package com.example.generative_api_v2.db.hibernate;

import com.example.generative_api_v2.dao.ItemDAO;
import com.example.generative_api_v2.dao.ItemDAOImpl;
import com.example.generative_api_v2.model.Item;

public final class StockItemHibernateRepository {
    private static final ItemDAO itemDAO = new ItemDAOImpl();

    private StockItemHibernateRepository() {
    }
    public static void save(Item item) {
        itemDAO.add(item);
    }

}
