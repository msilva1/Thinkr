/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.entity;

import com.bytes.thinkr.model.assignment.Question;

/**
 * Created by Kent on 1/13/2016.
 */
public class QuestionEntityFactory extends EntityFactory<Question> {

    private static QuestionEntityFactory instance;

    public static QuestionEntityFactory getInstance() {
        if(instance == null) {
            instance = new QuestionEntityFactory();
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
        question.setAnswer(AnswerEntityFactory.getInstance().create(i));

        return question;
    }

    @Override
    public String getDisplayInfo(Question entity) {
        return entity.getQuestion();
    }
}
