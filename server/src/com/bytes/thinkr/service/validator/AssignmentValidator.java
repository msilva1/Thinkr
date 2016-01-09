package com.bytes.thinkr.service.validator;

import com.bytes.thinkr.model.IValidationEnum;
import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.account.User;
import com.bytes.thinkr.model.assignment.Assignment;

public class AssignmentValidator {

	/**
	 * Indicate if the input parameters are valid for assignment creation. 
	 * 
	 * <ul><b>Check the following criteria:</b>
	 * <li>user is administrator or teacher
	 * <li>assignment name is valid (i.e., no symbols)
	 * <li>category is valid (i.e., defined in enum list)
	 * <li>assignment id is unique
	 * </ul>
	 * @param user
	 * @param assignment
	 * @return
	 */
	public static IValidationEnum isAssignmentIdValid(User user, Assignment assignment) {

		// User is (administrator or teacher)
		
		String userId = user.getUserId();
		String name = assignment.getName().toString();
		String category = assignment.getCategory().toString();
		
		return ValidationInfo.Common.Valid;
	}

	
	public static IValidationEnum isAssignmentValid() {
		
		return ValidationInfo.Common.Valid;
	}
	
	public static IValidationEnum isIdValid(User user, Assignment assignment) {

		String userId = user.getUserId();
		String name = assignment.getName().toString();
		String category = assignment.getCategory().toString();
		
		return ValidationInfo.Common.Valid;
	}
	
	/**
	 * 
	 * @param assignment 
	 * @return
	 */
	public static IValidationEnum isAnswerValid(Assignment assignment) {

		return ValidationInfo.Common.Valid;
	}	
		
	/**
	 * TODO
	 * @param assignment
	 * @return
	 */
	public static IValidationEnum isQuestionValid(Assignment assignment) {

		return ValidationInfo.Common.Valid;
	}

}
