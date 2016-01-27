/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.factory;

import com.bytes.thinkr.factory.merge.MergeFactory;
import com.bytes.thinkr.model.entity.assignment.Question;

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
    protected MergeFactory<Question> getMergeFactory() {
        return null;
    }
}
