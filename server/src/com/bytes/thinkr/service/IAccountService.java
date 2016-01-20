package com.bytes.thinkr.service;

import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.entity.account.AccountList;
import com.bytes.thinkr.model.entity.account.Client;

public interface IAccountService {


    /**
     * @param client the client account with administrator privilege and valid credentials
     * @return the created account
     */
    Account createAccount(Client client);

    /**
     * @param user    the user account matching the account to be updated or
     *                a user with administrator privilege and valid credentials
     * @param account the updated account
     * @return the updated account
     * TODO - fix me
     */
    Account updateAccount(Account account);

    /**
     * @param user      the user account matching the account to be deleted or
     *                  the user account with administrator privilege and valid credentials
     * @param accountId the account id to be deleted
     * @return the deleted account
     * TODO - fix me
     */
    Account deleteAccount(String accountId);


    /**
     * Verify if the specified user information matches with the server account
     * Validates all specified information. If not specified,
     * the validation result ignores the missing value.
     *
     * @param user
     * @return true if valid
     */
    boolean isExistingUserValid(Client clientId);

    /**
     * Return the account corresponding to the specified user id.
     *
     * @param accountId the account id (same as user id)
     * @return the account associated with the specified id
     * TODO - admin required
     */
    Account find(String accountId);

    /**
     * Return all account.
     * @return the all accounts on the server     *
     */
    AccountList findAll();
}
