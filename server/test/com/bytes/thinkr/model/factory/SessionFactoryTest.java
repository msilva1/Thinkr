/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.entity.session.Session;
import com.bytes.thinkr.model.factory.data.DataFactory;
import com.bytes.thinkr.model.factory.data.SessionDataFactory;

/**
 * Created by Kent on 1/22/2016.
 */
public class SessionFactoryTest extends EntityFactoryTest<Session>{

    @Override
    protected DataFactory<Session> getDataFactory() {
        return SessionDataFactory.getInstance();
    }

    @Override
    protected EntityFactory<Session> getEntityFactory() {
        return SessionFactory.getInstance();
    }
}