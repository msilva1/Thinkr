/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.factory.AssignmentFactory;
import com.bytes.thinkr.factory.EntityFactory;
import com.bytes.thinkr.model.entity.assignment.Assignment;
import com.bytes.thinkr.factory.data.AssignmentDataFactory;
import com.bytes.thinkr.factory.data.DataFactory;

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