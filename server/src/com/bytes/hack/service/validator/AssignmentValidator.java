package com.bytes.hack.service.validator;

import java.util.List;

import com.bytes.hack.model.account.User;
import com.bytes.hack.model.assignment.Assignment;
import com.bytes.hack.model.assignment.AssignmentValidation;
import com.bytes.hack.model.assignment.Question;

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
	public static AssignmentValidation.AssignmentId isAssignmentIdValid(User user, Assignment assignment) {

		// User is (administrator or teacher)
		
		String userId = user.getUserId();
		String name = assignment.getName().toString();
		String category = assignment.getCategory().toString();
		
		return AssignmentValidation.AssignmentId.Valid;
	}

	
	public static AssignmentValidation.Assignment isAssignmentValid() {
		
		return AssignmentValidation.Assignment.Valid;
	}
	
	public static AssignmentValidation.AssignmentId isIdValid(User user, Assignment assignment) {

		String userId = user.getUserId();
		String name = assignment.getName().toString();
		String category = assignment.getCategory().toString();
		
		return AssignmentValidation.AssignmentId.Valid;
	}
	
	/**
	 * 
	 * @param assignment 
	 * @return
	 */
	public static AssignmentValidation.Answer isAnswerValid(Assignment assignment) {

		return AssignmentValidation.Answer.Valid;
	}	
		
	/**
	 * TODO
	 * @param assignment
	 * @return
	 */
	public static AssignmentValidation.Question isQuestionValid(Assignment assignment) {

		return AssignmentValidation.Question.Valid;
	}
	
	

}
