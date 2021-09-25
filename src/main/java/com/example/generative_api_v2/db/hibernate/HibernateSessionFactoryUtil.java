package com.example.generative_api_v2.db.hibernate;

import com.example.generative_api_v2.model.Generative;
import com.example.generative_api_v2.model.Group;
import com.example.generative_api_v2.model.Item;
import com.example.generative_api_v2.model.Stock;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateSessionFactoryUtil {

    private HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionfactory() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Configuration.class);
        configuration.addAnnotatedClass(Item.class);
        configuration.addAnnotatedClass(Generative.class);
        configuration.addAnnotatedClass(Stock.class);
        configuration.addAnnotatedClass(Group.class);
        return configuration.buildSessionFactory();
    }

}
