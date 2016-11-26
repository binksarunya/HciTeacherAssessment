package com.example.maaster.teacherassessment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.maaster.teacherassessment.Model.Course;
import com.example.maaster.teacherassessment.Model.EditAssesListActivity;
import com.example.maaster.teacherassessment.Model.Question;
import com.example.maaster.teacherassessment.Model.Student;
import com.example.maaster.teacherassessment.Model.Teacher;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AssessmentActivity extends AppCompatActivity {


    private Teacher teacher;
    private final String TAG = "click";
    final String[] answer = {"1.สอนอย่างเป็นระบบ", "2.สอนให้คิดวิเคราะห์ วิจารณ์", "3.วิธีสอนให้น่าสนใจเเละน่าติดตาม", "4.จัดให้แสดงความคิดเห็น", "5.สามารถประเมินความเข้าใจ"};
    private ArrayList<Question> questions; 
    int k = 0;
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;
    private RadioGroup radioGroup;
    private ImageView backIcon;
    private Student student;
    private Course course;
    private  ArrayList<Course> courses;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_assessment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("การประเมิน");
        

        teacher =  getIntent().getExtras().getParcelable("teacher");
        student = getIntent().getExtras().getParcelable("student");

        courses = getIntent().getExtras().getParcelableArrayList("course");

        position = getIntent().getExtras().getInt("position");
        course = courses.get(position);


        Log.d(TAG, "onCreate: " + course.getName() + " " +course.getComplete());

        createQuestion();
        assessmentCheck();

        ImageView imageView = (ImageView) findViewById(R.id.image_teacher);
        TextView textView = (TextView) findViewById(R.id.name_teacher);


        imageView.setImageResource(teacher.getImageId());
        textView.setText(teacher.getName());


    }
    
    public void createQuestion() {
        
        questions = new ArrayList<>();

        for (int i = 0; i <5 ; i++) {
            questions.add(new Question((i+1)+"", answer[i]));
        }
    }


    public void assessmentCheck() {
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);

        backIcon = (ImageView) findViewById(R.id.back_icon);
        if(k==0) {
            backIcon = (ImageView) findViewById(R.id.back_icon);
            backIcon.setVisibility(View.INVISIBLE);

        }
        for (int i = 0; i < radioGroup.getChildCount() ; i++) {
            final int j = i;


            radioGroup.getChildAt(j).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        new CountDownTimer(500, 700) {
                            @Override
                            public void onTick(long l) {}

                            @Override
                            public void onFinish() {

                                try {

                                    int answers = j;

                                    questions.get(k).setAnswer(answers);


                                    k++;
                                    if(k>=questions.size()) {

                                        new CountDownTimer(500, 700) {
                                            @Override
                                            public void onTick(long l) {}
                                            @Override
                                            public void onFinish() {

                                                completeAssess ();


                                            }
                                        }.start();

                                    } else {
                                        clearBtn();

                                        backIcon = (ImageView) findViewById(R.id.back_icon);
                                        backIcon.setVisibility(View.VISIBLE);
                                        TextView textView = (TextView) findViewById(R.id.article_text);
                                        textView.setText(questions.get(k).getDetail());
                                        TextView textView1 = (TextView) findViewById(R.id.article_num);
                                        textView1.setText(questions.get(k).getNo()+"/"+questions.size());

                                    }

                                } catch (Exception e) {

                                }

                            }
                        }.start();
                    }
            });
        }


    }

    public void completeAssess () {

        LinearLayout layout = (LinearLayout) findViewById(R.id.complete_layout);
        layout.setVisibility(View.VISIBLE);
        ImageView imageView = (ImageView) findViewById(R.id.status_image);
        Resources res = getResources();
        imageView.setImageDrawable(res.getDrawable(R.drawable.complete_status));
        getSupportActionBar().setTitle("ยืนยันการประเมิน");

        course.setComplete(1);

    }

    public void backNo (View view){

        k--;
        if(k==0) {
            backIcon = (ImageView) findViewById(R.id.back_icon);
            backIcon.setVisibility(View.INVISIBLE);

        }
        TextView textView = (TextView) findViewById(R.id.article_text);
        textView.setText(questions.get(k).getDetail());
        TextView textView1 = (TextView) findViewById(R.id.article_num);
        textView1.setText(questions.get(k).getNo()+"/"+questions.size());

        ((RadioButton)radioGroup.getChildAt(0)).setChecked(true);
        ((RadioButton)radioGroup.getChildAt(1)).setChecked(true);
        ((RadioButton)radioGroup.getChildAt(questions.get(k).getAnswer())).setChecked(true);


        Log.d(TAG, "backNo: "+ ((RadioButton)radioGroup.getChildAt(questions.get(k).getAnswer())).isChecked());

        assessmentCheck();
    }

    public void clearBtn() {
        Log.d(TAG, "clear: ");
        RadioButton radioButton = (RadioButton) findViewById(R.id.r1);
        RadioButton radioButton2 = (RadioButton) findViewById(R.id.r2);
        RadioButton radioButton3 = (RadioButton) findViewById(R.id.r3);
        RadioButton radioButton4 = (RadioButton) findViewById(R.id.r4);
        RadioButton radioButton5 = (RadioButton) findViewById(R.id.r5);

        radioButton.setChecked(false);
        radioButton2.setChecked(false);
        radioButton3.setChecked(false);
        radioButton4.setChecked(false);
        radioButton5.setChecked(false);
    }

    public void comfirmAsess(View view) {


        Intent intent = new Intent(AssessmentActivity.this, ListActivity.class);

        intent.putExtra("student", student);
        courses.set(position, course);
        intent.putParcelableArrayListExtra("course", courses);

        startActivity(intent);
    }

    public void editAsess(View view) {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.edit_dialog);
        ListView lv = (ListView ) dialog.findViewById(R.id.lv);
        EditAssesListActivity adapter = new EditAssesListActivity(this,answer);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        dialog.setTitle("คำถาม");
        dialog.show();


    }



}
