/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory.data;

import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.entity.assignment.Assignment;
import com.bytes.thinkr.model.entity.assignment.Question;
import com.bytes.thinkr.model.entity.assignment.Score;
import com.bytes.thinkr.model.entity.assignment.Task;
import com.bytes.thinkr.service.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class AssignmentDataFactory extends DataFactory<Assignment> {

    private static AssignmentDataFactory instance;
    public static AssignmentDataFactory getInstance() {
        if (instance == null) {
            instance = new AssignmentDataFactory();
        }
        return instance;
    }


    @Override
    protected Assignment create(int i) {

        List<Account> accounts = new ArrayList<>();
        accounts.add(AccountDataFactory.getInstance().createTeacher(i));
        accounts.add(AccountDataFactory.getInstance().createParent(i));
        accounts.add(AccountDataFactory.getInstance().createStudent(i));

        Assignment assignment = new Assignment(
                TaskDataFactory.getInstance().create(i),
                DateUtil.calcFutureDate(4),
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
}
