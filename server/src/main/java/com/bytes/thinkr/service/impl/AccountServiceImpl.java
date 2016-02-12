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
import com.bytes.thinkr.model.entity.session.Session;
import com.bytes.thinkr.service.IAccountService;
import com.bytes.thinkr.service.util.PasswordUtil;
import com.bytes.thinkr.service.validator.AccountValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Calendar;
import java.util.List;


@Singleton
public class AccountServiceImpl implements IAccountService {

    // This class can use INFO or lower logging levels
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class.getName());
	
	/** The singleton instance */
	private static AccountServiceImpl instance;
	public static AccountServiceImpl getInstance() {
		if (instance == null) {
			instance = new AccountServiceImpl();
		}
		return instance;
	}

	private AccountServiceImpl() {}

    @Override
	public boolean login(String email, String password) {

        Session session = SessionServiceImpl.getInstance().login(email, password);
        return (session.isLoggedIn());
    }

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
    public boolean authenticate(String email, String password) {

        LOGGER.info("Request to authenticate user: {}" + email);

        // Check existing client using email
        if (email == null || email.equals(Client.DEFAULT_EMAIL)) {return false; }
        FactoryResponse<Client> clientR = ClientFactory.getInstance().findByEmail(email);
        Client existing = clientR.getEntity();

        // Non-existing account
        if (existing == null) {
            LOGGER.info("Email is not associated with any known user.");
            return false;
        }

        // Check client password
        String pwdEncrypted = existing.getPassword();

        // hex value comparison, case doesn't matter
        if (!PasswordUtil.encryptPassword(password).equals(pwdEncrypted)) {
            LOGGER.debug("Client password does not match: {}", email);
            return false;
        }

        LOGGER.info("Successfully authenticate client: {} ", email);
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
            LOGGER.info("Accounts not found");
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

        LOGGER.warn("Client is null. Unable to create account.");
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
            LOGGER.warn("Request to create an existing account: {}", userId);
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
            LOGGER.warn("Invalid account Id: {}", id, e);
            return Account.INVALID;
        }

        // Retrieve the existing account
        FactoryResponse<Account> response = AccountFactory.getInstance().findById(accountId);
        if (response.getEntity() != null) {
            response = AccountFactory.getInstance().merge(account, response.getEntity());

            return (response.getEntity() != null)
                ? response.getEntity()
                : Account.INVALID;
        }

        LOGGER.info("Account not found. Unable to update account {}", id);
		return Account.NOT_FOUND;
	}


	@Override
	public boolean delete(String accountId) {

        Long id = Long.parseLong(accountId);
        FactoryResponse<Account> response = AccountFactory.getInstance().findById(id);
        if (response.getEntity() != null) {
            return AccountFactory.getInstance().delete(response.getEntity());
        }

        LOGGER.info("Unable to find account: {}", id);
        return false;
	}


    @Override
    public boolean deleteByEmail(String email) {

        FactoryResponse<Account> response = AccountFactory.getInstance().findByEmail(email);
        if (response.getEntity() != null) {
            return AccountFactory.getInstance().delete(response.getEntity());
        }

        LOGGER.info("Unable to find account with email {}", email);
        return false;
    }

}
