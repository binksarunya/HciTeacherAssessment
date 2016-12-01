package com.example.maaster.teacherassessment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import java.util.HashMap;

import static com.example.maaster.teacherassessment.R.id.course;

/**
 * Created by Demos on 11/26/2016.
 */

public class ShowCompleteListActivity extends ArrayAdapter<String> {
    private final Activity context;
    private  String questionsDetail[];

    private ArrayList<Question> questions;
    private  String points[];


    public ShowCompleteListActivity(Activity context, String questionsDetail[],HashMap<String,ArrayList<Question>> teacherresult,String teachername) {
        super(context, R.layout.question_list_single,questionsDetail);
        this.context=context;
        this.questionsDetail=questionsDetail;
        this.questions = teacherresult.get(teachername);
        this.points=new String[teacherresult.get(teachername).size()];
        for (int i=0;i<points.length;i++){
            points[i]=String.valueOf(questions.get(i).getAnswer()+1);
        }

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.question_list_single, null, true);
        TextView question = (TextView)rowView.findViewById(R.id.question);
        TextView point = (TextView)rowView.findViewById(R.id.point);
        point.setText(points[position]);
        question.setText("  "+questionsDetail[position]);

        return rowView;
    }
}
