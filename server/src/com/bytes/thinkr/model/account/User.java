package com.bytes.thinkr.model.account;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Embeddable
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

    public enum Type {
		Unspecified,
		Teacher,
		Student,
		Parent,
		Admin
	}

	private String userId;

    @Enumerated(value = EnumType.STRING)
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

    //region properties
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Type getUserType() {
        return userType;
    }

    public void setUserType(Type userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    //endregion

}
