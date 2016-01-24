/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.factory;

import com.bytes.thinkr.model.entity.assignment.Task;
import com.bytes.thinkr.factory.merge.MergeFactory;

import java.util.List;

public class TaskFactory extends EntityFactory<Task> {

    private static TaskFactory instance;

    public static TaskFactory getInstance() {
        if (instance == null) {
            instance = new TaskFactory();
        }
        return instance;
    }

    @Override
    protected boolean saveSubEntities(List<Task> entity) {
        // Using cascade
        return true;
    }

    @Override
    protected MergeFactory<Task> getMergeFactory() {
        return null;
    }
}
