/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.factory;

import com.bytes.thinkr.factory.merge.AccountMergeFactory;
import com.bytes.thinkr.factory.merge.MergeFactory;
import com.bytes.thinkr.model.FactoryResponse;
import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.util.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created by Kent on 1/13/2016.
 *
 */
public class AccountFactory extends EntityFactory<Account> {

    // This class can use FINE or lower logging levels
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountFactory.class.getName());

    private static AccountFactory instance;
    public static AccountFactory getInstance() {
        if (instance == null) {
            instance = new AccountFactory();
        }
        return instance;
    }

    @Override
    protected MergeFactory<Account> getMergeFactory() {
        return AccountMergeFactory.getInstance();
    }

    /**
     * Request to retrieveAllByName an <tt>Account</tt> matching the specified email address.
     * @param email the email address
     * @return the <tt>Account</tt> matching the specified email address. If not found,
     * an Account with <tt>ValidationInfo.Common.NotFound</tt> is returned.
     */
    public FactoryResponse<Account> findByEmail(String email) {

        LOGGER.debug("Request to retrieved account with email: {}", email);

        // from Account a where a.client.email ='address1453271253491_0@email.com'
        String query = "from Account a where a.client.email ='" + email +"'";
        List<Account> accounts = HibernateUtil.retrieveByQuery(query);
        FactoryResponse<Account> response = new FactoryResponse<>();
        if (accounts != null && accounts.size() >= 1) {

            Account account = accounts.get(0);
            LOGGER.debug("Successfully retrieved account: {}", account.getId());
            response.setEntity(account);
        } else {
            LOGGER.debug("Unable to retrieved account with email: {} ", email);
            response.addValidation(ValidationInfo.Type.Account, ValidationInfo.Common.NotFound);
        }
        return response;

    }
}
