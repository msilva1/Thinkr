/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.db;

/**
 * Created by Kent on 1/16/2016.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({

        AccountEntityTest.class,
        AssignmentEntityTest.class,
        SessionEntityTest.class,
})
public class DbTestSuite {}