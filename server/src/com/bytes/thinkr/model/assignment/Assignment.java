package com.bytes.thinkr.model.assignment;

import com.bytes.thinkr.model.account.Account;
import com.bytes.thinkr.model.entity.IEntity;
import com.bytes.thinkr.model.ValidationInfo;

import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Assignment implements IEntity {

    //region static instances
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
    //endregion

    //region fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignmentId")
    private Long id;

    @OneToOne
    @XmlElement
    private Task task;

    @OneToOne
    @XmlElement
    private Account teacher;

    @OneToOne
    @XmlElement
    private Account parent;

    @OneToOne
    @XmlElement
    private Account student;

    @Embedded
    @XmlElement
    private Score score;

    @Temporal(value = TemporalType.DATE)
    @XmlTransient
    private Date assignedDate;

    @Temporal(value = TemporalType.DATE)
    @XmlTransient
    private Date dueDate;


    @Transient
    @XmlElement
    private ValidationInfo validation;

    //endregion

    public Assignment() {
        this(new ValidationInfo());
    }

    public Assignment(ValidationInfo validation) {
        this(new Task(), null, null, null, null, validation);
    }

    public Assignment(
            Task task, Date dueDate,
            Account teacher, Account parent, Account student,
            ValidationInfo validation) {

        setTask(task);
        setDueDate(dueDate);
        setTeacher(teacher);
        setParent(parent);
        setStudent(student);
        setValidation(validation);
        setAssignedDate(new Date());
    }

    public String toString() {

        return String.format("%1$s assigns %2$s to %3$s. Due on %4$s ",
                getTeacher(), getTask().getName(), getStudent(), getDueDate().toString());
    }

    //region properties
    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public ValidationInfo getValidation() {
        return validation;
    }

    public void setValidation(ValidationInfo validation) {
        this.validation = validation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Account getTeacher() {
        return teacher;
    }

    public void setTeacher(Account teacher) {
        this.teacher = teacher;
    }

    public Account getParent() {
        return parent;
    }

    public void setParent(Account parent) {
        this.parent = parent;
    }

    public Account getStudent() {
        return student;
    }

    public void setStudent(Account student) {
        this.student = student;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    //endregion

}
