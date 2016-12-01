package com.example.maaster.teacherassessment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.maaster.teacherassessment.Model.Course;
import com.example.maaster.teacherassessment.Model.Question;
import com.example.maaster.teacherassessment.Model.Student;
import com.example.maaster.teacherassessment.Model.Teacher;

import java.util.ArrayList;

public class TeacherListActivity extends AppCompatActivity {

    private Student student;
    private ArrayList<Teacher> teachers;
    private ArrayList<Course> courses;
    private ArrayList<Question> questions;
    private final String TAG = "click";

    private Context context;
    private ImageView iv, imageView;
    private boolean checkfirst =true;
    private boolean checkassessmentcomplete = false;

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
        setContentView(R.layout.activity_teacher_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Teacher Assessment");



        context = getBaseContext();
        teachers = new ArrayList<>();
        courses = new ArrayList<>();
        student = getIntent().getExtras().getParcelable("student");
        courses = getIntent().getExtras().getParcelableArrayList("course");
        student.setCourses(courses);
        checkfirst = getIntent().getExtras().getBoolean("checkfirst");

        try {

            questions = new ArrayList<>();
            questions = getIntent().getExtras().getParcelableArrayList("questions");
            courses.get(getIntent().getExtras().getInt("position")).setQuestions(questions);

        } catch (Exception e) {

        }

        if(checkfirst==true) {
            showStudentDialog(student);
        }



        getTeacherFromDB();
        getData();

        for (int i = 0; i < courses.size() ; i++) {
            Log.d(TAG, "complete " + courses.get(i).getName() + " " + courses.get(i).getComplete());
            if(courses.get(i).getComplete()==1){

                checkassessmentcomplete=true;
            }
            else{
                checkassessmentcomplete=false;
                break;
            }
        }

        if(checkassessmentcomplete==true){
            showComplete(student);

        }


    }

    public void getTeacherFromDB() {


        for (int i = 0; i <4 ; i++) {
            Teacher teacher = new Teacher(name[i]);
            teacher.setImageId(imageId[i]);
            teachers.add(teacher);

        }

    }

    public void getData(){

        CustomListActivity adapter = new CustomListActivity(TeacherListActivity.this,teachers,name,courseName,section,student,courses);
        ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }


    public void showStudentDialog(Student student){
        final Dialog welcomedialog= new Dialog(this);
        welcomedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        welcomedialog.setContentView(R.layout.welcome_dialog);
        TextView studentname = (TextView)welcomedialog.findViewById(R.id.studentnameTextview);
        TextView studentid = (TextView)welcomedialog.findViewById(R.id.studentIdTextview);
        studentname.setText(student.getName());
        studentid.setText("รหัสนักศึกษา "+student.getId());

        Button acceptbtn = (Button)welcomedialog.findViewById(R.id.accept);
        acceptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                welcomedialog.dismiss();
            }
        });


        welcomedialog.show();

    }

    public void onClickLogout(View view){

        if(checkassessmentcomplete==false){
            showNoComplete();
        } else {

            checkLogout();
        }


    }

    public void checkLogout() {
        final Dialog welcomedialog= new Dialog(this);
        welcomedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        welcomedialog.setContentView(R.layout.no_complete_dialog);
        TextView complete = (TextView)welcomedialog.findViewById(R.id.tltle) ;
        TextView studentname = (TextView)welcomedialog.findViewById(R.id.detail);

        complete.setText("ท่านต้องการออกจากระบบหรือไม่");
        studentname.setText("");
        complete.setTextSize(18);
        studentname.setTextSize(15);


        Button acceptbtn = (Button)welcomedialog.findViewById(R.id.accept_logout);
        acceptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                welcomedialog.dismiss();
                Intent intent = new Intent(TeacherListActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button cancelbtn = (Button)welcomedialog.findViewById(R.id.cacel_logout);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                welcomedialog.dismiss();
            }
        });


        welcomedialog.show();

    }



    public void showNoComplete() {
        final Dialog welcomedialog= new Dialog(this);
        welcomedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        welcomedialog.setContentView(R.layout.no_complete_dialog);
        TextView complete = (TextView)welcomedialog.findViewById(R.id.tltle) ;
        TextView studentname = (TextView)welcomedialog.findViewById(R.id.detail);

        complete.setText("ท่ายังประเมินอาจารย์ไม่ครบ");
        studentname.setText("ท่านต้องการออกจากระบบหรือไม่");
        complete.setTextSize(18);
        studentname.setTextSize(15);


        Button acceptbtn = (Button)welcomedialog.findViewById(R.id.accept_logout);
        acceptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                welcomedialog.dismiss();
                Intent intent = new Intent(TeacherListActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button cancelbtn = (Button)welcomedialog.findViewById(R.id.cacel_logout);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                welcomedialog.dismiss();
            }
        });


        welcomedialog.show();

    }

    public void showComplete(Student student){
        final Dialog welcomedialog= new Dialog(this);
        welcomedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        welcomedialog.setContentView(R.layout.welcome_dialog);
        TextView complete = (TextView)welcomedialog.findViewById(R.id.textView8) ;
        TextView studentname = (TextView)welcomedialog.findViewById(R.id.studentnameTextview);
        TextView studentid = (TextView)welcomedialog.findViewById(R.id.studentIdTextview);
        complete.setText("ท่านได้รับโควต้าพิมพ์เอกสารฟรีเป็นจำนวนทั้งสิ้น 200 บาท");
        studentname.setText("ขอบคุณสำหรับการประเมินอาจารย์ผู้สอน");
        complete.setTextSize(15);
        studentname.setTextSize(15);
        studentid.setText("");

        Button acceptbtn = (Button)welcomedialog.findViewById(R.id.accept);
        acceptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                welcomedialog.dismiss();
            }
        });


        welcomedialog.show();

    }

    public void openManual(View view) {
        startActivity(new Intent(TeacherListActivity.this, ManualActivity.class));
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
