package com.example.maaster.teacherassessment.Model;

/**
 * Created by Administrator on 17/11/2559.
 */

public class Question {

    private String no;
    private String detail;
    private int answer;

    public Question(String no, String detail) {
        this.no = no;
        this.detail = detail;

    }

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
}
