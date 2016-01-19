/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.db;

import com.bytes.thinkr.model.account.Account;
import com.bytes.thinkr.model.entity.AccountEntityFactory;
import com.bytes.thinkr.model.entity.SessionEntityFactory;
import com.bytes.thinkr.model.session.Session;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;

/**
 * Created by Kent on 1/15/2016.
 */
public class SessionEntityTest extends EntityTest<Session>{

    @Test
    public void crud() {

        int entityCount = 100;
        String entityName = "Session";
        SessionEntityFactory factory = SessionEntityFactory.getInstance();
        SessionEntityTest entityTest = new SessionEntityTest();
        entityTest.create(factory, entityCount);

        List<Session> retrievedEntities = entityTest.retrieve(entityName, factory);
        Assert.assertThat(retrievedEntities.size(), is(entityCount));
    }

    /**
     * Hydration:
     * Session
     *  - Account
     * Commit order: Account > Session
     * @param session
     */
    @Override
    protected void beforeEntitySave(Session session) {

        Account account = session.getAccount();
        HibernateUtil.commit(account.getClient());
        HibernateUtil.commit(account);
    }

}
