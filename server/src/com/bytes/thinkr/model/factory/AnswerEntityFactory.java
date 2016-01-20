/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.entity.assignment.Answer;
import com.bytes.thinkr.model.entity.assignment.Point;

import java.util.List;

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

    @Override
    public String getEntityName() {
        return Account.class.getSimpleName();
    }

    @Override
    protected boolean saveSubEntities(List<Answer> entity) {
        return false;
    }

    public static AnswerEntityFactory getInstance() {
        if (instance == null) {
            instance = new AnswerEntityFactory();
        }
        return instance;
    }

}
