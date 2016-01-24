/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model;

import com.bytes.thinkr.model.entity.IEntity;

/**
 * Created by Kent on 1/23/2016.
 */
public class FactoryResponse<T extends IEntity> {

    private T entity;

    private ValidationInfo validationInfo;

    public FactoryResponse() {
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

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
