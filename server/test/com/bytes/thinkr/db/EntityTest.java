/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.db;

import com.bytes.thinkr.model.entity.IEntity;
import com.bytes.thinkr.model.factory.EntityFactory;
import com.bytes.thinkr.model.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by Kent on 1/14/2016.
 */
public abstract class EntityTest<T extends IEntity> {

    protected static final int  JDBC_PATCH_SIZE = 100;

    /**
     *
     * @param entityFactory
     */
    protected void create(EntityFactory<T> entityFactory, int entityCount) {

        List<T> entities = entityFactory.generate(entityCount);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            int i = 0;
            for (T entity : entities) {

                beforeEntitySave(entity);
                entity.setId((Long) session.save(entity));
                if (i++ % JDBC_PATCH_SIZE == 0) {
                    session.flush();
                    session.clear();
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     *
     * @param entityName
     * @param factory
     */
    protected List<T> retrieve(String entityName, EntityFactory factory) {

        List<T> entities = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        String query = "from " + entityName;
        try {
            transaction = session.beginTransaction();
            entities = session.createQuery(query).list();
            for (T entity : entities) {
                System.out.println(factory.getDisplayInfo(entity));
            }
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
     */
    protected void beforeEntitySave(T entity) {}
}
