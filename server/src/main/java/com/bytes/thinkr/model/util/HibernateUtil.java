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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kent on 1/10/2016.
 */
public class HibernateUtil {

    // This class should use FINER or lower logging level
    private static final Logger LOG = LoggerFactory.getLogger(HibernateUtil.class.getName());

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

        LOG.debug("Request to retrieve by query: {}", query);

        Transaction transaction = null;
        List<T> entities = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            LOG.debug("Executing retrieve query: {}", query);

            entities = session.createQuery(query.toString()).list();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error("Unable to retrieve by query.", e);
        }

        LOG.debug("Successfully retrieved : {} rows by query.", entities.size());
        return entities;
    }

    /**
     * Request to perform a delete via SQL query
     * @param query the SQL retrieve query
     * @return true if successful
     */
    public static boolean deleteByQuery(String query) {

        LOG.debug("Request to delete by query: {}", query);

        int affectedRows = 0;
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            LOG.debug("Executing delete by query: {}", query);

            tx = session.beginTransaction();
            affectedRows = session.createQuery(query).executeUpdate();
            tx.commit();

        } catch (HibernateException e) {
            tx.rollback();
            LOG.error("Unable to delete by query.", e);
        }

        LOG.debug("Successfully delete by query.");
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

        LOG.debug("Request to retrieve all data for: {}", entityType.getSimpleName());

        List<T> list = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            list = session.createCriteria(entityType).list();

        } catch (HibernateException e) {
            LOG.error("Unable to perform retrieveAll for: {}", entityType.getSimpleName(), e);
            if (transaction != null) {
                transaction.rollback();
            }
        }

        if (list == null) {
            list = new ArrayList<>();
            LOG.debug("No data found for: {}", entityType.getSimpleName());
        } else {
            LOG.debug("Successfully retrieved {} rows for {} ",
                list.size(), entityType.getSimpleName());
        }

        return list;
    }

    /**
     *
     * @param entity
     * @return
     */
    public static boolean commit(IEntity entity) {

        LOG.debug("Request to commit data for: {}", entity);

        boolean result = false;
        Transaction tx = null;
        try (Session session = getSessionFactory().openSession()) {

            tx = session.beginTransaction();
            entity.setId((Long) session.save(entity));
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            result = false;
            LOG.error("Unable to commit data for: {}", entity, e);
        }

        LOG.debug("Data committed: {}", result);
        return result;
    }


    /**
     *
     * @param entities
     * @return
     */
    public static <T extends IEntity> boolean commit(List<T> entities) {

        LOG.debug("Preparing to commit {} entities", entities.size());

        boolean result;
        Transaction tx = null;
        try (Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            int i = 1;
            for (IEntity entity : entities) {
                if (session.contains(entity))
                {
                    entity.setId((Long) session.save(entity));
                    LOG.debug("saving entity: {}. Id: {}", entity.getClass().getSimpleName(), entity.getId());
                } else {
                    session.saveOrUpdate(entity);
                    LOG.debug("updated entity: {}. Id: {}", entity.getClass().getSimpleName(), entity.getId());
                }

                if (i++ % JDBC_PATCH_SIZE == 0) {
                    session.flush();
                    session.clear();
                }
            }
            result = true;
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            result = false;
            LOG.error("Unable to commit", e);
        }

        LOG.debug("Successfully committed {} entities.", entities.size());
        return result;
    }

    /**
     *
     * @param entities
     * @param <T>
     * @return
     */
    public static <T extends IEntity> boolean delete(List<T> entities) {

        LOG.debug("Preparing to delete " + entities.size() + " entities");
        Transaction tx = null;
        try (Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            int i = 1;
            for (T entity : entities) {
                LOG.debug("deleting entity: {} Id: {}", entity.getClass().getSimpleName(), entity.getId());

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
            LOG.error("Unable to delete entity",  e);
            return false;
        }

        LOG.debug("Successfully deleted {} entities", entities.size());
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
            LOG.error("Unable to find entity", e);
        }

        return entity;
    }

    /**
     *
     * @param entity
     * @param <T>
     * @return
     */
    public static <T extends IEntity> boolean merge(T entity) {

        LOG.debug("Request to merge entity: " + entity);

        boolean result;
        Transaction tx = null;
        try (Session session = getSessionFactory().openSession()) {

            tx = session.beginTransaction();
            session.saveOrUpdate(entity);
            tx.commit();
            result = true;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            result = false;
            LOG.error("Unable to merge data for: {}", entity, e);
        }

        LOG.debug("Entity updated: {}", result);
        return result;

    }
}
