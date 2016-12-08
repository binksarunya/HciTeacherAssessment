package com.example.maaster.teacherassessment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
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

import com.example.maaster.teacherassessment.Model.Constance;
import com.example.maaster.teacherassessment.Model.Course;
import com.example.maaster.teacherassessment.Model.MongoDBConnection;
import com.example.maaster.teacherassessment.Model.Question;
import com.example.maaster.teacherassessment.Model.Student;
import com.example.maaster.teacherassessment.Model.Teacher;
import com.mongodb.BasicDBList;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class TeacherListActivity extends AppCompatActivity {

    private Student student;
    private ArrayList<Teacher> teachers;
    private ArrayList<Course> courses;
    private ArrayList<Question> questions;
    private Course coursetmp;
    private final String TAG = "click";
    private Context context;
    private ImageView iv, imageView;
    private boolean checkfirst =true;
    private boolean checkassessmentcomplete = false;
    private int count = 0;
    private int position;
    private static HashMap<String,ArrayList<Question>> TeacherResult;


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
        TeacherResult=new HashMap<String, ArrayList<Question>>();
        context = getBaseContext();
        teachers = new ArrayList<>();
        courses = new ArrayList<>();
        student = getIntent().getExtras().getParcelable("student");
        courses = getIntent().getExtras().getParcelableArrayList("course");
        student.setCourses(courses);
        checkfirst = getIntent().getExtras().getBoolean("checkfirst");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getTeacherFromMongoDB();

        try {
            position = getIntent().getExtras().getInt("position");

            questions = new ArrayList<>();
            questions = getIntent().getExtras().getParcelableArrayList("question");

            int position = getIntent().getExtras().getInt("position");
            coursetmp= getIntent().getExtras().getParcelable("coursetmp");
            courses.get(position).setQuestions(questions);
            coursetmp.setQuestions(questions);
            Intent intent = getIntent();
            TeacherResult = (HashMap<String, ArrayList<Question>>) intent.getSerializableExtra("kuy");
            TeacherResult.put(String.valueOf(position),questions);
            Log.d(TAG, "onClickafter: "+TeacherResult.get(String.valueOf(position)).get(0).getAnswer());


        } catch (Exception e) {

            e.printStackTrace();

        }
        if(checkfirst==true) {
            showStudentDialog(student);
        }



        getTeacherFromDB();
        getData();

        for (int i = 0; i < courses.size() ; i++) {

            if(courses.get(i).getComplete()==1){

                checkassessmentcomplete=true;
            }
            else{
                checkassessmentcomplete=false;
                break;
            }
        }

        if(checkassessmentcomplete){
            showComplete(student);

        }


    }

    public void getTeacherFromDB() {
        for (int j = 0; j <4 ; j++) {
            Teacher teacher = new Teacher(name[j]);
            teacher.setImageId(imageId[j]);
            teachers.add(teacher);

        }

    }

    public void getTeacherFromMongoDB() {
        ArrayList<Teacher> teacherArrayList = new ArrayList<>();
        MongoDBConnection mongoDBConnection = new MongoDBConnection(Constance.IP_ADDRESS, "Teacher", "Asessment");
        DBCursor cursor = mongoDBConnection.getCursor();

        ArrayList<Course> courseList = new ArrayList<>();

        while (cursor.hasNext()) {

            DBObject object = cursor.next();

            Teacher teacher = new Teacher((String) object.get("name"));
            teacher.setImage((String) object.get("image"));
            BasicDBList list = (BasicDBList) object.get("course");

            for (int i = 0; i <list.size() ; i++) {
                DBObject dbObject = (DBObject) list.get(i);

                String nameCourse = (String) dbObject.get("name");
                String section = (String) dbObject.get("section");

                Course course = new Course(nameCourse, section);
                teacher.addCourse(course);
            }

            teacherArrayList.add(teacher);
            courseList.clear();
        }

        ArrayList<String> nameString = new ArrayList<>();
        ArrayList<String> sectionString = new ArrayList<>();
        ArrayList<String> courseString = new ArrayList<>();

        teachers = new ArrayList<>();
        for (int i = 0; i < teacherArrayList.size(); i++) {
            for (int j = 0; j < teacherArrayList.get(i).getCourses().size(); j++) {
                for (int k = 0; k < student.getCourses().size(); k++) {
                    if(student.getCourses().get(k).getName().equalsIgnoreCase(teacherArrayList.get(i).getCourses().get(j).getName()) &&
                            student.getCourses().get(k).getSection().equalsIgnoreCase(teacherArrayList.get(i).getCourses().get(j).getSection())) {

                        count++;

                        teachers.add(teacherArrayList.get(i));
                        Log.d("pun", "getTeacherFromMongoDB: " + student.getCourses().get(k).getName());
                        Log.d("pun", "getTeacherFromMongoDB: " + student.getCourses().get(k).getSection());
                        courseString.add(student.getCourses().get(k).getName());
                        sectionString.add(student.getCourses().get(k).getSection());

                    }
                }
            }
        }

        courseName = new String[courseString.size()];
        section = new String[sectionString.size()];
        Log.d(TAG, "getTeacherFromMongoDB: "+ student.getCourses().size());

        for (int i = 0; i < courseString.size() ; i++) {
            courseName[i] = courseString.get(i);
            section[i] = sectionString.get(i);
        }


    }


    public void getData(){

        CustomListActivity adapter = new CustomListActivity(TeacherListActivity.this,teachers,name,courseName,section,student,TeacherResult,courses);
        final ListView list = (ListView)findViewById(R.id.list);
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
