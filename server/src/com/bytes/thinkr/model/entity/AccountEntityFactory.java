/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.entity;

import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.account.Account;
import com.bytes.thinkr.model.account.Name;
import com.bytes.thinkr.model.account.User;

import java.util.Date;

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
        return entity.getUser().getUserId();
    }

    /**
     * Creates a random user based on the specified sequence
     * @param sequence the sequence
     * @return a random user
     */
    private User createUser(int sequence) {

        User.Type[] userTypes = User.Type.values();
        User user = new User(
            "userId" + sequence,
            "address" + System.currentTimeMillis() + "_" +sequence + "@email.com",
            "Password00" + sequence,
            userTypes[(int) (Math.random() * (userTypes.length-1))]);
        Name name = new Name("first" + sequence, "last" + sequence, "M"+sequence);
        user.setName(name);
        return user;

    }

    public static AccountEntityFactory getInstance() {
        if (instance == null) {
            instance = new AccountEntityFactory();
        }
        return instance;
    }

    public Account createParent(int i) {
        Account account = create(i);
        account.getUser().setUserType(User.Type.Parent);
        return account;
    }

    public Account createTeacher(int i) {
        Account account = create(i);
        account.getUser().setUserType(User.Type.Teacher);
        return account;
    }

    public Account createStudent(int i) {
        Account account = create(i);
        account.getUser().setUserType(User.Type.Student);
        return account;
    }
}
