package com.bytes.thinkr.model.assignment;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Kent on 1/6/2016.
 */

@Embeddable
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Score {

    private double score;

    public Score() {
        setScore(0);
    }

    public Score(double score) {
        setScore(score);
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
