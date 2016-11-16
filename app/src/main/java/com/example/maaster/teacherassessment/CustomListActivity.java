package com.example.maaster.teacherassessment;


import android.app.Activity;
import android.content.Intent;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.*;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

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


