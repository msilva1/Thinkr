/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.factory.AccountFactory;
import com.bytes.thinkr.factory.EntityFactory;
import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.factory.data.AccountDataFactory;
import com.bytes.thinkr.factory.data.DataFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Kent on 1/19/2016.
 * Run Entity Data Population Test Suite to create data for these tests
 */
public class AccountFactoryTest extends EntityFactoryTest<Account> {
    @Override
    protected DataFactory<Account> getDataFactory() {
        return AccountDataFactory.getInstance();
    }

    @Override
    protected EntityFactory<Account> getEntityFactory() {
        return AccountFactory.getInstance();
    }
}