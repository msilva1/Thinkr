/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.factory;

import com.bytes.thinkr.model.FactoryResponse;
import com.bytes.thinkr.model.FactoryResponseList;
import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.entity.IEntity;
import com.bytes.thinkr.factory.merge.MergeFactory;
import com.bytes.thinkr.model.util.HibernateUtil;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Kent on 1/13/2016.
 */
public abstract class EntityFactory<T extends IEntity> {

    // This class can use FINE or lower logging level
    private static final Logger LOGGER = Logger.getLogger(EntityFactory.class.getName());

    private Class<T> entityType;

    /**
     * Dynamically determine the type of the generic.
     */
    public EntityFactory() {

        // Obtain the class of T at runtime
        this.entityType = (Class<T>) ((ParameterizedType)
            getClass().getGenericSuperclass()).getActualTypeArguments()[0]; // T
    }

    /**
     * TODO consider changing Long to Serializable
     * Request to retrieveAllByName the entity matching the specified id
     * @param id the entity id
     * @return the entity
     */
    public FactoryResponse<T> findById(Long id) {

        FactoryResponse<T> response = new FactoryResponse<>();
        T entity = HibernateUtil.getEntity(entityType, id);

        if (entity == null) {
            response.addValidation(
                ValidationInfo.Type.Account,
                ValidationInfo.Common.NotFound);
        } else {
            response.setEntity(entity);
        }

        return response;
    }

    /**
     * Request to retrieveAllByName the list of entities matching the specified ids.
     * @param ids the list of entity ids
     * @return the list of entities
     */
    public FactoryResponseList<T> findByIdList(Long[] ids) {

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Preparing to retrieveAllByName " + ids.length + " entities");
        }

        // Construct the retrieve by query from the id list
        //  "from Entity e where et.id in('1','2','3')"
        StringBuilder query = new StringBuilder();
        query.append("from " + entityType.getSimpleName() + " e where e.id in(");
        for (Long id : ids) {
            query.append("'"+id+"',");
        }
        query.setCharAt(query.length()-1, ')');

        List<T> entities = HibernateUtil.retrieveByQuery(query.toString());

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Successfully retrieved " + entities.size()+ " entities");
        }

        FactoryResponseList<T> response = new FactoryResponseList<>();
        response.setEntities(entities);
        return response;
    }

    /**
     * Request to retrieve all rows of the specified entity
     * @return the list of data for the specified entity
     */
    public FactoryResponseList<T> findAll() {

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE,
                "Request to retrieve all  " + entityType.getSimpleName() + " entities");
        }

        FactoryResponseList<T> response = new FactoryResponseList<T>();
        List<T> entities = HibernateUtil.retrieveAll(entityType);
        response.setEntities(entities);

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Successfully retrieved " + entities.size()+ " entities");
        }

        return response;

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
     * TODO move hibernate calls to HibernateUtil
     *
     * Request to save the specified entities
     * @param entities the entities to be persisted
     * @return true if the entities were successfully persisted
     */
    public boolean saveAll(List<T> entities) {

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Preparing to commit " + entities.size() + " entities");
        }

        return HibernateUtil.commit(entities);
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

        return HibernateUtil.delete(entities);
    }

    /**
     *
     * @param entityId
     */
    public boolean deleteById(String entityId) {

        // delete from Account a where a.id = '10929'
        String query = String.format("delete from %1$s a where a.id = '%2$s'",
            entityType.getSimpleName(), entityId);

        return HibernateUtil.deleteByQuery(query);
    }

    /**
     *
     * @param source
     * @param destination
     * @return
     */
    public FactoryResponse<T> merge(T source, T destination) {

        T updatedEntity = getMergeFactory().merge(source, destination);
        boolean result = HibernateUtil.merge(updatedEntity);

        FactoryResponse<T> response = new FactoryResponse<>();
        if (result) {
            response.setEntity(updatedEntity);
        }
        return response;
    }

    protected abstract MergeFactory<T> getMergeFactory();

}
