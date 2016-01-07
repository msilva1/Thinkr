package com.bytes.hack.model.assignment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "Assignment")
@XmlAccessorType(XmlAccessType.FIELD)
public class Assignment {

	/** The list of assignment categories */
	public enum Category {
		Unspecified, Homework, Project, Extra, Others
	}

	/** 
	 * Indicates that this assignment already existed
	 */
	public static final Assignment EXISTING;
	public static final Assignment INVALID;
	public static final Assignment ASSIGNED;
	public static final Assignment AlREADY_ASSIGNED;
	
	static {
		
		EXISTING = new Assignment();
		EXISTING.getValidation().setAssignmentStatus(AssignmentValidation.Assignment.Existing);
		
		INVALID = new Assignment();
		INVALID.getValidation().setAssignmentStatus(AssignmentValidation.Assignment.Invalid);
		
		ASSIGNED = new Assignment();
		ASSIGNED.getValidation().setAssignmentStatus(AssignmentValidation.Assignment.Assigned);

		AlREADY_ASSIGNED = new Assignment();
		AlREADY_ASSIGNED.getValidation().setAssignmentStatus(AssignmentValidation.Assignment.Assigned);
	}
	
	
	/** The user specified name for this assignment */
	private String name;
	
	/** 
	 * The system generated id for this assignment 
	 *   id = {teacher user id} + {assignment name} + {assignment category}  
	 */
	private String id;

	/** The date the assignment was created or updated */
	@XmlTransient
	private Date createdDate;
	
	@XmlElement
	private List<Question> questions;
	
	/** The assignment <code>Category</code> */
	private Category category;
	
	/** The assignment duration in minutes */
	private int duration;
	
	/** The assignment validation information */
	@XmlElement 
	private AssignmentValidation validation;

	public Assignment() {
		this(new AssignmentValidation());
	}

	public Assignment(AssignmentValidation validation) {

		setName("Unspecified");
		setCategory(Category.Others);
		
		ArrayList<Question> questions = new ArrayList<Question>();
		questions.add(new Question());
		setQuestions(questions);
		
		setValidation(validation);
		setDuration(0);
		setCreatedDate(new Date());
	}
	
	public Assignment(String name, Category category, List<Question> question, int duration) {
		
		setName(name);
		setCategory(category);
		setQuestions(question);
		
		setValidation(validation);
		setDuration(0);
		setCreatedDate(new Date());
	}
		
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the questions
	 */
	public List<Question> getQuestions() {
		return questions;
	}

	/**
	 * @param questions
	 *            the questions to set
	 */
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the validation
	 */
	public AssignmentValidation getValidation() {
		return validation;
	}

	/**
	 * @param validation the validation to set
	 */
	public void setValidation(AssignmentValidation validation) {
		this.validation = validation;
	}
}
