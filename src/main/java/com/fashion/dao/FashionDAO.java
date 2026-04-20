package com.fashion.dao;

import com.fashion.model.FashionItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class FashionDAO {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public void saveFashionItem(FashionItem item) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        }
    }

    public List<FashionItem> getAllFashionItems() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from FashionItem", FashionItem.class).list();
        }
    }

    // Змінюємо параметр з int на Long
    public FashionItem getFashionItemById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(FashionItem.class, id);
        }
    }

    public void updateFashionItem(FashionItem item) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(item);
            session.getTransaction().commit();
        }
    }

    // Змінюємо параметр з int на Long
    public void deleteFashionItem(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            FashionItem item = session.get(FashionItem.class, id);
            if (item != null) {
                session.delete(item);
            }
            session.getTransaction().commit();
        }
    }
}