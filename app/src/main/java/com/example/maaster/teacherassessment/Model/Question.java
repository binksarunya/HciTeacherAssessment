package com.example.maaster.teacherassessment.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RadioButton;

import java.util.ArrayList;

/**
 * Created by Administrator on 17/11/2559.
 */

public class Question implements Parcelable{

    private String no;
    private String detail;
    private int answer;


    public Question(String no, String detail) {
        this.no = no;
        this.detail = detail;
        answer = -1;
    }

    protected Question(Parcel in) {
        no = in.readString();
        detail = in.readString();
        answer = in.readInt();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(no);
        parcel.writeString(detail);
        parcel.writeInt(answer);
    }
}
