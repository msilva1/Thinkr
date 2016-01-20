/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.entity.session.Session;

import java.util.List;

/**
 * Created by Kent on 1/13/2016.
 */
public class SessionEntityFactory extends EntityFactory<Session> {

    private static SessionEntityFactory instance;

    public static SessionEntityFactory getInstance() {
        if (instance == null) {
            instance = new SessionEntityFactory();
        }
        return instance;
    }

    @Override
    protected Session create(int i) {

        Session session = new Session(AccountEntityFactory.getInstance().create(i));
        session.setLoggedIn(true);
        return session;
    }

    @Override
    public String getDisplayInfo(Session entity) {
        return
            entity.getAccount().getClient().getDisplayName() + " logged in: " +
            entity.isLoggedIn();
    }

    @Override
    public String getEntityName() {
        return Session.class.getSimpleName();
    }

    @Override
    protected boolean saveSubEntities(List<Session> entity) {
        return false;
    }
}
