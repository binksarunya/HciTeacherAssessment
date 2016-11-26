package com.example.maaster.teacherassessment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView;

import com.example.maaster.teacherassessment.Model.Course;
import com.example.maaster.teacherassessment.Model.Student;
import com.example.maaster.teacherassessment.Model.Teacher;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

public class ListActivity extends Activity {

    private Student student;
    private ArrayList<Teacher> teachers;
    private ArrayList<Course> coursesList;
    private final String TAG = "click";

    private Context context;
    private ImageView iv, imageView;




    Integer[] imageId = {
            R.drawable.im_1,
            R.drawable.im_2,
            R.drawable.im_3,
            R.drawable.im_4
    };
    String[] name = {
            "ดร.ประภาพร รัตรธำรง",
            "ดร.มนวรรัตน์ ผ่องไพบูลย์",
            "ดร.วนิดา พฤทธิวิทยา",
            "ผศ.ดร.เด่นดวง ประดับสุวรรณ"
    } ;
    /*"ดร.ประภาพร รัตรธำรง",
            "ดร.มนวรรัตน์ ผ่องไพบูลย์",
            "ดร.วนิดา พฤทธิวิทยา",
            "ผศ.ดร.เด่นดวง ประดับสุวรรณ"*/
    String[] courseName = {
            "CS374",
            "CS374",
            "CS342",
            "CS223"
    } ;
    String[] section = {
            "40001",
            "40002",
            "60001",
            "20001"
    } ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);



        teachers = new ArrayList<>();
        coursesList = new ArrayList<>();
        student = getIntent().getExtras().getParcelable("student");
        context = getBaseContext();
        getTeacherFromDB();
        getData();



    }

    public void getTeacherFromDB() {


        for (int i = 0; i <4 ; i++) {
            Teacher teacher = new Teacher(name[i]);
            teacher.setImageId(imageId[i]);
            teachers.add(teacher);

        }

    }

    public void getData(){

        CustomListActivity adapter = new CustomListActivity(ListActivity.this,teachers,name,courseName,section);
        ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }




}
