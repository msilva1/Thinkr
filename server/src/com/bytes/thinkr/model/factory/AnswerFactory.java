/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.factory;

import com.bytes.thinkr.model.IValidationEnum;
import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.entity.assignment.Answer;

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
}
