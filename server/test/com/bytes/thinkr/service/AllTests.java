package com.bytes.thinkr.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.bytes.thinkr.service.rs.AccountTest;
import com.bytes.thinkr.service.rs.AssignmentTest;
import com.bytes.thinkr.service.ws.JsonTest;
import com.bytes.thinkr.service.ws.StringTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	AccountTest.class,
	AssignmentTest.class,
	StringTest.class, 
	JsonTest.class, 
})

public class AllTests {} 