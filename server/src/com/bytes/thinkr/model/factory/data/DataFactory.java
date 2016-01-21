/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory.data;

import com.bytes.thinkr.model.entity.IEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kent on 1/20/2016.
 */
public abstract class DataFactory<T extends IEntity> {


    /**
     * Generate a list of default persistable data
     * @param count the number of entities to be generated
     * @return the list of entities ready for persistence
     */
    public List<T> generate(int count) {

        List<T> dataList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            dataList.add(create(i));
        }
        return dataList;
    }

    /**
     * Create an entity with sufficient data for persistence
     * @param i the entity tag or counter
     * @return a persistable entity
     */
    protected abstract T create(int i);

}
