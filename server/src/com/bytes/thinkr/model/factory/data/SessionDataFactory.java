/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory.data;

import com.bytes.thinkr.model.entity.session.Session;

import java.util.List;

/**
 * Created by Kent on 1/13/2016.
 */
public class SessionDataFactory extends DataFactory<Session> {

    private static SessionDataFactory instance;

    public static SessionDataFactory getInstance() {
        if (instance == null) {
            instance = new SessionDataFactory();
        }
        return instance;
    }

    @Override
    protected Session create(int i) {

        Session session = new Session(AccountDataFactory.getInstance().create(i));
        session.setLoggedIn(true);
        return session;
    }
}
