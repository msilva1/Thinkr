package com.bytes.thinkr.service.validator;

import com.bytes.thinkr.model.IValidationEnum;
import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.account.Client;
import com.bytes.thinkr.model.assignment.Assignment;

public class AssignmentValidator {

	/**
	 * Indicate if the input parameters are valid for assignment creation. 
	 * 
	 * <ul><b>Check the following criteria:</b>
	 * <li>client is administrator or teacher
	 * <li>assignment name is valid (i.e., no symbols)
	 * <li>category is valid (i.e., defined in enum list)
	 * <li>assignment id is unique
	 * </ul>
	 * @param client
	 * @param assignment
	 * @return
	 */
	public static IValidationEnum isAssignmentIdValid(Client client, Assignment assignment) {

		// Client is (administrator or teacher)
        // TODO
		return ValidationInfo.Common.Valid;
	}

	
	public static IValidationEnum isAssignmentValid() {
		// TODO
		return ValidationInfo.Common.Valid;
	}
	
	public static IValidationEnum isIdValid(Client client, Assignment assignment) {

        // TODO
		return ValidationInfo.Common.Valid;
	}
	
	/**
	 * 
	 * @param assignment 
	 * @return
	 */
	public static IValidationEnum isAnswerValid(Assignment assignment) {

        // TODO
		return ValidationInfo.Common.Valid;
	}	
		
	/**
	 * TODO
	 * @param assignment
	 * @return
	 */
	public static IValidationEnum isQuestionValid(Assignment assignment) {

        // TODO
		return ValidationInfo.Common.Valid;
	}

}
