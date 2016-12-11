package com.example.maaster.teacherassessment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.maaster.teacherassessment.Model.CheckInternet;
import com.example.maaster.teacherassessment.Model.ConnectivityReceiver;
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

import static android.graphics.Color.GRAY;

public class LoginActivity extends Activity  {

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

        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.connection);

        final EditText idText = (EditText) findViewById(R.id.student_id_text);
        idText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(idText.length()>10) {
                    TextView id = (TextView) findViewById(R.id.wrong_id_text);
                    id.setText("*รหัสนักศึกษาไม่ถูกต้อง");
                    id.setVisibility(View.VISIBLE);
                } else {
                    TextView id = (TextView) findViewById(R.id.wrong_id_text);
                    id.setVisibility(View.INVISIBLE);
                }

            }
        });

        final EditText passText = (EditText) findViewById(R.id.passwaord_text);
        passText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                TextView pass = (TextView) findViewById(R.id.wrong_pass_text);
                pass.setVisibility(View.INVISIBLE);

                if(idText.length()<10) {
                    TextView id = (TextView) findViewById(R.id.wrong_id_text);
                    id.setText("*รหัสนักศึกษาไม่ถูกต้อง");
                    id.setVisibility(View.VISIBLE);
                } else if (idText.length()==0) {
                    TextView id = (TextView) findViewById(R.id.wrong_id_text);
                    id.setVisibility(View.INVISIBLE);


                }

            }
        });

    }


    private void showSnack() {
        String message="";
        int color = 0;

            message = "ไม่มีการเชื่อมต่อเครือข่ายกับข้อมูลอิเตอร์เน็ต";
            color = Color.WHITE;


        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.connection), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        sbView.setAlpha((float) 0.8);


        snackbar.show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (!isNetworkAvailable(this))
            showSnack();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        if (!isNetworkAvailable(this))
        showSnack();

    }




    public void openListTeacher(View view) {

        if(isNetworkAvailable(this)) {

            if(check) {

                createStudent();
            }

            if(student == null){

                EditText idEditText = (EditText) findViewById(R.id.student_id_text);
                EditText passEditText = (EditText) findViewById(R.id.passwaord_text);

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

               final ProgressDialog pd = new ProgressDialog(this);
                pd.setMessage("กำลังเข้าสู่ระบบ");
                pd.show();
                new CountDownTimer(1900, 1850) {
                    @Override
                    public void onTick(long l) {}
                    @Override
                    public void onFinish() {

                        pd.dismiss();
                        validateInput();
                    }
                }.start();

                return;

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

            if (!isNetworkAvailable(this))
                showSnack();
            return;

        }

    }

    public void validateInput() {

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
            EditText passEditText = (EditText) findViewById(R.id.passwaord_text);
            passEditText.setText("");
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
