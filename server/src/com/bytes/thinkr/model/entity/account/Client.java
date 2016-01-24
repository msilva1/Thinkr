/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.entity.account;

import com.bytes.thinkr.model.entity.IEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Client implements IEntity {

    public static final String DEFAULT_USER_ID = "Anonymous";
    public static final String DEFAULT_EMAIL = "address@email.com";
    public static final String DEFAULT_PASSWORD = "ChangeMe";

    public enum Type {
		Unspecified,
		Teacher,
		Student,
		Parent,
		Admin
	}

    @Id
    @Column(name = "clientId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private String displayName;

    @Embedded
    @XmlElement
    private Name name;

    @Enumerated(value = EnumType.STRING)
	private Type userType;

	private String password;

    @Column(unique = true)
    private String email;

	public Client() {
		this(DEFAULT_USER_ID, DEFAULT_EMAIL, DEFAULT_PASSWORD, Type.Unspecified);
	}

	public Client(String displayName) {
		this(displayName, DEFAULT_EMAIL, DEFAULT_PASSWORD, Type.Unspecified);
	}

	public Client(String displayName, String password) {
		this(displayName, DEFAULT_EMAIL, password, Type.Unspecified);
	}

	/**
	 * Create a new user.
	 * @param displayName - user display name, can be duplicate
	 * @param userEmail - user email address, must be unique
	 * @param password - the hashed password
	 */
	public Client(String displayName, String userEmail, String password, Type userType) {

		setDisplayName(displayName);
		setEmail(userEmail);
		setPassword(password);
		setUserType(userType);
        setName(new Name(displayName));

	}

	public String toString() {
		
		return String.format(System.lineSeparator() +
                "DisplayName: %1$s " + System.lineSeparator() +
				"Client Type: %2$s " + System.lineSeparator() +
				"Client Id: %3$s " + System.lineSeparator() +
				"Client Email: %4$s " + System.lineSeparator() +
				"Client Password1: %5$s " + System.lineSeparator(),
				getDisplayName(), getUserType(), getDisplayName(), getEmail(), getPassword());
	}

    //region properties
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String userId) {
        this.displayName = userId;
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

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //endregion

}
