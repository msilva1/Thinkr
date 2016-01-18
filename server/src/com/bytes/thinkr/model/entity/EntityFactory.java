/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.entity;

import com.bytes.thinkr.model.entity.IEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kent on 1/13/2016.
 */
public abstract class EntityFactory<T extends IEntity> {

    /**
     *
     * @param count
     * @return
     */
    public List<T> generate(int count) {

        List<T> dataList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            dataList.add(create(i));
        }
        return dataList;
    }

    /**
     *
     * @param i
     * @return
     */
    protected abstract T create(int i);

    /**
     *
     * @param entity
     * @return
     */
    public abstract String getDisplayInfo(T entity);
}
