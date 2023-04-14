package com.sda.dao;

import com.sda.db.HibernateUtils;
import com.sda.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UsersDAO {

    public List<User> findAll() {
        try (Session session = HibernateUtils.openSession()) {
//            String query = "FROM User";
            String query = "SELECT u FROM User u";
            return session.createQuery(query, User.class).list();
        }
    }

    public User findByUsername(String username) {
        try (Session session = HibernateUtils.openSession()) {
            return session.find(User.class, username);
        }
    }

    public void create(User user) {
//        Session session = HibernateUtils.openSession();
//        session.close();

        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        }
    }

    public boolean deleteByUsername(String username) {
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.find(User.class, username);
            boolean exists = user != null;

            if (exists) {
                session.remove(user);
            }
            transaction.commit();
            return exists;
        }
    }

    public User update(User updatedUser) {
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.merge(updatedUser);
            transaction.commit();
            return user;
        }
    }

    public boolean exists(String username) {
        try (Session session = HibernateUtils.openSession()) {
            String query = "SELECT count(1) FROM users WHERE username = :id";

            Integer result = session.createNativeQuery(query, Integer.class)
                    .setParameter("id", username)
                    .uniqueResult();

            return result > 0;
        }
    }
}
