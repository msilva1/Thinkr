package com.bytes.hack.service;

import com.bytes.hack.model.account.Account;
import com.bytes.hack.model.account.SessionList;
import com.bytes.hack.model.account.User;

public interface IAccountService {


	/**
	 * 
	 * @param user the user account with administrator privilege and valid credentials
	 * @return the created account
	 */
	public Account createAccount(User user);	
	
	/**
	 * 
	 * @param user the user account matching the account to be updated or 
	 * a user with administrator privilege and valid credentials
	 * @param account the updated account
	 * @return the updated account
	 * TODO - fix me
	 */
	public Account updateAccount(Account account);
	
	/**
	 * 
	 * @param user the user account matching the account to be deleted or 
	 * the user account with administrator privilege and valid credentials
	 * @param accountId the account id to be deleted
	 * @return the deleted account
	 * TODO - fix me
	 */
	public Account deleteAccount(String accountId);
	
	
	/**
	 * Logs in the specified account if the following criteria are met:  
	 * <ul>
	 * 	<li>user exists
	 * 	<li>credential is valid
	 *  <li>user is currently not logged in
	 * </ul>
	 *   
	 * TODO encrypt password in transit
	 * 
	 * @param userId the id of the user to be logged in
	 * @param password the password of the user to be logged in
	 * @return
	 */
	public Account login(String userId, String password);
	
	
	/**
	 * Logs out the specified account if the following criteria are met:  
	 * <ul>
	 * 	<li>user exists
	 * 	<li>credential is valid
	 *  <li>user is currently logged in
	 * </ul>
	 *   
	 * TODO encrypt password in transit
	 * 
	 * @param userId the id of the user to be logged out
	 * @param password the password of the user to be logged out
	 * @return
	 */
	public Account logout(String userId, String password);
	

	/**
	 * Returns the list of user matching the logged in state. This can be use to obtain
	 * the list of logged in users. 
	 * @param isLoggedIn
	 * @return the list of sessions matching the specified logged in state
	 */
	public SessionList getSessions(boolean isLoggedIn);
	
	/**
	 * Verify if the specified user information matches with the server account
	 * Validates all specified information. If not specified, 
	 * the validation result ignores the missing value. 
	 * @param user
	 * @return true if valid
	 */
	public boolean isExistingUserValid(User userId);

	
}
