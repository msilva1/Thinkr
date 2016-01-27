/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

/**
 * Created by Kent on 1/16/2016.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({

        AccountFactoryTest.class,
        AnswerFactoryTest.class,
        AssignmentFactoryTest.class,
        ClientFactoryTest.class,
        QuestionFactoryTest.class,
        SessionFactoryTest.class,
        TaskFactoryTest.class
})
public class EntityFactoryTestSuite {}