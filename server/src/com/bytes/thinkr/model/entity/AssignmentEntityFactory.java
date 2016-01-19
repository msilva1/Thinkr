/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.entity;

import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.account.Account;
import com.bytes.thinkr.model.assignment.Assignment;
import com.bytes.thinkr.model.assignment.Task;
import com.bytes.thinkr.model.assignment.Question;
import com.bytes.thinkr.model.assignment.Score;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssignmentEntityFactory extends EntityFactory<Assignment> {

    private static AssignmentEntityFactory instance;

    public static AssignmentEntityFactory getInstance() {
        if (instance == null) {
            instance = new AssignmentEntityFactory();
        }
        return instance;
    }


    @Override
    protected Assignment create(int i) {

        List<Account> accounts = new ArrayList<>();
        accounts.add(AccountEntityFactory.getInstance().createTeacher(i));
        accounts.add(AccountEntityFactory.getInstance().createParent(i));
        accounts.add(AccountEntityFactory.getInstance().createStudent(i));

        Assignment assignment = new Assignment(
                TaskEntityFactory.getInstance().create(i),
                calcFutureDate(),
                accounts,
                new ValidationInfo());

        Task task = assignment.getTask();
        List<Question> questions = task.getQuestions();

        int pointTotal = 0;
        int pointEarned = 0;
        for (Question q : questions) {
            pointTotal += q.getAnswer().getPoint().getMax();
            pointEarned += q.getAnswer().getPoint().getEarned();
        }

        assignment.setScore(new Score((double) pointEarned / (double) pointTotal));
        return assignment;
    }

    /**
     * Returns a date one week from the current time.
     * @return
     */
    private Date calcFutureDate() {
        return new Date(System.currentTimeMillis() + 604800000);
    }

    @Override
    public String getDisplayInfo(Assignment entity) {
        return entity.getTask().getName();
    }
}
