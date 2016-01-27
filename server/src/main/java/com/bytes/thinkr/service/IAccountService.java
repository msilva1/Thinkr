package com.bytes.thinkr.service;

import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.entity.account.AccountList;

public interface IAccountService {


    /**
     * @param account the <tt>Account</tt> to be created
     * @return the created <tt>Account</tt>
     */
    Account create(Account account);

    /**
     * Update an existing <tt>Account</tt>.
     * Version 1.0 - Existing data cannot be removed this way.
     * @param id the id of the <tt>Account</tt> to be updated
     * @param account the updated information
     * @return the updated <tt>Account</tt>
     */
    Account update(String id, Account account);

    /**
     * Request to delete an <tt>Account</tt> by <tt>Account</tt> id.
     * @param accountId the <tt>Account</tt> id to be deleted
     * @return true if successfully deleted
     */
    boolean delete(String accountId);

    /**
     * Request to delete an <tt>Account</tt> by email address
     * @param email the email address corresponding to the <tt>Account</tt> to be deleted
     * @return true if successfully deleted
     */
    boolean deleteByEmail(String email);


    /**
     * Request to log in.
     *
     * @param email the <tt>Account</tt> email
     * @param password the <tt>Account</tt> password
     * @return true if successfully logged in
     */
    boolean login(String email, String password);

    /**
     * Verify if the specified user information matches with the server <tt>Account</tt>
     * Validates all specified information, ignored if omitted.
     * Calculated and generated attributes are ignored.
     *
     * @param email the <tt>Account</tt> email
     * @param password the <tt>Account</tt> password
     * @return true if valid
     */
    boolean authenticate(String email, String password);

    /**
     * Return the <tt>Account</tt> corresponding to the specified user id.
     *
     * @param accountId the <tt>Account</tt> id
     * @return the <tt>Account</tt> associated with the specified id
     */
    Account find(String accountId);

    /**
     * Request to retrieve an <tt>Account</tt> by email
     * @param email the <tt>Account</tt> email
     * @return the <tt>Account</tt> corresponding to the specified email
     */
    Account findByEmail(String email);

    /**
     * Return all <tt>Account</tt>s.
     * @return all <tt>Account</tt>s on the server
     */
    AccountList findAll();

}
