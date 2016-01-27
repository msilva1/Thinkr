/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.factory.merge;

import com.bytes.thinkr.model.entity.IEntity;

/**
 * Created by Kent on 1/23/2016.
 */
public abstract class MergeFactory<T extends IEntity> {

    public abstract T merge(T source, T destination);

}
