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
public class AccountDataFactory extends DataFactory<Account> {

    private static AccountDataFactory instance;
    public static AccountDataFactory getInstance() {
        if (instance == null) {
            instance = new AccountDataFactory();
        }
        return instance;
    }

    @Override
    protected Account create(int i) {

        return new Account(ClientDataFactory.getInstance().create(i), ValidationInfo.VALID);
    }

    public Account createParent(int i) {
        Account account = create(i);
        String email = account.getClient().getEmail();
        account.getClient().setEmail("Parent_" + email);
        account.getClient().setUserType(Client.Type.Parent);
        return account;
    }

    public Account createTeacher(int i) {
        Account account = create(i);
        String email = account.getClient().getEmail();
        account.getClient().setEmail("Teacher_" + email);
        account.getClient().setUserType(Client.Type.Teacher);
        return account;
    }

    public Account createStudent(int i) {
        Account account = create(i);
        String email = account.getClient().getEmail();
        account.getClient().setEmail("Student_" + email);
        account.getClient().setUserType(Client.Type.Student);
        return account;
    }
}
