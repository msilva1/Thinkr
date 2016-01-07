package com.bytes.hack.model.account;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "Account")
@XmlAccessorType(XmlAccessType.FIELD)
public class Account {

	public static final Account INVALID;
	
	public static final Account EXISTING;
	
	public static final Account NOT_FOUND;

	public static final Account INVALID_PASSWORD;
	
	public static final Account INVALID_ID_OR_PASSWORD;
	
	static {
		
		INVALID = new Account();
		INVALID.getValidation().setAccountStatus(AccountValidation.Account.Invalid);
		
		EXISTING = new Account();
		EXISTING.getValidation().setUserIdStatus(AccountValidation.UserId.Existing);
	
		
		NOT_FOUND = new Account();
		NOT_FOUND.getValidation().setAccountStatus(AccountValidation.Account.Unspecified);
		
		INVALID_PASSWORD = new Account();
		INVALID_PASSWORD.getValidation().setAccountStatus(AccountValidation.Account.InvalidPassword);
		

		INVALID_ID_OR_PASSWORD = new Account();
		INVALID_ID_OR_PASSWORD.getValidation().setAccountStatus(AccountValidation.Account.InvalidIdOrPassword);

		
		
	}

	@XmlElement 
	private User user;
	
	@XmlElement 
	private AccountValidation validation;

	@XmlElement
	private Session session;
	
	@XmlTransient
	private Date dateCreated;
	
	
	public Account() {
		this(new AccountValidation());
	}
	
	public Account(AccountValidation validationInfo) {
		setValidation(validationInfo);
		setUser(new User());
		setSession(new Session());
		setDateCreated(new Date());
	}
	
	
	public String toString() {
		
		return String.format(
				"%1$s " + System.lineSeparator() + 
				"Date Created: %2$s " + System.lineSeparator() +
				"Account Validation: %3$s " + System.lineSeparator(),
				user, dateCreated.toInstant(), validation);
		
	}
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the validationInfo
	 */
	public AccountValidation getValidation() {
		return validation;
	}

	/**
	 * @param validationInfo the validationInfo to set
	 */
	public void setValidation(AccountValidation validationInfo) {
		this.validation = validationInfo;
	}

	/**
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(Session session) {
		this.session = session;
	}

}
