/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.factory;

import com.bytes.thinkr.model.entity.session.Session;
import com.bytes.thinkr.factory.merge.MergeFactory;

/**
 * Created by Kent on 1/13/2016.
 */
public class SessionFactory extends EntityFactory<Session> {

    private static SessionFactory instance;

    public static SessionFactory getInstance() {
        if (instance == null) {
            instance = new SessionFactory();
        }
        return instance;
    }

    @Override
    protected MergeFactory<Session> getMergeFactory() {
        return null;
    }
}
