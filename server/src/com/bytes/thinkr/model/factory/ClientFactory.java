/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.entity.account.Client;

import java.util.List;

/**
 * Created by Kent on 1/13/2016.
 */
public class ClientFactory extends EntityFactory<Client> {

    private static ClientFactory instance;
    public static ClientFactory getInstance() {
        if (instance == null) {
            instance = new ClientFactory();
        }
        return instance;
    }

    @Override
    protected boolean saveSubEntities(List<Client> entities) {
        return true; // no sub entities
    }

}
