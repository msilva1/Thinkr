/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.entity.assignment.Assignment;
import com.bytes.thinkr.model.factory.data.AssignmentDataFactory;
import com.bytes.thinkr.model.factory.data.DataFactory;

/**
 * Created by Kent on 1/22/2016.
 */
public class AssignmentFactoryTest extends EntityFactoryTest<Assignment> {

    @Override
    protected DataFactory<Assignment> getDataFactory() {
        return AssignmentDataFactory.getInstance();
    }

    @Override
    protected EntityFactory<Assignment> getEntityFactory() {
        return AssignmentFactory.getInstance();
    }
}