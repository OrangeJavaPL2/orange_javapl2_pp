package com.sda.db;

import com.sda.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static Session openSession() {
        return createSessionFactory().openSession();
    }

    private static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = getSessionFactory();
        }
        return sessionFactory;
    }

    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(User.class);

        return configuration.buildSessionFactory();
    }
}
