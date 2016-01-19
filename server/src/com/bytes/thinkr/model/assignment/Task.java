/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.assignment;

import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.entity.IEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kent on 1/17/2016.
 */
@Entity
@XmlRootElement
public class Task implements IEntity {

    private static final String DEFAULT_NAME = "Unspecified";


    /**  The list of assignment categories */
    public enum Category {
        Unspecified,
        Homework,
        Project,
        Extra,
        Others
    }

    //region fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taskId")
    private Long id;

    private String name;

    private String identifier;

    @Temporal(value = TemporalType.DATE)
    @XmlTransient
    private Date createdDate;

    @OneToMany
    @XmlElement
    private List<Question> questions;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    private int duration;

    @Transient
    @XmlElement
    private ValidationInfo validation;

    //end region

    public Task() {
        this(new ValidationInfo());
    }

    public Task(ValidationInfo validation) {
        this(DEFAULT_NAME, Category.Unspecified, new ArrayList<Question>(), 0, validation);
    }

    public Task(
            String name, Category category,
            List<Question> question, int duration) {
        this(name, category, question, duration, new ValidationInfo());

    }

    public Task(String name, Category category, List<Question> question, int duration, ValidationInfo validation) {

        setName(name);
        setQuestions(question);
        setCategory(category);
        setDuration(duration);
        setValidation(validation);
        setCreatedDate(new Date());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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
