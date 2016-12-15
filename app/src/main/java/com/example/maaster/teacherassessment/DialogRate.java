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

public class DialogRate extends ArrayAdapter<String> {
    private final Activity context;
    private String rate[];
    private String text[];


    public DialogRate(Activity context, String text[], String rate[]) {
        super(context, R.layout.question_list_single,text);
        this.context=context;
        this.rate = rate;
        this.text = text;


    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.question_option_list_single, null, true);
        TextView textView = (TextView) rowView.findViewById(R.id.question2);
        TextView rateText = (TextView) rowView.findViewById(R.id.point2);


        textView.setText(text[position]);
        rateText.setText("");

        return rowView;
    }
}
