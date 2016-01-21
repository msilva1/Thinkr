/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.db;

import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.entity.account.Client;
import com.bytes.thinkr.model.factory.data.AccountDataFactory;
import com.bytes.thinkr.model.util.HibernateUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;

/**
 * Created by Kent on 1/15/2016.
 */
public class AccountEntityTest extends EntityTest<Account>{

    @Test
    public void crud() {

        int entityCount = 100;
        String entityName = "Account";
        AccountDataFactory factory = AccountDataFactory.getInstance();

        AccountEntityTest entityTest = new AccountEntityTest();
        entityTest.create(factory, entityCount);

        List<Account> retrievedEntities = entityTest.retrieve(entityName, factory);
        Assert.assertThat(retrievedEntities.size(), is(entityCount));
    }

    /**
     * Hydration:
     * Account
     *  - Client
     * Commit order: Client > Account
     *
     * @param entity The Account to commit
     */
    @Override
    protected void beforeEntitySave(Account entity) {

        Client client = entity.getClient();
        HibernateUtil.commit(client);

    }
}
