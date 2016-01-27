/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model;

import com.bytes.thinkr.model.entity.IEntity;

import java.util.List;

/**
 * Created by Kent on 1/23/2016.
 */
public class FactoryResponseList<T extends IEntity> {

    private List<T> entities;

    private ValidationInfo validationInfo;

    public FactoryResponseList() {
        setValidationInfo(new ValidationInfo());
    }

    public ValidationInfo addValidation(ValidationInfo.Type type, IValidationEnum validation) {
        return getValidationInfo().add(type, validation);
    }

    public ValidationInfo getValidationInfo() {
        return validationInfo;
    }

    public void setValidationInfo(ValidationInfo validationInfo) {
        this.validationInfo = validationInfo;
    }


    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }
}
