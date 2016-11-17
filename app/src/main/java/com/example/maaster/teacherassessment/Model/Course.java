package com.example.maaster.teacherassessment.Model;

/**
 * Created by Administrator on 16/11/2559.
 */

public class Course {
    private String name;
    private String section;
    private Boolean complete;

    public Course(String name, String section) {
        this.name = name;
        this.section = section;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public Boolean getComplete() {
        return complete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
