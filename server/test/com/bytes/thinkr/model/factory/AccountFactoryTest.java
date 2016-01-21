/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.factory.data.AccountDataFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Kent on 1/19/2016.
 * Run Entity Data Population Test Suite to create data for these tests
 */
public class AccountFactoryTest {

    private List<Account> accounts;

    @Before
    public void init() {
        accounts = AccountDataFactory.getInstance().generate(10);
    }

    @Test
    public void testFindById() throws Exception {
        Account account = AccountFactory.getInstance().findById(1l);
        assertTrue(account.getId() == 1l);
    }

    @Test
    public void testFindByIdList() throws Exception {
        Long[] idList = {1l, 2l, 3l, 4l, 5l};
        List<Account> entities = AccountFactory.getInstance().findByIdList(idList);
        assertThat(entities.size(), is(idList.length));
    }

    @Test
    public void testSave() throws Exception {
        AccountFactory factory = AccountFactory.getInstance();
        assertTrue(factory.save(accounts.get(0)));
    }

    @Test
    public void testSaveAll() throws Exception {
        AccountFactory factory = AccountFactory.getInstance();
        assertTrue(factory.saveAll(accounts));
    }

    @Test
    public void testDelete() throws Exception {
        AccountFactory factory = AccountFactory.getInstance();
        assertTrue(factory.delete(accounts.get(0)));
    }

    @Test
    public void testDeleteAll() throws Exception {
        AccountFactory factory = AccountFactory.getInstance();
        assertTrue(factory.deleteAll(accounts));
    }

    @Test
    public void testFindAll() {
        List<Account> accounts = AccountFactory.getInstance().findAll();
        assertNotNull(accounts);
        System.out.println(accounts.size());
    }
}