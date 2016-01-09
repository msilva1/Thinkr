package com.bytes.thinkr.model.account;

import com.bytes.thinkr.model.ValidationInfo;

import javax.xml.bind.annotation.*;
import java.util.Date;

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
		INVALID.getValidation().add(ValidationInfo.Type.Account, ValidationInfo.Common.Invalid);
		
		EXISTING = new Account();
		EXISTING.getValidation().add(ValidationInfo.Type.UserId, ValidationInfo.UserId.Existing);
	
		NOT_FOUND = new Account();
		NOT_FOUND.getValidation().add(ValidationInfo.Type.Account, ValidationInfo.Common.Unspecified);
		
		INVALID_PASSWORD = new Account();
		INVALID_PASSWORD.getValidation().add(ValidationInfo.Type.Account, ValidationInfo.Account.InvalidPassword);

		INVALID_ID_OR_PASSWORD = new Account();
		INVALID_ID_OR_PASSWORD.getValidation().add(ValidationInfo.Type.Account, ValidationInfo.Account.InvalidIdOrPassword);
	}

	@XmlElement 
	private User user;
	
	@XmlElement 
	private ValidationInfo validation;

	@XmlTransient
	private Date dateCreated;
	
	
	public Account() {
		this(new ValidationInfo());
	}
	
	public Account(ValidationInfo validationInfo) {
		setValidation(validationInfo);
		setUser(new User());
		setDateCreated(new Date());
	}
	
	
	public String toString() {
		
		return String.format(
				"%1$s " + System.lineSeparator() + 
				"Date Created: %2$s " + System.lineSeparator() +
				"Account Validation: %3$s " + System.lineSeparator(),
				user, dateCreated.toInstant(), getValidation());
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

	public ValidationInfo getValidation() {
		return validation;
	}

	public void setValidation(ValidationInfo validation) {
		this.validation = validation;
	}
}
