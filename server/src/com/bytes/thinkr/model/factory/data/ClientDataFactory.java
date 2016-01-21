/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory.data;

import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.entity.account.Client;
import com.bytes.thinkr.model.entity.account.Name;

/**
 * Created by Kent on 1/13/2016.
 */
public class ClientDataFactory extends DataFactory<Client> {

    private static ClientDataFactory instance;
    public static ClientDataFactory getInstance() {
        if (instance == null) {
            instance = new ClientDataFactory();
        }
        return instance;
    }

    @Override
    protected Client create(int i) {

        Client.Type[] userTypes = Client.Type.values();
        Client client = new Client(
                "userId" + i,
                "address" + System.currentTimeMillis() + "_" + i + "@email.com",
                "Password00" + i,
                userTypes[(int) (Math.random() * (userTypes.length-1))]);
        Name name = new Name("first" + i, "last" + i, "M"+i);
        client.setName(name);
        return client;

    }
}
