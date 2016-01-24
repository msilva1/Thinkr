/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.FactoryResponse;
import com.bytes.thinkr.model.IValidationEnum;
import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.util.HibernateUtil;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Kent on 1/13/2016.
 *
 */
public class AccountFactory extends EntityFactory<Account> {

    // This class can use FINE or lower logging levels
    private static final Logger LOGGER = Logger.getLogger(AccountFactory.class.getName());

    private static AccountFactory instance;
    public static AccountFactory getInstance() {
        if (instance == null) {
            instance = new AccountFactory();
        }
        return instance;
    }

    /**
     * Using @OneToOne(cascade = CascadeType.ALL)
     * @param entities
     * @return
     */
    @Override
    protected boolean saveSubEntities(List<Account> entities) {
        return true;
//        List<Client> clients = new ArrayList<>();
//        for (Account entity : entities) {
//            clients.add(entity.getClient());
//        }
//        return HibernateUtil.commit(clients);
    }

    /**
     * Request to retrieveAllByName an <tt>Account</tt> matching the specified email address.
     * @param email the email address
     * @return the <tt>Account</tt> matching the specified email address. If not found,
     * an Account with <tt>ValidationInfo.Common.NotFound</tt> is returned.
     */
    public FactoryResponse<Account> findByEmail(String email) {

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Request to retrieved account with email: " + email);
        }

        // from Account a where a.client.email ='address1453271253491_0@email.com'
        String query = "from Account a where a.client.email ='" + email +"'";
        List<Account> accounts = HibernateUtil.retrieveByQuery(query);
        FactoryResponse<Account> response = new FactoryResponse<>();
        if (accounts != null && accounts.size() >= 1) {

            Account account = accounts.get(0);
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, "Successfully retrieved account : " + account.toString());
            }
            response.setEntity(account);
        }

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Unable to retrieved account with email: " + email);
        }
        response.addValidation(ValidationInfo.Type.Account, ValidationInfo.Common.NotFound);
        return response;

    }
}
