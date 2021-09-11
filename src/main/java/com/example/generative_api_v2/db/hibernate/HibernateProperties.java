package com.example.generative_api_v2.db.hibernate;

import java.io.IOException;
import java.util.Properties;

public class HibernateProperties {
    private static final Properties properties = new Properties();

    public static Properties getProperties() {
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private HibernateProperties() {
    }
}
