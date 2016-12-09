package com.example.maaster.teacherassessment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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

        if(isNetworkAvailable(this)) {



        if(check) {
            createStudent();
        }


        if(student == null){


            EditText idEditText = (EditText) findViewById(R.id.student_id_text);
            EditText passEditText = (EditText) findViewById(R.id.passwaord_text);

            Log.d(TAG, "openListTeacher: 5555555555"+idEditText.getText()+"555555555");


            if(idEditText.getText().length()==0 && passEditText.getText().length()==0) {
                TextView id = (TextView) findViewById(R.id.wrong_id_text);
                id.setText("*กรุณากรอกรหัสนักศึกษา");
                id.setVisibility(View.VISIBLE);

                TextView pass = (TextView) findViewById(R.id.wrong_pass_text);
                pass.setText("*กรุณากรอกรหัสผ่าน");
                pass.setVisibility(View.VISIBLE);

                Log.d("pun", "openListTeacher: ");

                return;
            }

            if(idEditText.getText().length()==0) {
                TextView id = (TextView) findViewById(R.id.wrong_id_text);
                id.setText("*กรุณากรอกรหัสนักศึกษา");
                id.setVisibility(View.VISIBLE);
                Log.d("pun", "openListTeacher: ");

                return;

            }
            if(passEditText.getText().length()==0) {
                TextView pass = (TextView) findViewById(R.id.wrong_pass_text);
                pass.setText("*กรุณากรอกรหัสผ่าน");
                pass.setVisibility(View.VISIBLE);
                Log.d("pun", "openListTeacher: ");

                return;
            }


            if(!checkId) {
                TextView id = (TextView) findViewById(R.id.wrong_id_text);
                id.setText("*รหัสนักศึกษาไม่ถูกต้อง");
                id.setVisibility(View.VISIBLE);

                return;

            }

            if(!checkPass) {

                TextView pass = (TextView) findViewById(R.id.wrong_pass_text);
                pass.setVisibility(View.VISIBLE);
                pass.setText("*รหัสผ่านไม่ถูกต้อง");

                return;
            }

        }

        Intent intent = new Intent(this, TeacherListActivity.class);
        intent.putExtra("student", student);
        intent.putParcelableArrayListExtra("course", courses);
        intent.putExtra("checkfirst",true);
        intent.putExtra("check", check);

        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("กำลังเข้าสู่ระบบ");
        pd.show();
        startActivity(intent);
        } else {
            final Dialog welcomedialog= new Dialog(this);
            welcomedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            welcomedialog.setContentView(R.layout.dialog_warn);

            TextView title = (TextView) welcomedialog.findViewById(R.id.title_warn);
            TextView sub = (TextView) welcomedialog.findViewById(R.id.subtitle_warn);
            title.setText("ไม่มีการเชือมต่อเครือยข่าย");
            sub.setText("กรุณาเชื่อมต่อกับเครือข่าย");

            Button acceptbtn = (Button)welcomedialog.findViewById(R.id.accept_warn);
            acceptbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    welcomedialog.dismiss();
                }
            });

            welcomedialog.show();
            return;

        }

    }

    private boolean checkId = false;
    private boolean checkPass = false;

    public void createStudent() {

        TextView passTextView = (TextView) findViewById(R.id.wrong_pass_text);
        passTextView.setVisibility(View.INVISIBLE);

        TextView idTextView = (TextView) findViewById(R.id.wrong_id_text);
        idTextView.setVisibility(View.INVISIBLE);

        checkId = false;
        checkPass = false;

        MongoDBConnection mongoDBConnection  = new MongoDBConnection(Constance.IP_ADDRESS, "Student", "Asessment");
        DBCursor cursor = mongoDBConnection.getCursor();


        TextView studetIdTextView = (TextView) findViewById(R.id.student_id_text);
        TextView passwordTextView = (TextView) findViewById(R.id.passwaord_text);

        while (cursor.hasNext()) {

            DBObject object = cursor.next();

            String id = (String) object.get("id");
            String pass = (String) object.get("password");



            if(studetIdTextView.getText().toString().equalsIgnoreCase(id)) {

                checkId = true;

                if (passwordTextView.getText().toString().equalsIgnoreCase(pass)) {

                    checkPass =true;
                    BasicDBList coursesDb = (BasicDBList) object.get("course");


                    for (int i = 0; i < coursesDb.size(); i++) {
                        DBObject courseObject = (DBObject) coursesDb.get(i);
                        Course course = new Course((String) courseObject.get("name"), (String) courseObject.get("section"));

                        Log.d("course", "getView: " + course.getName());

                        courses.add(course);

                    }

                    student = new Student((String) object.get("name"), id, courses, pass);
                    break;
                }

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
