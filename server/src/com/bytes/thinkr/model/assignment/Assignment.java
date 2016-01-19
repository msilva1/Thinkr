package com.bytes.thinkr.model.assignment;

import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.account.Account;
import com.bytes.thinkr.model.entity.IEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @OneToMany
    @XmlElement
    private List<Account> accounts;

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
        this(new Task(), new Date(), new ArrayList<Account>(), validation);
    }

    public Assignment(
            Task task, Date dueDate,
            List<Account> accounts,
            ValidationInfo validation) {

        setTask(task);
        setDueDate(dueDate);
        setAccounts(accounts);
        setValidation(validation);
        setAssignedDate(new Date());
    }

    public String toString() {

        return String.format("Task %1$s is assigned to %2$s accounts %3$s. Due on %4$s ",
            getTask().getName(), accounts.size(), getDueDate().toString());
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

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    //endregion

}
