/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.entity.assignment.Question;

import java.util.List;

/**
 * Created by Kent on 1/13/2016.
 */
public class QuestionFactory extends EntityFactory<Question> {

    private static QuestionFactory instance;
    public static QuestionFactory getInstance() {
        if(instance == null) {
            instance = new QuestionFactory();
        }
        return instance;
    }

    @Override
    protected boolean saveSubEntities(List<Question> entity) {
        // Using cascade
        return true;
    }
}
