/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.util;

import com.bytes.thinkr.model.entity.IEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by Kent on 1/10/2016.
 */
public class HibernateUtil {


    private static final SessionFactory sessionFactory;
    private static final int JDBC_PATCH_SIZE = 100;

    static {
        try {
            sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     *
     * @param entityName
     * @param <T>
     * @return
     */
    public  <T> List<T> retrieve(String entityName) {

        List<T> entities = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        String query = "from " + entityName;
        try {
            transaction = session.beginTransaction();
            entities = session.createQuery(query).list();

        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return entities;
    }

    /**
     *
     * @param entity
     * @return
     */
    public static boolean commit(IEntity entity) {

        boolean result = false;
        Session session = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            entity.setId((Long) session.save(entity));
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            result = false;
            e.printStackTrace();
        } finally {
            session.close();
        }

        return result;
    }


    /**
     *
     * @param entities
     * @return
     */
    public static <T extends IEntity> boolean commit(List<T> entities) {

        boolean result = false;
        Session session = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            int i = 0;
            for (IEntity entity : entities) {
                entity.setId((Long) session.save(entity));
                if (i++ % JDBC_PATCH_SIZE == 0) {
                    session.flush();
                    session.clear();
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            result = false;
            e.printStackTrace();
        } finally {
            session.close();
        }

        return result;
    }

}
