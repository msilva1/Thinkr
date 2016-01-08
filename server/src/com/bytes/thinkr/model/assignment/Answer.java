package com.bytes.thinkr.model.assignment;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Answer {


	public enum Type {
		Single,    // Use this is there is only one provided answer
		Multiple   // Default. Use this if multiple answers are provided (multiple-choice question)
	}

	@XmlElement
	private List<String> answers;

	private Point point;
	
	private Type type;
	
	public Answer() {
		setType(Type.Multiple);
		setAnswers(new ArrayList<String>());
	}

	public void addAnswer(String s) {
		answers.add(s);
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
