/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.factory.data;

import com.bytes.thinkr.model.entity.assignment.Question;

/**
 * Created by Kent on 1/13/2016.
 */
public class QuestionDataFactory extends DataFactory<Question> {

    private static QuestionDataFactory instance;

    public static QuestionDataFactory getInstance() {
        if(instance == null) {
            instance = new QuestionDataFactory();
        }
        return instance;
    }

    @Override
    protected Question create(int i) {

        Question question = new Question();
        question.setQuestion("What is the square root of this number: " + i);
        question.setAttempted(Math.random() > 0.5);
        Question.Subject[] subjects = Question.Subject.values();
        question.setSubject(subjects[(int) Math.random() * (subjects.length - 1)]);
        question.setAnswer(AnswerDataFactory.getInstance().create(i));

        return question;
    }
}
