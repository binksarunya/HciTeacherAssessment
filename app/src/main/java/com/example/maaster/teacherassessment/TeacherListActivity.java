package com.example.maaster.teacherassessment;

import android.app.Dialog;
import android.app.ProgressDialog;
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
    private boolean check;

    final String[] answerLsit = {"ส่วนที่ 1 ข้อคำถามกลางของมหาวิทยาลัย", "1.สอนอย่างเป็นระบบ", "2.สอนให้คิดวิเคราะห์ วิจารณ์", "3.วิธีสอนให้น่าสนใจเเละน่าติดตาม", "4.จัดให้แสดงความคิดเห็น", "5.สามารถประเมินความเข้าใจ",
            "6.ทำให้เห็นความสัมพันธ์กับวิชาอื่นที่เกี่ยวข้อง", "7.ใช้สื่อและอุปกรณ์ช่วยสอนได้ดี","8.แนะนำแหล่งค้นคว้าข้อมูลเพิ่มเติมให้","ส่วนที่ 2 ข้อคำถามของคณะ/หน่วยงาน", "1.ผู้สอนแจ้งวัตถุประสงค์และเนื้อหาตามเค้าโครงการสอนอย่างชัดเจน","2.ผู้สอนแจ้งเกณฑ์และวิธีประเมินผล ล่วงหน้าชัดเจน",
            "3.ผู้สอนเข้าสอนและเลิกสอนตรงเวลา","4.ผู้สอนมาสอนสม่ำเสมอ","5.ผู้สอนสอนเนื้อหาครบถ้วนและสอดคล้องตามเค้าโครงการสอน","6.ผู้สอนมีการเตรียมการสอนมาอย่างดี","7.ผู้สอนชี้แนะจุดมุ่งหมายหรือข้อสรุปที่เป็นเนื้อหาสาระสำคัญ",
            "8.ผู้สอนแทรกเนื้อหาเกี่ยวกับคุณธรรมจริยธรรมในการเรียนการสอน","9.อาจารย์ให้คำปรึกษาและช่วยเหลือนักศึกษาในห้องฝึกปฏิบัติ","10.อาจารย์ตรวจงาน และให้ข้อคิดเห็นที่เป็นประโยชน์","11.อาจารย์ให้เวลานักศึกษาตลอดการปฏิบัติงาน","ส่วนที่ 3 สภาพแวดล้อมและสิ่งสนับสนุนการเรียนรู้","1.อุปกรณ์ช่วยสอนในห้องเรียนมีคุณภาพพร้อมใช้งาน",
            "2.สภาพห้องเรียนหรือห้องปฏิบัติการมีคุณภาพพร้อมใช้งาน","3.จำนวนอุปกรณ์ในการเรียนการสอนเพียงพอและเหมาะสมต่อจำนวน","4.เจ้าหน้าที่อำนวยความสะดวกในการให้บริการ"};

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

        try {
            position = getIntent().getExtras().getInt("position");

            questions = new ArrayList<>();
            questions = getIntent().getExtras().getParcelableArrayList("question");
            check = getIntent().getExtras().getBoolean("check");
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
        Log.d("check", "onCreate: "+check);

        if(check) {
            getTeacherFromMongoDB();
        } else {
            getTeacherFromDB();
        }
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




    @Override
    protected void onPause() {
        super.onPause();
        if (!Constance.isNetworkAvailable(this))
            showSnack();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        if (!Constance.isNetworkAvailable(this))
            showSnack();

    }

    public void getTeacherFromDB() {
        String url[] = {"http://www.cs.tu.ac.th/uploads/articles_icon/1446541817.jpg",
                        "http://www.cs.tu.ac.th/uploads/articles_icon/1446542136.jpg",
                        "http://www.cs.tu.ac.th/uploads/articles_icon/1446601639.jpg",
                        "http://www.cs.tu.ac.th/uploads/articles_icon/1467772704.jpg"};

        for (int j = 0; j <4 ; j++) {
            Teacher teacher = new Teacher(name[j]);
            teacher.setImage(url[j]);
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
                        nameString.add(teacherArrayList.get(i).getName());
                        courseString.add(student.getCourses().get(k).getName());
                        sectionString.add(student.getCourses().get(k).getSection());

                    }
                }
            }
        }

        courseName = new String[courseString.size()];
        section = new String[sectionString.size()];
        name = new String[nameString.size()];
        Log.d(TAG, "getTeacherFromMongoDB: "+ student.getCourses().size());

        for (int i = 0; i < courseString.size() ; i++) {
            courseName[i] = courseString.get(i);
            section[i] = sectionString.get(i);
            name[i] = nameString.get(i);
        }


    }


    public void getData(){

        CustomListActivity adapter = new CustomListActivity(TeacherListActivity.this,teachers,name,courseName,section,student,TeacherResult,courses,check);
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

    private void showSnack() {
        String message="";
        int color = 0;

        message = "ไม่มีการเชื่อมต่อเครือข่ายกับข้อมูลอิเตอร์เน็ต";
        color = Color.WHITE;


        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.expand_image), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        sbView.setAlpha((float) 0.8);
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    public void onClickLogout(View view){

        if (!Constance.isNetworkAvailable(this)){
            showSnack();
            return;
        }


        if(checkassessmentcomplete==false){
            showNoComplete();
        } else {

            checkLogout();
        }
    }

    public void checkLogout() {

        if (!Constance.isNetworkAvailable(this)) {
            showSnack();
            return;
        }
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
                ProgressDialog pd = new ProgressDialog(context);
                pd.setMessage("กำลังออกจากระบบ");
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

        complete.setText("ท่านยังประเมินอาจารย์ไม่ครบ");
        studentname.setText("ท่านต้องการออกจากระบบหรือไม่");
        complete.setTextSize(18);
        studentname.setTextSize(15);
        Button acceptbtn = (Button)welcomedialog.findViewById(R.id.accept_logout);
        acceptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                welcomedialog.dismiss();
                Intent intent = new Intent(TeacherListActivity.this, LoginActivity.class);
                ProgressDialog pd = new ProgressDialog(context);
                pd.setMessage("กำลังออกจากระบบ");
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
