/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.entity.assignment.Question;
import com.bytes.thinkr.model.factory.data.DataFactory;
import com.bytes.thinkr.model.factory.data.QuestionDataFactory;

/**
 * Created by Kent on 1/22/2016.
 */
public class QuestionFactoryTest extends EntityFactoryTest<Question>{

    @Override
    protected DataFactory<Question> getDataFactory() {
        return QuestionDataFactory.getInstance();
    }

    @Override
    protected EntityFactory<Question> getEntityFactory() {
        return QuestionFactory.getInstance();
    }
}