package com.bytes.thinkr.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Singleton;

import com.bytes.thinkr.model.IValidationEnum;
import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.account.Account;
import com.bytes.thinkr.model.account.User;
import com.bytes.thinkr.model.assignment.Assignment;
import com.bytes.thinkr.model.assignment.AssignmentList;
import com.bytes.thinkr.service.IAssignmentService;
import com.bytes.thinkr.service.validator.AssignmentValidator;

@Singleton
public class AssignmentServiceImpl implements IAssignmentService {

	/** 
	 * In-memory storage for created assignments
	 * TODO persist
	 * key - assignment id ({teacher user id} + {assignment name} + {assignment category})
	 * value - <tt>Assignment</tt>
	 */
	private HashMap<String, Assignment> assignments;
	
	/**
	 * In-memory storage for assignments associated to user
	 * Teacher - assignments created by teacher
	 * Student - assignments assigned to student
	 * Parent - assignments for parents to approved
	 */
	private HashMap<String, Set<Assignment>> assignmentMap;

	
	private static final Logger LOGGER = Logger.getLogger(AssignmentServiceImpl.class.getName());
	
	/** The singleton instance */
	private static AssignmentServiceImpl instance;
	public static IAssignmentService getInstance() {
		if (instance == null) {
			instance = new AssignmentServiceImpl();
		}
		return instance;
	}

	/** Singleton constructor */
	private AssignmentServiceImpl() {
		
		assignments = new HashMap<>();
		assignmentMap = new HashMap<>();
	}

	@Override
	public Assignment create(String userId, Assignment assignment) {
	
		User user = AccountServiceImpl.getInstance().find(userId).getUser();
		ValidationInfo validationInfo = new ValidationInfo();
		IValidationEnum aidStatus = AssignmentValidator.isAssignmentIdValid(user, assignment);
		IValidationEnum questionStatus = AssignmentValidator.isQuestionValid(assignment);
		IValidationEnum answerStatus =  AssignmentValidator.isAnswerValid(assignment);
		
		if (aidStatus == ValidationInfo.Common.Valid &&
			questionStatus == ValidationInfo.Common.Valid &&
			answerStatus == ValidationInfo.Common.Valid) {

			// id = {teacher user id} + {assignment name} + {assignment category}
			String id = getAssignmentId(user, assignment);

			// Existing account
			if (assignments.containsKey(id)) {
				return Assignment.EXISTING;

			} else {
				// Save assignment to server
				assignment.setIdentifier(id);
				assignments.put(id, assignment);
				assignment.setValidation(validationInfo
                    .add(ValidationInfo.Type.AssignmentId, aidStatus)
                    .add(ValidationInfo.Type.Question, questionStatus)
                    .add(ValidationInfo.Type.Answer, answerStatus));
				return assignment;
			}
		}

		return Assignment.INVALID;

	}

	@Override
	public Assignment update(Assignment assignment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Assignment delete(String assignmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Construct the assignment id
	 * @param user the user creating the assignment
	 * @param assignment the assignment to be created
	 * @return the assignment id
	 * Note: all lower case
	 */
	private String getAssignmentId(User user, Assignment assignment) {
		
		// id = {teacher user id} + {assignment name} + {assignment category}
		return (user.getUserId() + assignment.getName() + assignment.getCategory()).toLowerCase();
	}

	@Override
	public AssignmentList getAssignmentList(String userId) {
		
		// Special case for debugging
		AssignmentList assignedList = new AssignmentList();
		if (userId.equals("all")) {
			assignedList.getAssignments().addAll(assignments.values());
			return assignedList;
		}
		
		if (assignmentMap.containsKey(userId)) {
			assignedList.getAssignments().addAll(assignmentMap.get(userId));
		}
		return assignedList;
	}

	@Override
	public Assignment assign(String userId, String assignmentId) {

		Account account = AccountServiceImpl.getInstance().find(userId);
		if (account != Account.INVALID && isExistingAssignmentValid(assignmentId)) {

			Set<Assignment> assignedList;
			if (assignmentMap.containsKey(userId)) {
				// user already has assigned assignments
				assignedList = assignmentMap.get(userId);		
				
			} else {
				// create a new entry
				assignedList = new HashSet<>();
				assignmentMap.put(userId, assignedList);
			}
			
			Assignment assignment = assignments.get(assignmentId);
			if(assignedList.add(assignment)) {
				if (LOGGER.isLoggable(Level.INFO)) {
					LOGGER.log(Level.FINE, "Assigned " + assignment.getIdentifier() + " to " + userId);
				}
				return Assignment.ASSIGNED;
				
			} else {
				if (LOGGER.isLoggable(Level.FINE)) {
					LOGGER.log(Level.FINE, "Existing assignment found: " + assignment.getIdentifier());
				}
				return Assignment.AlREADY_ASSIGNED;
			}
		}
		
		return Assignment.INVALID;
	}

	private boolean isExistingAssignmentValid(String assignmentId) {
		
		return assignments.containsKey(assignmentId);
	}
}
