/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.entity;

import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.account.Account;
import com.bytes.thinkr.model.account.Client;
import com.bytes.thinkr.model.account.Name;

/**
 * Created by Kent on 1/13/2016.
 */
public class AccountEntityFactory extends EntityFactory<Account> {

    private static final ValidationInfo VALID;

    static {
        VALID = new ValidationInfo()
            .add(ValidationInfo.Type.Account, ValidationInfo.Common.Valid);
    }

    private static AccountEntityFactory instance;

    @Override
    protected Account create(int i) {
        return new Account(createUser(i), VALID);
    }

    @Override
    public String getDisplayInfo(Account entity) {
        return entity.getClient().getDisplayName();
    }

    /**
     * Creates a random user based on the specified sequence
     * @param sequence the sequence
     * @return a random user
     */
    private Client createUser(int sequence) {

        Client.Type[] userTypes = Client.Type.values();
        Client client = new Client(
            "userId" + sequence,
            "address" + System.currentTimeMillis() + "_" +sequence + "@email.com",
            "Password00" + sequence,
            userTypes[(int) (Math.random() * (userTypes.length-1))]);
        Name name = new Name("first" + sequence, "last" + sequence, "M"+sequence);
        client.setName(name);
        return client;

    }

    public static AccountEntityFactory getInstance() {
        if (instance == null) {
            instance = new AccountEntityFactory();
        }
        return instance;
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
