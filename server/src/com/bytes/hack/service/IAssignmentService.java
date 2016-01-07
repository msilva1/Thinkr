package com.bytes.hack.service;

import com.bytes.hack.model.account.User;
import com.bytes.hack.model.assignment.Assignment;
import com.bytes.hack.model.assignment.AssignmentList;

public interface IAssignmentService {

	/**
	 * Create the specified assignment
	 * @param userId the administrator or teacher 
	 * @param assignment the assignment to be created
	 * @return the created assignment
	 */
	Assignment create(String userId, Assignment assignment);
	
	/**
	 * Create the specified assignment
	 * @param user the administrator or the teacher that created the assignment
	 * @param assignment the assignment to be created
	 * @return the updated assignment
	 */
	Assignment update(Assignment assignment);
	
	/**
	 * Delete the specified assignment
	 * @param user the administrator or the teacher that created the assignment
	 * @param assignment the assignment id to be deleted
	 * @return the created assignment
	 */
	Assignment delete(String assignmentId);
	
	/**
	 * Assign an assignment to a user
	 * @param userId the user with the privilege to assign  
	 * @param assignmentId the assignment 
	 * @return the assigned assignment, status indicated by the validation info
	 */
	Assignment assign(String userId, String assignmentId);
	
	/**
	 * Get all assignments associated with the specified user
	 * @param userId the user
	 * @return the list of assignments
	 */
	AssignmentList getAssignmentList(String userId);
	
}
