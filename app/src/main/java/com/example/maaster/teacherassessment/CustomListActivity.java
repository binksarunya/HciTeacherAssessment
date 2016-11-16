package com.example.maaster.teacherassessment;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomListActivity extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] name;
    private final String[] course;
    private final String[] section;
    private final Integer[] imageId;
    public CustomListActivity(Activity context,
                              Integer[] imageId, String[] name,String[] course,String[] section) {
        super(context,R.layout.list_single,name);
        this.context = context;
        this.imageId = imageId;
        this.name = name;
        this.course = course;
        this.section = section;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
        TextView txtTeacherName = (TextView) rowView.findViewById(R.id.name);
        TextView txtCourse = (TextView) rowView.findViewById(R.id.course);
        TextView txtSection = (TextView) rowView.findViewById(R.id.section);
        ImageView zoombtn = (ImageView) rowView.findViewById(R.id.zoom);
        zoombtn.setImageResource(R.drawable.zoom_icon);
        txtTeacherName.setText(name[position]);
        txtCourse.setText(course[position]);
        txtSection.setText(section[position]);
        imageView.setImageResource(imageId[position]);


        return rowView;
    }

    public void openAssessmentActivity(View view) {

        Intent intent = new Intent(context, AssessmentActivity.class);
        context.startActivity(intent);
    }
}


