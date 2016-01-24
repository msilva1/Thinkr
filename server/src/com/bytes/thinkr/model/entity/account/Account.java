/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.entity.account;

import com.bytes.thinkr.model.IValidationEnum;
import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.entity.IEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@XmlRootElement(name = "Account")
@XmlAccessorType(XmlAccessType.FIELD)
public class Account implements IEntity {

	//region static instances for convenience
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
		NOT_FOUND.getValidation().add(ValidationInfo.Type.Account, ValidationInfo.Common.NotFound);

		INVALID_PASSWORD = new Account();
		INVALID_PASSWORD.getValidation().add(ValidationInfo.Type.Account, ValidationInfo.Account.InvalidPassword);

		INVALID_ID_OR_PASSWORD = new Account();
		INVALID_ID_OR_PASSWORD.getValidation().add(ValidationInfo.Type.Account, ValidationInfo.Account.InvalidIdOrPassword);
	}
	//endregion

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountId")
	private Long id;

    @OneToOne(cascade=CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "client_clientId")
    @XmlElement
	private Client client;

    @Transient
	@XmlElement 
	private ValidationInfo validation;

	@Temporal(value = TemporalType.DATE)
	private Date dateCreated;

    /** Default constructor for code generation. */
	public Account() {
        this(new Client(), new ValidationInfo());
	}

    /**
     * Creates an <tt>Account</tt> with no validation information.
     * @param client the client information
     */
    public Account(Client client) {
        this(client, new ValidationInfo());
    }

    /**
     * Creates an <tt>Account</tt> with validation information.
     * @param client the client information
     * @param validationInfo the validation information
     */
     public Account(Client client, ValidationInfo validationInfo) {
        setClient(client);
        setValidation(validationInfo);
        setDateCreated(new Date());
    }

    @Override
    public String toString() {
		
		return String.format(
				"%1$s " + System.lineSeparator() + 
				"Date Created: %2$s " + System.lineSeparator() +
				"Account Validation: %3$s " + System.lineSeparator(),
				getClient(), getDateCreated().toString(), getValidation());
	}

	//region properties

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ValidationInfo getValidation() {
		return validation;
	}

	public void setValidation(ValidationInfo validation) {
		this.validation = validation;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

    //endregion
}
