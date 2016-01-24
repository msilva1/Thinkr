package com.bytes.thinkr.service.impl;

import com.bytes.thinkr.factory.AccountFactory;
import com.bytes.thinkr.factory.ClientFactory;
import com.bytes.thinkr.model.FactoryResponse;
import com.bytes.thinkr.model.FactoryResponseList;
import com.bytes.thinkr.model.IValidationEnum;
import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.entity.account.AccountList;
import com.bytes.thinkr.model.entity.account.Client;
import com.bytes.thinkr.service.IAccountService;
import com.bytes.thinkr.service.util.PasswordUtil;
import com.bytes.thinkr.service.validator.AccountValidator;

import javax.inject.Singleton;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class AccountServiceImpl implements IAccountService {

    // This class can use INFO or lower logging levels
	private static final Logger LOGGER = Logger.getLogger(AccountServiceImpl.class.getName());
	
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

	private AccountServiceImpl() {}

	/**
     * Validate that an existing user is valid.
     *
	 * Client is valid base on these criteria:
	 * <ul>
	 * <li> client exists
	 * <li> client specified password matches server's account password
	 * </ul>
	 * @param email the client email.
     * @param password the client password.
	 * 
	 * Note: This is used for existing client
	 */
	@Override
	public boolean login(String email, String password) {

        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Request to authenticate user: " + email);
        }

        // Check existing client using email
        if (email == null || email.equals(Client.DEFAULT_EMAIL)) {return false; }
        Client existing = ClientFactory.getInstance().findByEmail(email);

        // Non-existing account
        if (existing == null) {
            if (LOGGER.isLoggable(Level.INFO)) {
                LOGGER.log(Level.INFO, "Email is not associated with any known user.");
            }
            return false;
        }

		// Check client password
        String pwdEncrypted = existing.getPassword();

        // hex value comparison, case doesn't matter
		if (!PasswordUtil.encryptPassword(password).equals(pwdEncrypted)) {
			if (LOGGER.isLoggable(Level.FINE)) {
				LOGGER.log(Level.FINE, "Client password does not match: " + email);
			}
			return false;
		}
		
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.log(Level.INFO, "Successfully authenticate client: " + email);
		}
		return true;
	}

	@Override
	public Account find(String id) {

        Long accountId = Long.parseLong(id);
        FactoryResponse<Account> response = AccountFactory.getInstance().findById(accountId);
        if (response.getEntity() != null) {
            return response.getEntity();
        }
        return Account.NOT_FOUND;
	}

    @Override
    public Account findByEmail(String email) {

        FactoryResponse<Account> response = AccountFactory.getInstance().findByEmail(email);
        if (response.getEntity() != null) {
            return response.getEntity();
        }
        return Account.NOT_FOUND;
    }

    @Override
    public AccountList findAll() {

        FactoryResponseList<Account> response = AccountFactory.getInstance().findAll();
        List<Account> accounts = response.getEntities();
        AccountList accountList;
        if (accounts == null) {
            if (LOGGER.isLoggable(Level.INFO)) {
                LOGGER.log(Level.INFO, "Accounts not found");
            }
            accountList = new AccountList();
        } else {
            accountList = new AccountList(accounts);
        }

        return accountList;
    }

	@Override
	public Account create(Account account) {

		if (account != null && account.getClient() != null) {
            Client client = account.getClient();
            return createAccount(
                    client.getDisplayName(),
                    client.getEmail(),
                    client.getPassword(),
                    client.getUserType());
		}

		if (LOGGER.isLoggable(Level.WARNING)) {
			LOGGER.log(Level.WARNING, "Client is null. Unable to create account.");
		}

		return Account.INVALID;
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

        FactoryResponse<Account> response = AccountFactory.getInstance().findByEmail(userEmail);
        Account account = response.getEntity();
        // Existing account
		if (account != null) {
            if (LOGGER.isLoggable(Level.WARNING)) {
                LOGGER.log(Level.WARNING, "Request to create an existing account: " + userId);
            }
			return Account.EXISTING;
		}

        IValidationEnum idStatus = AccountValidator.isUserIdValid(userId);
        IValidationEnum pwdStatus = AccountValidator.isPasswordValid(password);
        IValidationEnum emailStatus = AccountValidator.isEmailValid(userEmail);

		account = new Account();

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
        AccountFactory.getInstance().save(account);
		return account;
	}


	@Override
	public Account update(String id, Account account) {

        Long accountId;
        try {
            accountId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            if (LOGGER.isLoggable(Level.WARNING)) {
                LOGGER.log(Level.WARNING, "Invalid account Id" + id, e);
            }
            return Account.INVALID;
        }

        // Retrieve the existing account
        FactoryResponse<Account> response = AccountFactory.getInstance().findById(accountId);
        if (response.getEntity() != null) {
            FactoryResponse<Account> mergeResponse = AccountFactory.getInstance().merge(account, response.getEntity());
            Account updatedAccount = (mergeResponse.getEntity() != null)
                ? mergeResponse.getEntity()
                : Account.INVALID;

            return updatedAccount;
        }

        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Account not found. Unable to update account " + id);
        }

		return Account.NOT_FOUND;
	}


	@Override
	public boolean delete(String accountId) {

        Long id = Long.parseLong(accountId);
        FactoryResponse<Account> response = AccountFactory.getInstance().findById(id);
        if (response.getEntity() != null) {
            return AccountFactory.getInstance().delete(response.getEntity());
        }

        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Unable to find " + id);
        }
        return false;
	}


    @Override
    public boolean deleteByEmail(String email) {

        FactoryResponse<Account> response = AccountFactory.getInstance().findByEmail(email);
        if (response.getEntity() != null) {
            return AccountFactory.getInstance().delete(response.getEntity());
        }

        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Unable to find account with email " + email);
        }
        return false;
    }
}
