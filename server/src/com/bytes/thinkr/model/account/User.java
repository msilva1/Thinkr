package com.bytes.thinkr.model.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Kent on 12/5/2015.
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

	public static final User INVALID;
	
	public enum Type {
		Unspecified,
		Teacher,
		Student,
		Parent,
		Admin
	}
	
	static {
		INVALID = new User("invalid");
	}
	
	private String userId;
	private Type userType;
	private String password;
	private String email;

	public User() {
		this("Anonymous", "--", "--", Type.Unspecified);
	}
	
	public User(String userId) {
		this(userId, "--", "--", Type.Unspecified);
	}

	public User(String userId, String password) {
		this(userId, "--", password, Type.Unspecified);
	}

	
	/**
	 * Create a new user.
	 * @param userId - user display name, can be duplicate
	 * @param userEmail - user email address, must be unique
	 * @param password - the hashed password
	 */
	public User(String userId, String userEmail, String password, Type userType) {

		setUserType(userType);
		setUserId(userId);
		setEmail(userEmail);
		setPassword(password);
		
	}	

	public String toString() {
		
		return String.format(
				"User Type: %1$s " + System.lineSeparator() + 
				"User Id: %2$s " + System.lineSeparator() +
				"User Email: %3$s " + System.lineSeparator() +
				"User Password1: %4$s " + System.lineSeparator(),
				getUserType(), getUserId(), getEmail(), getPassword());
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId.toLowerCase();
	}

	/**
	 * @return the userType
	 */
	public Type getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(Type userType) {
		this.userType = userType;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
