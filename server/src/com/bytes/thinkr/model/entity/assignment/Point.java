/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.model.entity.assignment;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Embeddable
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Point {

    private int min;
    private int max;
    private int earned;

    public Point() {
        setMin(0);
        setMax(1);
        setEarned(0);
    }

    public Point(int earned) {
        setMin(0);
        setMax(earned);
        setEarned(earned);
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getEarned() {
        return earned;
    }

    public void setEarned(int earned) {
        this.earned = earned;
    }
}
