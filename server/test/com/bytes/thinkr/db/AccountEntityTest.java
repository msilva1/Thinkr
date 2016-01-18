/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.db;

import com.bytes.thinkr.model.account.Account;
import com.bytes.thinkr.model.entity.AccountEntityFactory;
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
        AccountEntityFactory factory = AccountEntityFactory.getInstance();

        AccountEntityTest entityTest = new AccountEntityTest();
        entityTest.create(factory, entityCount);

        List<Account> retrievedEntities = entityTest.retrieve(entityName, factory);
        Assert.assertThat(retrievedEntities.size(), is(entityCount));
    }

}
