package com.bytes.thinkr.model.assignment;

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
		Unspecified,
		Homework,
		Project,
		Extra,
		Others
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

	@XmlElement
	private Score score;

	private String name;
	
	private String id;

	@XmlTransient
	private Date createdDate;
	
	@XmlElement
	private List<Question> questions;
	
	private Category category;
	
	private int duration;
	
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
		
		setValidation(getValidation());
		setDuration(0);
		setCreatedDate(new Date());
	}


	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	/** The user specified name for this assignment */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * The system generated id for this assignment
	 *   id = {teacher user id} + {assignment name} + {assignment category}
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/** The date the assignment was created or updated */
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	/** The assignment <code>Category</code> */
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	/** The assignment duration in minutes */
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	/** The assignment validation information */
	public AssignmentValidation getValidation() {
		return validation;
	}

	public void setValidation(AssignmentValidation validation) {
		this.validation = validation;
	}


}
