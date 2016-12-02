package com.example.maaster.teacherassessment;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.maaster.teacherassessment.Model.Question;

import java.util.ArrayList;

/**
 * Created by Administrator on 2/12/2559.
 */

public class ShowResultAnswer extends ArrayAdapter<String> {
    private final ArrayList<Question> questions;
    private final Activity context;
    private final LayoutInflater inflater;
    public ShowResultAnswer(Activity context, ArrayList<Question> questions ,String answer[]) {
        super(context, R.layout.result_answer_single, answer);
        this.context = context;
        this.questions = questions;
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.result_answer_single, parent, false);
        TextView question = (TextView) vi.findViewById(R.id.pun);
        TextView answer = (TextView) vi.findViewById(R.id.answer);
        question.setText(questions.get(position).getDetail()+"/t"+questions.get(position).getAnswer());

        return vi;

    }
}
