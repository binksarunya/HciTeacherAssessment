package com.example.maaster.teacherassessment.Model;

/**
 * Created by Administrator on 16/11/2559.
 */

public class Point {

    private String part;
    private Integer point;

    public Point(String part, Integer point) {
        this.part = part;
        this.point = point;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
