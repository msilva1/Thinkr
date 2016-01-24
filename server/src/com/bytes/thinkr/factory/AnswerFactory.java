/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.factory;

import com.bytes.thinkr.model.entity.assignment.Answer;
import com.bytes.thinkr.factory.merge.MergeFactory;

import java.util.List;

public class AnswerFactory extends EntityFactory<Answer> {

    private static AnswerFactory instance;
    public static AnswerFactory getInstance() {
        if (instance == null) {
            instance = new AnswerFactory();
        }
        return instance;
    }

    @Override
    protected boolean saveSubEntities(List<Answer> entity) {
        return false;
    }

    @Override
    protected MergeFactory<Answer> getMergeFactory() {

        return null;
    }
}
