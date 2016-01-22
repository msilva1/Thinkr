package com.bytes.thinkr.service;

import com.bytes.thinkr.model.factory.data.AccountDataFactoryTest;
import com.bytes.thinkr.model.factory.data.AssignmentDataFactoryTest;
import com.bytes.thinkr.model.factory.data.SessionDataFactoryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({



        // DB Entities Tests
        AccountDataFactoryTest.class,
        AssignmentDataFactoryTest.class,
        SessionDataFactoryTest.class,
})
public class TestSuite {}