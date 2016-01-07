package com.bytes.hack.model.assignment;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Answer {

	public enum Type {
		Text, Choice, Other
	}

	private List<String> answer;
	
	private Type type;
	
	public Answer() {
		setType(Type.Choice);
		setAnswer(new ArrayList<String>());
	}

	/**
	 * @return the answer
	 */
	public List<String> getAnswer() {
		return answer;
	}
	
	/**
	 * Add an answer to the answer list
	 * @param answer
	 * @return
	 */
	public boolean addAnswer(String answer) {
		return this.answer.add(answer);
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(List<String> answer) {
		this.answer = answer;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

}
