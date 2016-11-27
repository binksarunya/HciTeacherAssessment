package com.example.maaster.teacherassessment.Model;

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

import com.example.maaster.teacherassessment.AssessmentActivity;
import com.example.maaster.teacherassessment.ListActivity;
import com.example.maaster.teacherassessment.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.example.maaster.teacherassessment.R.id.course;

/**
 * Created by Demos on 11/26/2016.
 */

public class EditAssesListActivity extends ArrayAdapter<String> {
    private final Activity context;
    private final Context contxt;
    private final String questions[];

    public EditAssesListActivity(Activity context, String questions[]) {
        super(context, R.layout.question_list_single,questions);
        this.context=context;
        this.contxt=context;
        this.questions=questions;

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.question_list_single, null, true);
        TextView question = (TextView)rowView.findViewById(R.id.question);
        question.setText("  "+questions[position]);
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ไปหน้าตำถามที่ดลือก*/
                Log.d("click", "onClick: ");
                Intent intent = new Intent(context, AssessmentActivity.class);
                intent.putExtra("checkEdit", true);
                intent.putExtra("positionAns" , position);
                context.startActivity(intent);
            }
        });


        return rowView;
    }
}
