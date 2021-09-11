package com.example.generative_api_v2.db.hibernate;

import com.example.generative_api_v2.model.Generative;
import com.example.generative_api_v2.model.Item;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.addAnnotatedClass(Item.class);
                configuration.addAnnotatedClass(Generative.class);
                configuration.addAnnotatedClass(Configuration.class);
                configuration.setProperties(HibernateProperties.getProperties());
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
                        applySettings(configuration.getProperties());

                sessionFactory =configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
