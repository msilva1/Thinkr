package com.bytes.thinkr.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Singleton;

import com.bytes.thinkr.model.account.Account;
import com.bytes.thinkr.model.account.AccountValidation;
import com.bytes.thinkr.model.account.Session;
import com.bytes.thinkr.model.account.SessionList;
import com.bytes.thinkr.model.account.User;
import com.bytes.thinkr.service.IAccountService;
import com.bytes.thinkr.service.validator.AccountValidator;

@Singleton
public class AccountServiceImpl implements IAccountService {

	/*
	 * The password encryption algorithm 
	 * One-way has: MD5
	 * -- http://www.ietf.org/rfc/rfc1321.txt 
	 * Two-way hash: SHA-1 
	 * -- http://csrc.nist.gov/publications/PubsFIPS.html SHA-256 SHA-384 SHA-512
	 */
	private static final String PWD_ENCRYPT_ALG = "MD5";
	
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
			instance.createAccount("Kent", "kentative@live.com", "1a2b3c4d5e", User.Type.Admin);
		}
		return instance;
	}

	
	private AccountServiceImpl() {
		
		accounts = new HashMap<String, Account>();
		
	}
	
		
	/**
	 * Returns a list of users matching the logged in status
	 * @return the list of user IDs and their sessions
	 */
	public SessionList getSessions(boolean isLoggedIn) {
		
		SessionList sessionList = new SessionList();
		
		for (Account a : accounts.values()) {
			Session s = a.getSession();
			if (s.isLoggedIn() == isLoggedIn) {
				
				if (LOGGER.isLoggable(Level.FINE)) {
					LOGGER.log(Level.FINE, "User found: " + a.getUser());
				}
				
				sessionList.getSessions().put(a.getUser().getUserId(), s);
			}
		}
		
		return sessionList;
	}

	/**
	 * User is valid base on these criteria
	 * <ul>
	 * <li> user exists
	 * <li> user specified password matches server's account password
	 * </ul>
	 * @param user the user information. 
	 * 
	 * Note: This is used for existing user
	 */
	@Override
	public boolean isExistingUserValid(User user) {

		if (user == null ) {
			return false;
		}
		
		// Check for existing user
		String userId = user.getUserId();
		if (LOGGER.isLoggable(Level.FINE)) {
			LOGGER.log(Level.FINE, "Request to validate user: " + userId);
		}
		
		// Non-existing account
		if (!accounts.containsKey(userId.toLowerCase())) {
			
			if (LOGGER.isLoggable(Level.FINE)) {
				LOGGER.log(Level.FINE, "User not found: " + userId);
			}
			
			return false;
		}
		
		// Check user password
		String userPassword = user.getPassword();
		Account account = accounts.get(userId.toLowerCase());

		// hex value comparison, case doesn't matter
		if (!encryptPassword(userPassword).equals(account.getUser().getPassword())) {
			if (LOGGER.isLoggable(Level.FINE)) {
				LOGGER.log(Level.FINE, "User password does not match: " + userId);
			}
			return false;
		}
		
		if (LOGGER.isLoggable(Level.FINE)) {
			LOGGER.log(Level.FINE, "Successfully authenticate user: " + userId);
		}
		return true;
	}
	
	@Override
	public Account createAccount(User user) {
		
		if (user != null) {
			return createAccount(user.getUserId(), user.getEmail(), user.getPassword(), user.getUserType());
		}

		if (LOGGER.isLoggable(Level.WARNING)) {
			LOGGER.log(Level.WARNING, "User is null. Unable to create account.");
		}
		
		return Account.INVALID;
		
	}


	@Override
	public Account login(String userId, String password) {
		return logUser(userId, password, true);
	}


	@Override
	public Account logout(String userId, String password) {
		return logUser(userId, password, false);
	}
	
	
	/**
	 * Encrypt the plain password using a message digest algorithm (MD5) 
	 * 
	 * Note:
	 * Validate the plain password before calling this method.
	 * This is a one-way hash 
	 * TODO Add salt and iterations
	 * 
	 * @param password the password to be hashed, NOT nullable
	 * @return the hashed password.
	 */
	private static String encryptPassword(String password) {

		StringBuilder pwd = new StringBuilder();
		try {
			
			byte[] encryptedPwd = MessageDigest.getInstance(PWD_ENCRYPT_ALG).digest(password.getBytes());
			
			// Convert byte to Hex
			for (byte b : encryptedPwd)
				pwd.append(Integer.toString((b & 0xFF), 16));

		} catch (NoSuchAlgorithmException e) {

			LOGGER.log(Level.SEVERE, "Unable to encrypt password using: " + PWD_ENCRYPT_ALG, e);
		}

		if (LOGGER.isLoggable(Level.FINE)) {
			LOGGER.log(Level.FINE, "Plain password: " + password + " digest: " + pwd.toString());
		}
		
		return pwd.toString();
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
	private Account createAccount(String userId, String userEmail, String password, User.Type userType) {

		// Existing account
		if (accounts.containsKey(userId.toLowerCase())) {
			return Account.EXISTING;
		}
		
		AccountValidation.UserId idStatus = AccountValidator.isUserIdValid(userId);
		AccountValidation.Password pwdStatus = AccountValidator.isPasswordValid(password);
		AccountValidation.Email emailStatus = AccountValidator.isEmailValid(userEmail);

		Account account = new Account();

		if (idStatus == AccountValidation.UserId.Valid && 
			pwdStatus == AccountValidation.Password.Valid && 
			emailStatus == AccountValidation.Email.Valid) {
			
			account.setUser(new User(userId, userEmail, encryptPassword(password), userType));
		}

		account.setDateCreated(Calendar.getInstance().getTime());
		account.setValidation(new AccountValidation(idStatus, pwdStatus, emailStatus));
		
		// Save account to server
		accounts.put(userId.toLowerCase(), account);
		
		return account;
	}
	
	/**
	 * 
	 * @param userId
	 * @param password
	 * @param state
	 * @return
	 */
	private Account logUser(String userId, String password, boolean state) {
		
		if (!isExistingUserValid(new User(userId, password)))
		{
			return Account.INVALID_ID_OR_PASSWORD;
		}
		
		// retrieves the existing account
		Account account = accounts.get(userId.toLowerCase());
		Session session = account.getSession();
		
		// starts a new session
		session.setLoggedIn(state);
		session.setDuration(0);
		session.setLoggedInTime(new Date());
		
		if (LOGGER.isLoggable(Level.FINE)) {
			LOGGER.log(Level.FINE, "logged state: " + state);
		}
		
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


	public User getUser(String userId) {
		
		String id = userId.toLowerCase();
		if (accounts.containsKey(id)) {
			
			return accounts.get(id).getUser();
		}
		
		return User.INVALID;
	}

}
