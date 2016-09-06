/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.service.impl;

import com.bytes.thinkr.factory.EntityFactory;
import com.bytes.thinkr.model.FactoryResponse;
import com.bytes.thinkr.model.entity.IEntity;
import com.bytes.thinkr.service.IResource;
import com.bytes.thinkr.service.rs.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Kent on 3/2/2016.
 */
public abstract class DefaultResourceImpl<T extends IEntity> implements IResource<T> {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultResourceImpl.class.getName());

    @Override
    public String version() {
        return "<p>Version: " + Application.apiVersion + "</p>";
    }

    @Override
    public T create(T resource) {
        return (getEntityFactory().save(resource)) ? resource : null;
    }

    @Override
    public T find(String id) {

        T entity = null;
        Long entityId = Long.parseLong(id);
        FactoryResponse<T> response = getEntityFactory().findById(entityId);
        if (!response.getValidationInfo().hasError()) {
            entity = response.getEntity();
        }
        return entity;
    }

    @Override
    public T update(String id, T entity) {

        T updatedEntity = null;
        FactoryResponse<T> response = getEntityFactory().update(id, entity);
        if (!response.getValidationInfo().hasError()) {
            updatedEntity = response.getEntity();
        }

        return updatedEntity;
    }

    @Override
    public boolean delete(String id) {

        boolean result = false;
        Long entityId = Long.parseLong(id);
        FactoryResponse<T> response = getEntityFactory().findById(entityId);
        if (!response.getValidationInfo().hasError()) {
            result = getEntityFactory().delete(response.getEntity());
        }

        return result;
    }

    protected abstract EntityFactory<T> getEntityFactory();
}
