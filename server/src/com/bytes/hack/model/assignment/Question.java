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
    private Score score;
    private Answer answer;
    private Subject subject;

    public Question() {
        setSubject(Subject.Undefined);
        setAnswer(new Answer());
        setQuestion("This is a sample question?");
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
