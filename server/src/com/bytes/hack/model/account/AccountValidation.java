package com.bytes.hack.model.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountValidation {
	
	public enum Account {
		Unspecified,
		InvalidPassword,
		InvalidIdOrPassword,
		Invalid, 
		Valid
	}
	
	public enum Email {
		Unspecified,
		Invalid,
		NotVerified,
		Valid
	}
	
	public enum UserId {
		Unspecified,
		Invalid,
		NotAvailable,
		Inappropriate,
		Existing,
		Valid
	}
	
	public enum Password {
		Unspecified,
		Invalid,
		TooShort,
		TooSimple,
		Valid
	}

			
	private Account accountStatus;
	private UserId userIdStatus;
	private Email emailStatus;
	private Password passwordStatus;

	public AccountValidation() {
		this(UserId.Unspecified, Password.Unspecified, Email.Unspecified);
		
	}
	
	/**
	 * Create a new ValidationInfo with the specified status code
	 * @param idStatus 
	 * @param pwdStatus
	 * @param emailStatus
	 */
	public AccountValidation(UserId idStatus, Password pwdStatus, Email emailStatus) {
		setUserIdStatus(idStatus);
		setPasswordStatus(pwdStatus);
		setEmailStatus(emailStatus);
		
		if (idStatus == AccountValidation.UserId.Valid &&
				pwdStatus == AccountValidation.Password.Valid  &&
				emailStatus == AccountValidation.Email.Valid ) {
			accountStatus = Account.Valid;
			
		} else {
			accountStatus = Account.Invalid;
		}
	}
	
	public String toString() {

		return String.format(
				"%1$s " + System.lineSeparator() + 
				"User validation info: %2$s " + System.lineSeparator() +
				"Email validation info: %3$s " + System.lineSeparator() +
				"Password validation info: %4$s " + System.lineSeparator(),
				getAccountStatus(), getUserIdStatus(), getEmailStatus(), getPasswordStatus());
	}

	/**
	 * @return the userIdStatus
	 */
	public UserId getUserIdStatus() {
		return userIdStatus;
	}

	/**
	 * @param userIdStatus the userIdStatus to set
	 */
	public void setUserIdStatus(UserId userIdStatus) {
		this.userIdStatus = userIdStatus;
	}

	/**
	 * @return the emailStatus
	 */
	public Email getEmailStatus() {
		return emailStatus;
	}

	/**
	 * @param emailStatus the emailStatus to set
	 */
	public void setEmailStatus(Email emailStatus) {
		this.emailStatus = emailStatus;
	}

	/**
	 * @return the passwordStatus
	 */
	public Password getPasswordStatus() {
		return passwordStatus;
	}

	/**
	 * @param passwordStatus the passwordStatus to set
	 */
	public void setPasswordStatus(Password passwordStatus) {
		this.passwordStatus = passwordStatus;
	}

	/**
	 * @return the accountStatus
	 */
	public Account getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param accountStatus the accountStatus to set
	 */
	public void setAccountStatus(Account accountStatus) {
		this.accountStatus = accountStatus;
	}
}
