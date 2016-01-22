/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.entity.account.Client;
import com.bytes.thinkr.model.factory.data.ClientDataFactory;
import com.bytes.thinkr.model.factory.data.DataFactory;

/**
 * Created by Kent on 1/21/2016.
 */
public class ClientFactoryTest extends EntityFactoryTest<Client> {

    @Override
    protected DataFactory<Client> getDataFactory() {
        return ClientDataFactory.getInstance();
    }

    @Override
    protected EntityFactory<Client> getEntityFactory() {
        return ClientFactory.getInstance();
    }
}