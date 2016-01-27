/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.factory;

import com.bytes.thinkr.model.entity.assignment.Task;
import com.bytes.thinkr.factory.merge.MergeFactory;

public class TaskFactory extends EntityFactory<Task> {

    private static TaskFactory instance;

    public static TaskFactory getInstance() {
        if (instance == null) {
            instance = new TaskFactory();
        }
        return instance;
    }

    @Override
    protected MergeFactory<Task> getMergeFactory() {
        return null;
    }
}
