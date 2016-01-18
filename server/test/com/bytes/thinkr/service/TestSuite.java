package com.bytes.thinkr.service;

import com.bytes.thinkr.db.AccountEntityTest;
import com.bytes.thinkr.db.AssignmentEntityTest;
import com.bytes.thinkr.db.SessionEntityTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.bytes.thinkr.service.rs.AccountTest;
import com.bytes.thinkr.service.rs.AssignmentTest;
import com.bytes.thinkr.service.ws.JsonTest;
import com.bytes.thinkr.service.ws.StringTest;

@RunWith(Suite.class)
@SuiteClasses({



        // DB Entities Tests
        AccountEntityTest.class,
        AssignmentEntityTest.class,
        SessionEntityTest.class,
})
public class TestSuite {}