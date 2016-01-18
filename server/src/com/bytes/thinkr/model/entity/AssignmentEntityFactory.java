/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.entity;

import com.bytes.thinkr.model.ValidationInfo;
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

        Task task = new Task();
        task.setName("Auto generated assignment " + i);
        task.setDuration((int) (Math.random()*90));

        List<Question> questions = new ArrayList<>();
        questions.add(QuestionEntityFactory.getInstance().create(i));
        task.setQuestions(questions);

        int pointTotal = 0;
        int pointEarned = 0;
        for (Question q : questions) {
            pointTotal += q.getAnswer().getPoint().getMax();
            pointEarned += q.getAnswer().getPoint().getEarned();
        }

        Assignment assignment = new Assignment(
                task, calcFutureDate(),
                AccountEntityFactory.getInstance().createTeacher(i),
                AccountEntityFactory.getInstance().createParent(i),
                AccountEntityFactory.getInstance().createStudent(i),
                new ValidationInfo());

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
