/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.entity;

import com.bytes.thinkr.model.assignment.Answer;
import com.bytes.thinkr.model.assignment.Point;

/**
 *
 */
public class AnswerEntityFactory extends EntityFactory<Answer> {

    private static AnswerEntityFactory instance;

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

    @Override
    public String getDisplayInfo(Answer entity) {
        return entity.toString();
    }

    public static AnswerEntityFactory getInstance() {
        if (instance == null) {
            instance = new AnswerEntityFactory();
        }
        return instance;
    }

}
