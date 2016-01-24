/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.util;

import com.bytes.thinkr.model.entity.IEntity;
import com.bytes.thinkr.model.entity.account.Client;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Kent on 1/10/2016.
 */
public class HibernateUtil {

    // This class should use FINER or lower logging level
    private static final Logger LOGGER = Logger.getLogger(HibernateUtil.class.getName());

    private static final SessionFactory sessionFactory;
    public static final int JDBC_PATCH_SIZE = 100;

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
     * Request to perform an SQL retrieveByQuery
     * @param query the SQL retrieve query
     * @param <T> the Entity type
     * @return the result list
     */
    public static <T extends IEntity> List<T> retrieveByQuery(String query) {

        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER, "Request to retrieve by query: " + query);
        }

        Transaction transaction = null;
        List<T> entities = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            if (LOGGER.isLoggable(Level.FINEST)) {
                LOGGER.log(Level.FINEST, "Executing retrieve query: " + query);
            }

            entities = session.createQuery(query.toString()).list();

        } catch (HibernateException e) {
            transaction.rollback();
            LOGGER.log(Level.SEVERE, "Unable to retrieve by query.", e);
        }

        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER,
                "Successfully retrieved by query: " + entities.size() + " rows.");
        }

        return entities;
    }

    /**
     * Request to perform a delete via SQL query
     * @param query the SQL retrieve query
     * @return true if successful
     */
    public static boolean deleteByQuery(String query) {

        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER, "Request to delete by query: " + query);
        }

        int affectedRows = 0;
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            if (LOGGER.isLoggable(Level.FINEST)) {
                LOGGER.log(Level.FINEST, "Executing delete by query: " + query);
            }

            tx = session.beginTransaction();
            affectedRows = session.createQuery(query).executeUpdate();
            tx.commit();

        } catch (HibernateException e) {
            tx.rollback();
            LOGGER.log(Level.SEVERE, "Unable to delete by query.", e);
        }

        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER, "Successfully delete by query.");
        }

        return affectedRows > 0;
    }


    /**
     *
     * @param entityName
     * @param <T>
     * @return
     */
    public static <T> List<T> retrieveAllByName(String entityName) {

        List<T> entities = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        String query = "from " + entityName;
        try {
            transaction = session.beginTransaction();
            entities = session.createQuery(query).list();

        } catch (HibernateException e) {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return entities;
    }

    /**
     * Request to retrieve all rows of the specified entity
     * @param entityType the entity class
     * @param <T> an IEntity type
     * @return the list of data for the specified entity
     */
    public static <T extends IEntity> List<T> retrieveAll(Class entityType) {

        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER,
                "Request to retrieve all data for : " + entityType.getSimpleName());
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<T> list = null;
        Transaction transaction = null;
        try {

            transaction = session.beginTransaction();
            list = session.createCriteria(entityType).list();

        } catch (HibernateException e) {
            if (transaction != null) { transaction.rollback(); }
            LOGGER.log(
                    Level.SEVERE,
                    "Unable to perform retrieveAll for : " + entityType.getSimpleName(), e);
        } finally {
            session.close();
        }

        if (list == null) {
            list = new ArrayList<>();
            if (LOGGER.isLoggable(Level.FINER)) {
                LOGGER.log(Level.FINER, "No data found for: " + entityType.getSimpleName());
            }
        } else {
            if (LOGGER.isLoggable(Level.FINER)) {
                LOGGER.log(Level.FINER,
                    "Successfully retrieved " + list.size() + " rows for " + entityType.getSimpleName());
            }
        }

        return list;
    }

    /**
     *
     * @param entity
     * @return
     */
    public static boolean commit(IEntity entity) {

        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER,
                "Request to commit data for : " + entity);
        }

        boolean result = false;
        Session session = getSessionFactory().openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            entity.setId((Long) session.save(entity));
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) { tx.rollback(); }
            result = false;
            LOGGER.log(Level.SEVERE, "Unable to commit data for : " + entity, e);
        } finally {
            session.close();
        }

        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER, "Data committed : " + result);
        }

        return result;
    }


    /**
     *
     * @param entities
     * @return
     */
    public static <T extends IEntity> boolean commit(List<T> entities) {

        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER, "Preparing to commit " + entities.size() + " entities");
        }

        boolean result = false;
        Session session = getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            int i = 1;
            for (IEntity entity : entities) {

                entity.setId((Long) session.save(entity));

                if (LOGGER.isLoggable(Level.FINEST)) {
                    LOGGER.log(Level.FINEST, "saving entity: " + entities.toString() + ". Id: " + entity.getId());
                }

                if (i++ % JDBC_PATCH_SIZE == 0) {
                    session.flush();
                    session.clear();
                }
            }
            result = true;
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) { tx.rollback(); }
            result = false;
            LOGGER.log(Level.SEVERE, "Unable to commit", e);
        } finally {
            session.close();
        }

        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER, "Successfully committed " + entities.size() + " entities");
        }
        return result;
    }

    /**
     *
     * @param entities
     * @param <T>
     * @return
     */
    public static <T extends IEntity> boolean delete(List<T> entities) {

        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER, "Preparing to delete " + entities.size() + " entities");
        }

        Transaction tx = null;
        try (Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            int i = 1;
            for (T entity : entities) {
                if (LOGGER.isLoggable(Level.FINEST)) {
                    LOGGER.log(Level.FINEST, "deleting entity: " + entities.toString() + ". Id: " + entity.getId());
                }

                session.delete(entity);

                if (i++ % HibernateUtil.JDBC_PATCH_SIZE == 0) {
                    session.flush();
                    session.clear();
                }
            }
            tx.commit();
        } catch (HibernateException e) {

            if (tx != null) {
                tx.rollback();
            }
            LOGGER.log(Level.SEVERE, "Unable to delete entity", e);
            return false;
        }

        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER, "Successfully deleted " + entities.size() + " entities");
        }
        return true;
    }

    /**
     *
     * @param entityClass
     * @param entityId
     * @param <T>
     * @return
     */
    public static <T extends IEntity> T getEntity(Class<T> entityClass, Long entityId) {

        T entity = null;
        Transaction tx = null;
        try (Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            entity = session.get(entityClass, entityId);
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOGGER.log(Level.SEVERE, "Unable to find entity", e);
        }

        return entity;
    }
}
