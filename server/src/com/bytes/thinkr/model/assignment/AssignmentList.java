package com.bytes.thinkr.model.assignment;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AssignmentList {
	
	
	private Set<Assignment> assignments;

	public AssignmentList() {
		assignments = new HashSet<Assignment>();
	}

	/**
	 * @return the assignments
	 */
	public Set<Assignment> getAssignments() {
		return assignments;
	}

	/**
	 * @param assignments the assignments to set
	 */
	public void setAssignments(Set<Assignment> assignments) {
		this.assignments = assignments;
	}

}