/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.entity.assignment.Answer;
import com.bytes.thinkr.model.factory.data.AnswerDataFactory;
import com.bytes.thinkr.model.factory.data.DataFactory;

/**
 * Created by Kent on 1/20/2016.
 */
public class AnswerFactoryTest extends EntityFactoryTest<Answer> {

    @Override
    protected DataFactory getDataFactory() {
        return AnswerDataFactory.getInstance();
    }

    @Override
    protected EntityFactory getEntityFactory() {
        return AnswerFactory.getInstance();
    }
}