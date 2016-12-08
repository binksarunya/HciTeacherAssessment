package com.example.maaster.teacherassessment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.maaster.teacherassessment.Model.Constance;
import com.example.maaster.teacherassessment.Model.Course;
import com.example.maaster.teacherassessment.Model.MongoDBConnection;
import com.example.maaster.teacherassessment.Model.Student;
import com.mongodb.BasicDBList;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class LoginActivity extends Activity {

    private Student student;
    private ArrayList<Course> courses;
    private MongoDBConnection mongoDBConnection;
    private final String TAG = "click";
    private boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login2);
        courses = new ArrayList<>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);


    }



    public void openListTeacher(View view) {

        if(check)
        createStudent();

        if(student == null) {

            final Dialog welcomedialog= new Dialog(this);
            welcomedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            welcomedialog.setContentView(R.layout.welcome_dialog);
            TextView studentname = (TextView)welcomedialog.findViewById(R.id.studentnameTextview);
            TextView studentid = (TextView)welcomedialog.findViewById(R.id.studentIdTextview);
            studentname.setText("รหัสผิดพลาด");
            studentid.setText("กรุณาใส่รหัสใหม่");
            Button acceptbtn = (Button)welcomedialog.findViewById(R.id.accept);
            acceptbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    welcomedialog.dismiss();
                }
            });

            welcomedialog.show();
            return;

        }

        Intent intent = new Intent(this, TeacherListActivity.class);
        intent.putExtra("student", student);
        intent.putParcelableArrayListExtra("course", courses);
        intent.putExtra("checkfirst",true);
        intent.putExtra("check", check);
        startActivity(intent);
    }

    public void createStudent() {

        MongoDBConnection mongoDBConnection  = new MongoDBConnection(Constance.IP_ADDRESS, "Student", "Asessment");
        DBCursor cursor = mongoDBConnection.getCursor();


        TextView studetIdTextView = (TextView) findViewById(R.id.student_id_text);
        TextView passwordTextView = (TextView) findViewById(R.id.passwaord_text);

        while (cursor.hasNext()) {

            DBObject object = cursor.next();

            String id = (String) object.get("id");
            String pass = (String) object.get("password");



            if(studetIdTextView.getText().toString().equalsIgnoreCase(id) && passwordTextView.getText().toString().equalsIgnoreCase(pass)){


                BasicDBList coursesDb = (BasicDBList) object.get("course");


                for (int i = 0; i <coursesDb.size() ; i++) {
                    DBObject courseObject = (DBObject) coursesDb.get(i);
                    Course course = new Course((String)courseObject.get("name"), (String)courseObject.get("section"));

                        Log.d("course", "getView: "+ course.getName());

                    courses.add(course);

                }

                student = new Student((String) object.get("name"), id, courses ,pass);
                break;
            }

        }

        mongoDBConnection.closeDB();

    }

    public void loginNoDB(View view) {

        String[] course = {
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


        for (int i = 0; i < 4 ; i++) {
            Course courseStudent= new Course(course[i], section[i]);
            courses.add(courseStudent);
        }

        student = new Student("ปัณวรรธน์ นกเกตุ", "5709680044", courses, "1234");
        check = false;
    }
}
