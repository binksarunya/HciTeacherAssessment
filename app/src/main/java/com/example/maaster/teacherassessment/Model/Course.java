package com.example.maaster.teacherassessment.Model;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 16/11/2559.
 */

public class Course implements Parcelable{
    private String name;
    private String section;
    private int complete;
    private ArrayList<Question> questions;

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public Course(String name, String section) {
        this.name = name;
        this.section = section;
        this.complete = 0;
        questions = new ArrayList<>();
    }

    protected Course(Parcel in) {
        name = in.readString();
        section = in.readString();
        complete = in.readInt();

    }



    public void setComplete(Integer complete) {
        this.complete = complete;
    }

    public Integer getComplete() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(section);
        parcel.writeInt(complete);


    }

    public static Creator<Course> CREATOR = new Creator<Course>() {

        @Override
        public Course createFromParcel(Parcel source) {
            return new Course(source);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }

    };
}

