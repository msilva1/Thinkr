/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.entity.assignment.Assignment;

import java.util.Date;
import java.util.List;

public class AssignmentFactory extends EntityFactory<Assignment> {

    private static AssignmentFactory instance;
    public static AssignmentFactory getInstance() {
        if (instance == null) {
            instance = new AssignmentFactory();
        }
        return instance;
    }

    @Override
    protected boolean saveSubEntities(List<Assignment> entity) {
        return false;
    }
}
