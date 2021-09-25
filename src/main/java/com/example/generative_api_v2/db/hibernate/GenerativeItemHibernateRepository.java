package com.example.generative_api_v2.db.hibernate;

import com.example.generative_api_v2.conf.ApplicationContext;
import com.example.generative_api_v2.dao.GenerativeItemDAOImpl;
import com.example.generative_api_v2.model.Generative;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class GenerativeItemHibernateRepository {

    private     GenerativeItemDAOImpl itemDAO  =new GenerativeItemDAOImpl();
@Autowired
    public GenerativeItemHibernateRepository(GenerativeItemDAOImpl itemDAO) {
        this.itemDAO = itemDAO;
    }

    public   void save(Generative item) {
        itemDAO.add(item);
    }

    public   List<Generative> getAll() {
        return itemDAO.getAll();
    }

    public   void deleteById(int id) {
        itemDAO.deleteById(id);
    }

    public   Generative getById(int id) {
        return itemDAO.getById(id);
    }

    public   void updateById(Generative item) {
        itemDAO.updateById(item);
    }

    public   List<Generative> getItemsWithPriceFromTo(int from, int to) {
        return itemDAO.getItemsWithPriceFromTo(from, to);
    }

//    public static void clear( ) {
//        SessionFactory sessionFactory = ApplicationContext.context.getBean("getSessionfactory",SessionFactory.class);
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        session.createQuery("delete  from Generative ").executeUpdate();
//        session.close();
//    }

    public   Generative findLastAdded() {
      return  itemDAO.getLastAdded();
    }


    public GenerativeItemHibernateRepository() {
    }
}
