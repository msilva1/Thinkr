/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.entity.account.Client;
import com.bytes.thinkr.model.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kent on 1/13/2016.
 */
public class AccountFactory extends EntityFactory<Account> {

    private static AccountFactory instance;
    public static AccountFactory getInstance() {
        if (instance == null) {
            instance = new AccountFactory();
        }
        return instance;
    }

    @Override
    protected boolean saveSubEntities(List<Account> entities) {

        List<Client> clients = new ArrayList<>();
        for (Account entity : entities) {
            clients.add(entity.getClient());
        }
        return HibernateUtil.commit(clients);
    }

}
