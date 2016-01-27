package com.bytes.thinkr.service.validator;

import com.bytes.thinkr.model.IValidationEnum;
import com.bytes.thinkr.model.ValidationInfo;
import org.apache.commons.validator.routines.EmailValidator;

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
	public static IValidationEnum isUserIdValid(String userId) {
		return ValidationInfo.Common.Valid;
	}

	/**
	 * Validate email addresses. This is a wrapper for Apache
	 * Common @EmailValidator TODO add email verification
	 * 
	 * @param email the value validation is being performed on. A <tt>null</tt> value is considered invalid.
	 * @return true if the email is valid
	 */
	public static IValidationEnum isEmailValid(String email) {
		
		if (EmailValidator.getInstance().isValid(email)) {
			return ValidationInfo.Common.Valid;
		} else {
			return ValidationInfo.Common.Invalid;
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
	public static IValidationEnum isPasswordValid(String password) {
		if (password != null && password.length() >= 8) {
			return ValidationInfo.Common.Valid;
		} else {
			return ValidationInfo.Password.TooShort;
		}
	}

}
