/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.util.entity;

import com.bytes.thinkr.model.assignment.Question;

/**
 * Created by Kent on 1/13/2016.
 */
public class QuestionEntityUtil extends EntityUtil<Question> {

    private static QuestionEntityUtil instance;

    public static QuestionEntityUtil getInstance() {
        if(instance == null) {
            instance = new QuestionEntityUtil();
        }
        return instance;
    }

    @Override
    public Question create(int i) {

        Question question = new Question();
        question.setQuestion("What is the square root of this number: " + i);
        question.setAttempted(Math.random() > 0.5);
        Question.Subject[] subjects = Question.Subject.values();
        question.setSubject(subjects[(int) Math.random() * (subjects.length - 1)]);
        question.setAnswer(AnswerEntityUtil.getInstance().create(i));

        return question;
    }
}
