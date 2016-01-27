/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.entity.account;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Embeddable
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Name {

    private static final String DEFAULT_FIRST_NAME = "Unknown";
    private static final String DEFAULT_LAST_NAME = "Unknown";
    private static final String DEFAULT_MIDDLE_NAME = "";


    private String first;

    private String last;

    private String middle;

    private String suffix;

    private String prefix;

    public Name() {
        this(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_MIDDLE_NAME);
    }

    public Name(String first) {
        this(first, DEFAULT_LAST_NAME, DEFAULT_MIDDLE_NAME);
    }

    public Name(String first, String last, String middle) {
        setFirst(first);
        setLast(last);
        setMiddle(middle);
    }

    //region properties

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    // endregion
}
