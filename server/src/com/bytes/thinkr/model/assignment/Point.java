package com.bytes.thinkr.model.assignment;

/**
 * Created by Kent on 1/8/2016.
 */
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
