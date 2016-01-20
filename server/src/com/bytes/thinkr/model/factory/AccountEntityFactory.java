/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.entity.account.Client;
import com.bytes.thinkr.model.entity.account.Name;
import com.bytes.thinkr.model.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public String getEntityName() {
        return Account.class.getSimpleName();
    }

    @Override
    protected boolean saveSubEntities(List<Account> entities) {

        List<Client> clients = new ArrayList<>();
        for (Account entity : entities) {
            clients.add(entity.getClient());
        }
        return HibernateUtil.commit(clients);
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
