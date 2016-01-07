package com.bytes.hack.model.assignment;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Question {

	public enum Subject {

		Undefined, 
		Math, 
		Science, 
		History, 
		Language, 
		Other
	}
	
	public enum AnswerType {
		Text, 
		Choice, 
		Other
	}

	private String question;
	private List<Answer> answers;	
	private boolean isCompleted;
	private int points;
	private Answer answer;
	
	private Subject subject;

	public Question() {
		setSubject(Subject.Undefined);
		setAnswer(new Answer());
		setQuestion("This is a sample question?");
	}
	
}
