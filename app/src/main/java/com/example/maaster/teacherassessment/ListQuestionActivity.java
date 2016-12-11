package com.example.maaster.teacherassessment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.maaster.teacherassessment.Model.Question;

import java.util.ArrayList;

/**
 * Created by Administrator on 1/12/2559.
 */

public class ListQuestionActivity extends ArrayAdapter<String> {

    private final Activity context;
    private final Context contxt;
    private  String questionsDetail[];
    private ArrayList<Question> questions;
    private  String points[];

    public ListQuestionActivity(Activity context, String questionsDetail[], ArrayList<Question> questions) {
        super(context, R.layout.question_list_single,questionsDetail);
        this.context=context;
        this.contxt=context;
        this.questionsDetail=questionsDetail;
        this.questions = questions;

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.question_list_single, null, true);
        TextView question = (TextView)rowView.findViewById(R.id.question);
        if(position==9 || position==21 || position==0) {
            question.setTypeface(null, Typeface.BOLD);
        }
        question.setText(questionsDetail[position]);


        return rowView;
    }
}
