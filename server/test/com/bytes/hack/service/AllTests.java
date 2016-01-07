package com.bytes.hack.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.bytes.hack.service.rest.AccountTest;
import com.bytes.hack.service.rest.AssignmentTest;
import com.bytes.hack.service.ws.JsonTest;
import com.bytes.hack.service.ws.StringTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	AccountTest.class,
	AssignmentTest.class,
	StringTest.class, 
	JsonTest.class, 
})

public class AllTests {} 