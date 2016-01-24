/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.factory.data;

import com.bytes.thinkr.model.entity.assignment.Answer;
import com.bytes.thinkr.model.entity.assignment.Point;

/**
 *
 */
public class AnswerDataFactory extends DataFactory<Answer> {

    private static AnswerDataFactory instance;
    public static AnswerDataFactory getInstance() {
        if (instance == null) {
            instance = new AnswerDataFactory();
        }
        return instance;
    }

    @Override
    protected Answer create(int i) {

        Answer answer = new Answer();
        int p = (Math.random() > 0.5) ? 1 : 0;
        Point point = new Point(p);
        point.setMax(1);
        answer.setPoint(point);
        answer.addAnswer("This is answer " + i + "a");
        answer.addAnswer("This is answer " + i + "b");
        answer.addAnswer("This is answer " + i + "c");
        answer.addAnswer("This is answer " + i + "d");
        return answer;
    }
}
