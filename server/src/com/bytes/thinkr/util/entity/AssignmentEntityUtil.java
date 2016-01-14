/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.util.entity;

import com.bytes.thinkr.model.assignment.Assignment;
import com.bytes.thinkr.model.assignment.Question;
import com.bytes.thinkr.model.assignment.Score;

import java.util.ArrayList;
import java.util.List;

public class AssignmentEntityUtil extends EntityUtil<Assignment> {

    private static AssignmentEntityUtil instance;

    public static AssignmentEntityUtil getInstance() {
        if (instance == null) {
            instance = new AssignmentEntityUtil();
        }
        return instance;
    }


    @Override
    public Assignment create(int i) {

        Assignment assignment = new Assignment();
        List<Question> questions = new ArrayList<>();
        questions.add(QuestionEntityUtil.getInstance().create(i));
        assignment.setQuestions(questions);

        int pointTotal = 0;
        int pointEarned = 0;
        for (Question q : questions) {
            pointTotal += q.getAnswer().getPoint().getMax();
            pointEarned += q.getAnswer().getPoint().getEarned();
        }

        assignment.setScore(new Score((double) pointEarned/(double) pointTotal));
        return assignment;
    }
}
