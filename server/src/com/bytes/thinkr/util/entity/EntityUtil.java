/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.util.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kent on 1/13/2016.
 */
public abstract class EntityUtil<T> {


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
    public abstract T create(int i);

}
