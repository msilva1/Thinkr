/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.util.entity;

import com.bytes.thinkr.model.assignment.Answer;
import com.bytes.thinkr.model.assignment.Point;

/**
 *
 */
public class AnswerEntityUtil extends EntityUtil<Answer> {

    private static AnswerEntityUtil instance;

    @Override
    public Answer create(int i) {

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

    public static AnswerEntityUtil getInstance() {
        if (instance == null) {
            instance = new AnswerEntityUtil();
        }
        return instance;
    }

}
