/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.util.entity;

import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.account.Account;
import com.bytes.thinkr.model.account.Name;
import com.bytes.thinkr.model.account.User;

/**
 * Created by Kent on 1/13/2016.
 */
public class AccountEntityUtil extends EntityUtil<Account> {

    private static final ValidationInfo VALID;

    static {
        VALID = new ValidationInfo()
            .add(ValidationInfo.Type.Account, ValidationInfo.Common.Valid);
    }

    private static AccountEntityUtil instance;

    @Override
    public Account create(int i) {
        return new Account(createUser(i), VALID);
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
            "address" + sequence + "@email.com",
            "Password00" + sequence,
            userTypes[(int) (Math.random() * (userTypes.length-1))]);
        Name name = new Name("first" + sequence, "last" + sequence, "M"+sequence);
        user.setName(name);
        return user;

    }

    public static AccountEntityUtil getInstance() {
        if (instance == null) {
            instance = new AccountEntityUtil();
        }
        return instance;
    }
}
