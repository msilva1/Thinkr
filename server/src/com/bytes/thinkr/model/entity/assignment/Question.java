/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.entity.assignment;

import com.bytes.thinkr.model.entity.IEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Question implements IEntity {

    public enum Subject {
        Undefined,
        Math,
        Science,
        History,
        Language,
        Other
    }

    //region fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "questionId")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Answer answer;

    private Subject subject;

    private String question;

    private boolean isCompleted;

    private boolean isAttempted;

    //endregion

    public Question() {
        setSubject(Subject.Undefined);
        setQuestion("What is a sample question?");
        setAnswer(new Answer());
    }

    @Override
    public String toString() {
        return getQuestion();
    }

    //region property
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isAttempted() {
        return isAttempted;
    }

    public void setAttempted(boolean attempted) {
        isAttempted = attempted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //endregion
}
