/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.entity.assignment;

import com.bytes.thinkr.model.entity.IEntity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Answer implements IEntity {


	public enum Type {
		Single,    // Use this is there is only one provided answer
		Multiple   // Default. Use this if multiple answers are provided (multiple-choice question)
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answerId")
	private Long id;

	@ElementCollection
    @CollectionTable(name = "Answer_Entries")
	@XmlElement
	private List<String> answers;

	@Embedded
	@XmlElement
	private Point point;

    @Enumerated(value = EnumType.STRING)
	private Type type;
	
	public Answer() {
		setType(Type.Multiple);
		setAnswers(new ArrayList<String>());
	}

	public void addAnswer(String s) {
		answers.add(s);
	}

    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (String ans : answers) {
            sb.append(ans + System.lineSeparator());
        }

        return sb.toString();
    }

    //region properties

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    //endregion
}
