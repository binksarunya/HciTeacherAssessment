package com.example.maaster.teacherassessment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.maaster.teacherassessment.Model.Question;

import com.example.maaster.teacherassessment.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;
import static com.example.maaster.teacherassessment.R.id.course;

/**
 * Created by Demos on 11/26/2016.
 */

public class EditAssesListActivity extends ArrayAdapter<String> {
    private final Activity context;
    private final Context contxt;
    private  String questionsDetail[];
    private ArrayList<Question> questions;
    private  String points[];
    private ArrayList<Question> questionsList;

    String part[] = {"ส่วนที่ 1 ข้อคำถามกลางของมหาวิทยาลัย","ส่วนที่ 2 ข้อคำถามของคณะ/หน่วยงาน","ส่วนที่ 3 สภาพแวดล้อมและสิ่งสนับสนุนการเรียนรู้"};


    public EditAssesListActivity(Activity context, String questionsDetail[],ArrayList<Question> questions) {
        super(context, R.layout.question_list_single,questionsDetail);
        this.context=context;
        this.contxt=context;
        this.questionsDetail=questionsDetail;
        questionsList = new ArrayList<>();
        this.points=new String[questions.size()];
        this.questions = questions;
        for (int i=0;i<points.length;i++){
            points[i]=String.valueOf(questions.get(i).getAnswer()+1);
        }
        int j= 0;
        for (int i = 0; i < 23 ; i++) {
            Question question;
            if(i==8 || i==19 || i==0) {
                question = new Question("", part[j]);
                question.setAnswer(0);
                j++;
                questionsList.add(question);
            }
            question = new Question(questions.get(i).getNo(), questions.get(i).getDetail());
            question.setAnswer(questions.get(i).getAnswer());

            questionsList.add(question);
        }

        for (int i = 0; i < questionsList.size(); i++) {
            Log.d("pun", "EditAssesListActivity: " +questionsList.get(i).getDetail()+i);
        }
    }



    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.question_list_single, null, true);
        TextView question = (TextView)rowView.findViewById(R.id.question);
        TextView point = (TextView)rowView.findViewById(R.id.point);

        if(position==9 || position==21 || position==0) {
            point.setText("");
            question.setText(questionsList.get(position).getDetail());
            question.setTypeface(null, Typeface.BOLD);
        } else {
            point.setText(questionsList.get(position).getAnswer()+1+"");
            question.setText(questionsList.get(position).getDetail());
        }


        return rowView;
    }
}
