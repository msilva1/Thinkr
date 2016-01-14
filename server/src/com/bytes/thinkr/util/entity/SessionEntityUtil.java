/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.util.entity;

import com.bytes.thinkr.model.session.Session;

/**
 * Created by Kent on 1/13/2016.
 */
public class SessionEntityUtil extends EntityUtil<Session> {

    private static SessionEntityUtil instance;

    public static SessionEntityUtil getInstance() {
        if (instance == null) {
            instance = new SessionEntityUtil();
        }
        return instance;
    }

    @Override
    public Session create(int i) {

        return new Session();
    }
}
