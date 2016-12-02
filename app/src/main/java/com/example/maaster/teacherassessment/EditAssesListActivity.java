package com.example.maaster.teacherassessment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

    public EditAssesListActivity(Activity context, String questionsDetail[],ArrayList<Question> questions) {
        super(context, R.layout.question_list_single,questionsDetail);
        this.context=context;
        this.contxt=context;
        this.questionsDetail=questionsDetail;

        this.points=new String[questions.size()];
        this.questions = questions;
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
