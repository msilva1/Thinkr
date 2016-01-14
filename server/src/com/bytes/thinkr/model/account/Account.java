package com.bytes.thinkr.model.account;

import com.bytes.thinkr.model.ValidationInfo;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Date;

@Entity
@XmlRootElement(name = "Account")
@XmlAccessorType(XmlAccessType.FIELD)
public class Account {

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
		NOT_FOUND.getValidation().add(ValidationInfo.Type.Account, ValidationInfo.Common.Unspecified);
		
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

    @Embedded
	@XmlElement 
	private User user;

    @Transient
	@XmlElement 
	private ValidationInfo validation;

	@Temporal(value = TemporalType.DATE)
	private Date dateCreated;

    /** Default constructor for code generation. */
	public Account() {
        this(new User(), new ValidationInfo());
	}

    /**
     * Creates an <tt>Account</tt> with no validation information.
     * @param user the user information
     */
    public Account(User user) {
        this(user, new ValidationInfo());
    }

    /**
     * Creates an <tt>Account</tt> with validation information.
     * @param user the user information
     * @param validationInfo the validation information
     */
     public Account(User user, ValidationInfo validationInfo) {
        setUser(user);
        setValidation(validationInfo);
        setDateCreated(new Date());
    }

    @Override
    public String toString() {
		
		return String.format(
				"%1$s " + System.lineSeparator() + 
				"Date Created: %2$s " + System.lineSeparator() +
				"Account Validation: %3$s " + System.lineSeparator(),
				getUser(), getDateCreated().toInstant(), getValidation());
	}

	//region properties

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
