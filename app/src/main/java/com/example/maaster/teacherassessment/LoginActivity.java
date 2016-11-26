package com.example.maaster.teacherassessment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.maaster.teacherassessment.Model.Course;
import com.example.maaster.teacherassessment.Model.MongoDBConnection;
import com.example.maaster.teacherassessment.Model.Student;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.ArrayList;

public class LoginActivity extends Activity {

    private Student student;
    private ArrayList<Course> courses;
    private MongoDBConnection mongoDBConnection;
    private final String TAG = "click";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login2);
        courses = new ArrayList<>();
    }

    public void openListTeacher(View view) {

        createStudent();
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("student", student);
        intent.putParcelableArrayListExtra("course", courses);

        startActivity(intent);
    }

    public void createStudent() {

       /* mongoDBConnection = new MongoDBConnection("mongodb://172.16.160.172:27017", "Student", "test");
        DBCursor cursor = mongoDBConnection.getCursor();
        cursor.next();

        DBObject object = cursor.next();
        String name = (String) object.get("name");

        Log.d("name", "createStudent: "+name);*/

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

    }
}
