package com.bytes.thinkr.service.impl;

import com.bytes.thinkr.model.IValidationEnum;
import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.account.Account;
import com.bytes.thinkr.model.account.AccountList;
import com.bytes.thinkr.model.account.Client;
import com.bytes.thinkr.service.IAccountService;
import com.bytes.thinkr.model.IAccountServiceLocal;
import com.bytes.thinkr.service.util.PasswordUtil;
import com.bytes.thinkr.service.validator.AccountValidator;

import javax.inject.Singleton;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class AccountServiceImpl implements IAccountService, IAccountServiceLocal {

	private static final Logger LOGGER = Logger.getLogger(AccountServiceImpl.class.getName());
	
	/** 
	 * In-memory storage for created accounts
	 * TODO persist
	 * key - user id
	 * value - <tt>Account</tt>
	 */
	private HashMap<String, Account> accounts;
	
	/** The singleton instance */
	private static AccountServiceImpl instance;
	public static AccountServiceImpl getInstance() {
		if (instance == null) {
			instance = new AccountServiceImpl();
			
			// For debugging 
			instance.createAccount("Kent", "kentative@live.com", "1a2b3c4d5e", Client.Type.Admin);
		}
		return instance;
	}

	
	private AccountServiceImpl() {
		accounts = new HashMap<>();
	}


	/**
	 * Client is valid base on these criteria
	 * <ul>
	 * <li> client exists
	 * <li> client specified password matches server's account password
	 * </ul>
	 * @param client the client information.
	 * 
	 * Note: This is used for existing client
	 */
	@Override
	public boolean isExistingUserValid(Client client) {

		if (client == null ) {
			return false;
		}
		
		// Check for existing client
		String userId = client.getDisplayName();
		if (LOGGER.isLoggable(Level.FINE)) {
			LOGGER.log(Level.FINE, "Request to validate client: " + userId);
		}
		
		// Non-existing account
		if (!accounts.containsKey(userId.toLowerCase())) {
			
			if (LOGGER.isLoggable(Level.FINE)) {
				LOGGER.log(Level.FINE, "Client not found: " + userId);
			}
			
			return false;
		}
		
		// Check client password
		String userPassword = client.getPassword();
		Account account = accounts.get(userId.toLowerCase());

		// hex value comparison, case doesn't matter
		if (!PasswordUtil.encryptPassword(userPassword).equals(account.getClient().getPassword())) {
			if (LOGGER.isLoggable(Level.FINE)) {
				LOGGER.log(Level.FINE, "Client password does not match: " + userId);
			}
			return false;
		}
		
		if (LOGGER.isLoggable(Level.FINE)) {
			LOGGER.log(Level.FINE, "Successfully authenticate client: " + userId);
		}
		return true;
	}

	@Override
	public Account find(String userId) {

		String id = userId.toLowerCase();
		if (accounts.containsKey(id)) {

			return accounts.get(id);
		}

		return Account.INVALID;
	}

	@Override
	public Account createAccount(Client client) {
		
		if (client != null) {
			return createAccount(client.getDisplayName(), client.getEmail(), client.getPassword(), client.getUserType());
		}

		if (LOGGER.isLoggable(Level.WARNING)) {
			LOGGER.log(Level.WARNING, "Client is null. Unable to create account.");
		}
		
		return Account.INVALID;
		
	}

    @Override
    public AccountList findAll() {
        AccountList accountList = new AccountList();
        accountList.setAccounts(accounts.values());
        return accountList;
    }


	
	/**
	 * Request to create an <tt>Account</tt>.
	 * @param userId the unique user id
	 * @param userEmail the user email
	 * @param password the account password
	 * @param userType the account type
	 * @return A new account if all specified information is valid. 
	 * If user id exists, returns an invalid account.
	 */
	private Account createAccount(String userId, String userEmail, String password, Client.Type userType) {

		// Existing account
		if (accounts.containsKey(userId.toLowerCase())) {
			return Account.EXISTING;
		}

        IValidationEnum idStatus = AccountValidator.isUserIdValid(userId);
        IValidationEnum pwdStatus = AccountValidator.isPasswordValid(password);
        IValidationEnum emailStatus = AccountValidator.isEmailValid(userEmail);

		Account account = new Account();

		if (idStatus == ValidationInfo.Common.Valid &&
			pwdStatus == ValidationInfo.Common.Valid &&
			emailStatus == ValidationInfo.Common.Valid) {
			
			account.setClient(new Client(
                userId,
                userEmail,
                PasswordUtil.encryptPassword(password),
                userType));
		}

		account.setDateCreated(Calendar.getInstance().getTime());
        ValidationInfo validationInfo = new ValidationInfo();
        account.setValidation(validationInfo
                .add(ValidationInfo.Type.UserId, idStatus)
                .add(ValidationInfo.Type.Password, pwdStatus)
                .add(ValidationInfo.Type.Email, emailStatus));
		
		// Save account to server
		accounts.put(userId.toLowerCase(), account);
		
		return account;
	}


	@Override
	public Account updateAccount(Account account) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Account deleteAccount(String accountId) {
		// TODO Auto-generated method stub
		return null;
	}
}
