package com.bytes.thinkr.model.assignment;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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

    private Subject subject;
    private Answer answer;
    private String question;
    private boolean isCompleted;
    private boolean isAttempted;

    public Question() {
        setSubject(Subject.Undefined);
        setAnswer(new Answer());
        setQuestion("This is a sample question?");
    }

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

}
