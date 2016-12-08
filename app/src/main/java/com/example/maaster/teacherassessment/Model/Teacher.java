package com.example.maaster.teacherassessment.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 16/11/2559.
 */

public class Teacher implements  Parcelable{

    private String name;
    private ArrayList<Course> courses;
    private ArrayList<Point> points;
    private Boolean complete;
    private String image;
    private FileInputStream imageFile;
    private Integer imageId;



    public Teacher(String name) {
        courses = new ArrayList<>();
        points = new ArrayList<>();
        this.name = name;

    }

    protected Teacher(Parcel in) {
        name = in.readString();
        image = in.readString();
        imageId = in.readInt();
    }


    public static final Creator<Teacher> CREATOR = new Creator<Teacher>() {
        @Override
        public Teacher createFromParcel(Parcel in) {
            return new Teacher(in);
        }

        @Override
        public Teacher[] newArray(int size) {
            return new Teacher[size];
        }
    };

    public FileInputStream getImageFile() {
        return imageFile;
    }

    public void setImageFile(FileInputStream imageFile) {
        this.imageFile = imageFile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }


    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeInt(imageId);
    }
}
