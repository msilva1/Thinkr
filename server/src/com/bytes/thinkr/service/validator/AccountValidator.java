package com.bytes.thinkr.service.validator;

import org.apache.commons.validator.routines.EmailValidator;

import com.bytes.thinkr.model.account.AccountValidation;

public class AccountValidator {

	/**
	 * Perform user id validation: 1. Is userId available 2. Is userId
	 * appropriate 
	 * 
	 * TODO Implement
	 * 
	 * @param userId
	 * @return
	 */
	public static AccountValidation.UserId isUserIdValid(String userId) {
		return AccountValidation.UserId.Valid;
	}

	/**
	 * Validate email addresses. This is a wrapper for Apache
	 * Common @EmailValidator TODO add email verification
	 * 
	 * @param email the value validation is being performed on. A <tt>null</tt> value is considered invalid.
	 * @return true if the email is valid
	 */
	public static AccountValidation.Email isEmailValid(String email) {
		
		if (EmailValidator.getInstance().isValid(email)) {
			return AccountValidation.Email.Valid;
		} else {
			return AccountValidation.Email.Invalid;
		}
		
	}

	/**
	 * Perform password validation. 
	 * 1. Length > 8 
	 * 2. Rule2 
	 * 3. Rule3 
	 * TODO add additional rules
	 * 
	 * @param password the string to be validated
	 * @return true if the password is valid.
	 */
	public static AccountValidation.Password isPasswordValid(String password) {
		if (password != null && password.length() >= 8) {
			return AccountValidation.Password.Valid;
		} else {
			return AccountValidation.Password.TooShort;
		}
	}

}
