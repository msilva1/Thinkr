/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.entity.assignment.Task;
import com.bytes.thinkr.model.factory.data.DataFactory;
import com.bytes.thinkr.model.factory.data.TaskDataFactory;

/**
 * Created by Kent on 1/22/2016.
 */
public class TaskFactoryTest extends EntityFactoryTest<Task>{

    @Override
    protected DataFactory<Task> getDataFactory() {
        return TaskDataFactory.getInstance();
    }

    @Override
    protected EntityFactory<Task> getEntityFactory() {
        return TaskFactory.getInstance();
    }
}