package com.example.generative_api_v2.db.hibernate;

import com.example.generative_api_v2.model.Generative;
import com.example.generative_api_v2.model.Group;
import com.example.generative_api_v2.model.Item;
import com.example.generative_api_v2.model.Stock;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Configuration.class);
                configuration.addAnnotatedClass(Item.class);
               configuration.addAnnotatedClass(Generative.class);
               configuration.addAnnotatedClass(Stock.class);
               configuration.addAnnotatedClass(Group.class);
                configuration.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQL10Dialect");
                configuration.setProperty("hibernate.connection.driver_class","org.postgresql.Driver");
                configuration.setProperty("hibernate.connection.url","jdbc:postgresql://localhost:5432/postgres");
                configuration.setProperty("hibernate.connection.username","postgres");
                configuration.setProperty("hibernate.connection.password","123456");
                configuration.setProperty("hibernate.show_sql.password","true");
                configuration.setProperty("hibernate.format_sql","true");
                configuration.setProperty("hibernate.show_sql","true");
                sessionFactory =configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}