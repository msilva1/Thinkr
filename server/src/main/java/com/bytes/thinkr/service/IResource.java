/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.service;

import com.bytes.thinkr.model.entity.IEntity;

/**
 * Created by Kent on 2/1/2016.
 */
public interface IResource<T extends IEntity> {

    /**
     * The resource API version
     * @return the resource API version
     */
    String version();

    /**
     * @param resource the resource to be created
     * @return the created resource
     */
    T create(T resource);

    /**
     * Update an existing <tt>Account</tt>.
     * Version 1.0 - Existing data cannot be removed this way.
     *
     * @param id       the id of the resource to be updated
     * @param resource the updated information
     * @return the updated <tt>Account</tt>
     */
    T update(String id, T resource);

    /**
     * Request to delete an resource by its id.
     *
     * @param id the resource id
     * @return true if successfully deleted
     */
    boolean delete(String id);

    /**
     * Request to retrieve the resource corresponding to the specified id.
     *
     * @param id the resource id
     * @return the resource associated with the specified id
     */
    T find(String id);

    /**
     * Return all resources of this type
     * @return all available resources of this type
     */
    //List<T> findAll();
}
