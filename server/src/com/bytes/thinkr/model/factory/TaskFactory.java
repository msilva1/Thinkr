/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.entity.assignment.Task;

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
        return false;
    }
}
