package com.bytes.thinkr.model.assignment;

import com.bytes.thinkr.model.ValidationInfo;

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

    /**
     * The list of assignment categories
     */
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
        EXISTING.getValidation().add(ValidationInfo.Type.Assignment, ValidationInfo.Assignment.Existing);

        INVALID = new Assignment();
        INVALID.getValidation().add(ValidationInfo.Type.Assignment, ValidationInfo.Common.Invalid);

        ASSIGNED = new Assignment();
        ASSIGNED.getValidation().add(ValidationInfo.Type.Assignment, ValidationInfo.Assignment.Assigned);

        AlREADY_ASSIGNED = new Assignment();
        AlREADY_ASSIGNED.getValidation().add(ValidationInfo.Type.Assignment, ValidationInfo.Assignment.Assigned);
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
    private ValidationInfo validation;

    public Assignment() {
        this(new ValidationInfo());
    }

    public Assignment(ValidationInfo validation) {

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ValidationInfo getValidation() {
        return validation;
    }

    public void setValidation(ValidationInfo validation) {
        this.validation = validation;
    }
}
