/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.factory;

import com.bytes.thinkr.model.entity.assignment.Assignment;
import com.bytes.thinkr.factory.merge.MergeFactory;

public class AssignmentFactory extends EntityFactory<Assignment> {

    private static AssignmentFactory instance;
    public static AssignmentFactory getInstance() {
        if (instance == null) {
            instance = new AssignmentFactory();
        }
        return instance;
    }

    @Override
    protected MergeFactory<Assignment> getMergeFactory() {
        return null;
    }
}
