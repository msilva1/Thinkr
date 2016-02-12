package com.bytes.thinkr.service;

import com.bytes.thinkr.model.entity.assignment.Assignment;
import com.bytes.thinkr.model.entity.assignment.AssignmentList;

public interface IAssignmentService extends IResource<Assignment>{

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
	boolean delete(String assignmentId);
	
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
