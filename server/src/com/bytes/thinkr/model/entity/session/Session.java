/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.entity.session;

import com.bytes.thinkr.model.entity.IEntity;
import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.entity.account.Account;

import javax.persistence.*;
import javax.print.DocFlavor;
import javax.xml.bind.annotation.*;
import java.util.Date;

@Entity
@XmlRootElement
@XmlType(name = "Session")
@XmlAccessorType(XmlAccessType.FIELD)
public class Session implements IEntity {

	public static final Session INVALID_ID_OR_PASSWORD;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SessionId")
    private Long id;

    @OneToOne
    @XmlElement
    private Account account;

    private boolean loggedIn;

    @Temporal(TemporalType.DATE)
	private Date loggedInTime;
	
	private int duration;

    @Transient
    @XmlElement
    private ValidationInfo validation;

    static {
        INVALID_ID_OR_PASSWORD = new Session();
        INVALID_ID_OR_PASSWORD.getValidation()
            .add(ValidationInfo.Type.Session, ValidationInfo.Account.InvalidIdOrPassword);
    }

    public Session() {
        this(Account.NOT_FOUND);
	}


    public Session(Account account) {
        setAccount(account);
        setLoggedIn(false);
        setDuration(0);
        setLoggedInTime(new Date());
        setValidation(new ValidationInfo());
    }

    @Override
    public String toString(){
        return getAccount().getClient().getDisplayName() + " logged in: " + isLoggedIn();
    }

    /**
	 * @return the loggedIn
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * @param loggedIn the loggedIn to set
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	/**
	 * @return the loggedInTime
	 */
	public Date getLoggedInTime() {
		return loggedInTime;
	}

	/**
	 * @param loggedInTime the loggedInTime to set
	 */
	public void setLoggedInTime(Date loggedInTime) {
		this.loggedInTime = loggedInTime;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

    public ValidationInfo getValidation() {
        return validation;
    }

    public void setValidation(ValidationInfo validation) {
        this.validation = validation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
