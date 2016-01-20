/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.entity.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Kent on 1/8/2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountList {

    private Collection<Account> accounts;

    public AccountList() {
        setAccounts(new HashSet<Account>());
    }

    public Collection<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Collection<Account> accounts) {
        this.accounts = accounts;
    }
}
