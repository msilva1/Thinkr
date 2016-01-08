package com.bytes.thinkr.model.assignment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class AssignmentValidation {

	/** The overall validity of the assignment */
	public enum Assignment {
		Unspecified,
		Existing,
		Invalid, 
		Valid, 
		AlreadyAssigned,    // already assigned to a specific user; can be assigned to different users
		Assigned 			// successfully assigned an assignment
	}
	
	/** The validity of the assignment id */
	public enum AssignmentId {
		Unspecified,
		Existing,
		Invalid, 
		Valid
	}
	
	/** The validity of the question id */
	public enum Question {
		Unspecified,
		Invalid,
		Valid
	}
	
	/** The validity of the answer id */
	public enum Answer {
		Unspecified,
		Invalid,
		Correct,
		Incorrect,
		Valid
	}
	
	private Assignment assignmentStatus;
	private AssignmentId assignmentId;
	private Question questionsStatus;
	private Answer answerStatus;

	public AssignmentValidation() {
		this(Question.Unspecified, Answer.Unspecified);
		
	}
	
	/**
	 * Create a new Validation with the specified status code
	 * @param idStatus 
	 * @param pwdStatus
	 * @param emailStatus
	 */
	public AssignmentValidation(Question questionStatus, Answer answerStatus) {

		setQuestionsStatus(questionStatus);
		setAnswerStatus(answerStatus);
		
		if (questionStatus == Question.Valid &&
			answerStatus == Answer.Valid) {
			setAssignmentStatus(Assignment.Valid);
			
		} else {
			setAssignmentStatus(Assignment.Invalid);
		}
	}

	/**
	 * @return the assignmentStatus
	 */
	public Assignment getAssignmentStatus() {
		return assignmentStatus;
	}

	/**
	 * @param assignmentStatus the assignmentStatus to set
	 */
	public void setAssignmentStatus(Assignment assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}

	/**
	 * @return the questionsStatus
	 */
	public Question getQuestionsStatus() {
		return questionsStatus;
	}

	/**
	 * @param questionsStatus the questionsStatus to set
	 */
	public void setQuestionsStatus(Question questionsStatus) {
		this.questionsStatus = questionsStatus;
	}

	/**
	 * @return the answerStatus
	 */
	public Answer getAnswerStatus() {
		return answerStatus;
	}

	/**
	 * @param answerStatus the answerStatus to set
	 */
	public void setAnswerStatus(Answer answerStatus) {
		this.answerStatus = answerStatus;
	}

	/**
	 * @return the assignmentId
	 */
	public AssignmentId getAssignmentId() {
		return assignmentId;
	}

	/**
	 * @param assignmentId the assignmentId to set
	 */
	public void setAssignmentId(AssignmentId assignmentId) {
		this.assignmentId = assignmentId;
	}
}
