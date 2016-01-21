/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.entity.IEntity;
import com.bytes.thinkr.model.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Kent on 1/13/2016.
 */
public abstract class EntityFactory<T extends IEntity> {

    private static final int JDBC_PATCH_SIZE = 10;
    private static final Logger LOGGER = Logger.getLogger(EntityFactory.class.getName());

    private Class entityType;

    /**
     * Dynamically determine the type of the generic.
     */
    public EntityFactory() {

        // Obtain the class of T at runtime
        this.entityType = (Class) ((ParameterizedType)
            getClass().getGenericSuperclass()).getActualTypeArguments()[0]; // T
    }

    /**
     * TODO consider changing Long to Serializable
     * Request to retrieve the entity matching the specified id
     * @param id the entity id
     * @return the entity
     */
    public T findById(Long id) {
        return findByIdList(new Long[] {id}).get(0);
    }

    /**
     * Reuqest to retrieve the list of entities matching the specified ids.
     * @param ids the list of entity ids
     * @return the list of entities
     */
    public List<T> findByIdList(Long[] ids) {

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Preparing to retrieve " + ids.length + " entities");
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        // Construct this query from the id list
        //  "from Entity e where et.id in('1','2','3')"
        StringBuilder query = new StringBuilder();
        query.append("from " + entityType.getSimpleName() + " e where e.id in(");
        for (Long id : ids) { query.append("'"+id+"',"); }
        query.setCharAt(query.length()-1, ')');

        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER, query.toString());
        }

        List<T> entities = null;
        try {
            transaction = session.beginTransaction();
            entities = session.createQuery(query.toString()).list();
        } catch (HibernateException e) {
            transaction.rollback();
            LOGGER.log(Level.SEVERE, "Unable to retrieve data.", e);
        } finally {
            session.close();
        }

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Successfully retrieved " + entities.size()+ " entities");
        }

        return entities;
    }

    public List<T> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return (List<T>) session.createCriteria(entityType).list();
    }

    /**
     * Request to save the specified entity
     * @param entity the entity to be persisted
     * @return true if the entity was successfully persisted
     */
    public boolean save(T entity) {

        List<T> entities = new ArrayList<>();
        entities.add(entity);
        return saveAll(entities);
    }

    /**
     * Request to save the specified entities
     * @param entities the entities to be persisted
     * @return true if the entities were successfully persisted
     */
    public boolean saveAll(List<T> entities) {

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Preparing to commit " + entities.size() + " entities");
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            int i = 0;

            saveSubEntities(entities);
            for (T entity : entities) {

                entity.setId((Long) session.save(entity));

                if (LOGGER.isLoggable(Level.FINEST)) {
                    LOGGER.log(Level.FINEST, "saving entity: " + entities.toString() + ". Id: " + entity.getId());
                }

                if (i++ % JDBC_PATCH_SIZE == 0) {
                    session.flush();
                    session.clear();
                }
            }
            tx.commit();
        } catch (HibernateException e) {

            tx.rollback();
            LOGGER.log(Level.SEVERE, "Unable to save entity", e);
            return false;
        } finally {
            session.close();
        }

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Successfully committed " + entities.size() + " entities");
        }
        return true;
    }

    protected abstract boolean saveSubEntities(List<T> entity);

    /**
     * Request to delete the specified entity
     * @param entity the entity to be deleted
     * @return true if the entity was successfully deleted
     */
    public boolean delete(T entity) {
        List<T> entities = new ArrayList<>();
        entities.add(entity);
        return deleteAll(entities);
    }

    /**
     * Request to delete the specified entities
     * @param entities the entities to be deleted
     * @return true if the entities were successfully deleted
     */
    public boolean deleteAll(List<T> entities) {

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Preparing to delete " + entities.size() + " entities");
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            int i = 0;
            for (T entity : entities) {
                if (LOGGER.isLoggable(Level.FINEST)) {
                    LOGGER.log(Level.FINEST, "deleting entity: " + entities.toString() + ". Id: " + entity.getId());
                }

                session.delete(entity);

                if (i++ % JDBC_PATCH_SIZE == 0) {
                    session.flush();
                    session.clear();
                }
            }
            tx.commit();
        } catch (HibernateException e) {

            tx.rollback();
            LOGGER.log(Level.SEVERE, "Unable to delete entity", e);
            return false;
        } finally {
            session.close();
        }

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Successfully deleted " + entities.size() + " entities");
        }
        return true;
    }
}
