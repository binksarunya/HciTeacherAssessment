package com.example.maaster.teacherassessment;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.os.Parcelable;

import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.maaster.teacherassessment.Model.Course;
import com.example.maaster.teacherassessment.Model.Question;
import com.example.maaster.teacherassessment.Model.Student;
import com.example.maaster.teacherassessment.Model.Teacher;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

public class AssessmentActivity extends AppCompatActivity {


    private Teacher teacher;
    private final String TAG = "click";

    /*8 19  */
    final String[] answer = {"1.สอนอย่างเป็นระบบ", "2.สอนให้คิดวิเคราะห์ วิจารณ์", "3.วิธีสอนให้น่าสนใจเเละน่าติดตาม", "4.จัดให้แสดงความคิดเห็น", "5.สามารถประเมินความเข้าใจ",
                                "6.ทำให้เห็นความสัมพันธ์กับวิชาอื่นที่เกี่ยวข้อง", "7.ใช้สื่อและอุปกรณ์ช่วยสอนได้ดี","8.แนะนำแหล่งค้นคว้าข้อมูลเพิ่มเติมให้","1.ผู้สอนแจ้งวัตถุประสงค์และเนื้อหาตามเค้าโครงการสอนอย่างชัดเจน","2.ผู้สอนแจ้งเกณฑ์และวิธีประเมินผล ล่วงหน้าชัดเจน",
    "3.ผู้สอนเข้าสอนและเลิกสอนตรงเวลา","4.ผู้สอนมาสอนสม่ำเสมอ","5.ผู้สอนสอนเนื้อหาครบถ้วนและสอดคล้องตามเค้าโครงการสอน","6.ผู้สอนมีการเตรียมการสอนมาอย่างดี","7.ผู้สอนชี้แนะจุดมุ่งหมายหรือข้อสรุปที่เป็นเนื้อหาสาระสำคัญ",
    "8.ผู้สอนแทรกเนื้อหาเกี่ยวกับคุณธรรมจริยธรรมในการเรียนการสอน","9.อาจารย์ให้คำปรึกษาและช่วยเหลือนักศึกษาในห้องฝึกปฏิบัติ","10.อาจารย์ตรวจงาน และให้ข้อคิดเห็นที่เป็นประโยชน์","11.อาจารย์ให้เวลานักศึกษาตลอดการปฏิบัติงาน","1.อุปกรณ์ช่วยสอนในห้องเรียนมีคุณภาพพร้อมใช้งาน",
    "2.สภาพห้องเรียนหรือห้องปฏิบัติการมีคุณภาพพร้อมใช้งาน","3.จำนวนอุปกรณ์ในการเรียนการสอนเพียงพอและเหมาะสมต่อจำนวน","4.เจ้าหน้าที่อำนวยความสะดวกในการให้บริการ"};
    private ArrayList<Question> questions; 
    int k = 0;
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;
    private RadioGroup radioGroup;
    private Button backIcon;
    private Button previusIcon;
    private Student student;
    private Course course;
    private  ArrayList<Course> courses;
    private int position;
    private boolean checkfirst;
    private int positionNo;
    private int checkpresent;
    private int backfirst;
    private Activity context;
    private Context contxt;
    private static HashMap<String,ArrayList<Question>> TeacherResult;
    private boolean check, dialogCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_assessment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("การประเมิน");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D94130")));
        createQuestion();



        teacher =  getIntent().getExtras().getParcelable("teacher");
        student = getIntent().getExtras().getParcelable("student");
        courses = getIntent().getExtras().getParcelableArrayList("course");
        position = getIntent().getExtras().getInt("position");
        course = courses.get(position);
        check = getIntent().getExtras().getBoolean("check");

        dialogCheck = getIntent().getExtras().getBoolean("dialog");

        if (dialogCheck) {

        }
        showRateDialog();

        LinearLayout layout = (LinearLayout) findViewById(R.id.part_layout);
        layout.setVisibility(View.VISIBLE);

        TextView partTextView = (TextView) findViewById(R.id.part_text);
        partTextView.setText(part[partIndex]);
        TextView partTitleTextView = (TextView) findViewById(R.id.part_title);
        partTitleTextView.setText(parttitle[partIndex]);
        partIndex++;

        new CountDownTimer(1900, 1500) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() {

                LinearLayout layout = (LinearLayout) findViewById(R.id.part_layout);
                layout.setVisibility(View.INVISIBLE);

            }
        }.start();
        assessmentCheck();

        ImageView imageView = (ImageView) findViewById(R.id.image_teacher);
        TextView textView = (TextView) findViewById(R.id.name_teacher);
        TextView section = (TextView) findViewById(R.id.section_teacher);
        TextView courseText = (TextView) findViewById(R.id.course_teacher);

        Picasso.with(context).load(teacher.getImage()).into(imageView);

        textView.setText(teacher.getName());
        courseText.setText("วิชา " + course.getName());
        section.setText("Section " + course.getSection());

        textView.setText(teacher.getName());

        backfirst=0;
        context=AssessmentActivity.this;
        contxt=AssessmentActivity.this;
        TeacherResult =new HashMap<String,ArrayList<Question>>();
        TeacherResult=(HashMap<String,ArrayList<Question>>) getIntent().getSerializableExtra("teacherresult");


        final ImageView imageteacher = (ImageView) findViewById(R.id.image_teacher);
        final ImageView zoombtn = (ImageView) findViewById(R.id.zoom_ass);

        zoombtn.setImageResource(R.drawable.zoom_icon);
        zoombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    zoomImageFromThumb(zoombtn, teacher.getImage());



            }
        });



    }

    String text[] = {"5", "4", "3", "2", "1"};
    String rate[] = {"ดีมาก", "ดี", "ปานกลาง", "พอใช้", "ปรัปรุง"};

    public void showRateDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.question_option_dialog);
        ListView listView = (ListView) dialog.findViewById(R.id.lv_rate);
        DialogRate dialogRate = new DialogRate(this, text , rate);
        listView.setAdapter(dialogRate);
        dialog.show();

        Button button = (Button) dialog.findViewById(R.id.assessment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    public void showAllQuestion(View view) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.list_question);
        dialog.setTitle("รายการคำถาม");
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ListView lv = (ListView ) dialog.findViewById(R.id.ls_view_question);
        ListQuestionActivity adapter = new ListQuestionActivity(this, answerLsit,  questions);
        lv.setAdapter(adapter);

        Button confirmbtn = (Button)dialog.findViewById(R.id.list_confirm_btn) ;
        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void createQuestion() {
        questions = new ArrayList<>();
        for (int i = 0; i <answer.length ; i++) {
            questions.add(new Question((i+1)+"", answer[i]));
        }
    }

    String part[] = {"ข้อคำถามกลางของมหาวิทยาลัย","ข้อคำถามของคณะ/หน่วยงาน","สภาพแวดล้อมและสิ่งสนับสนุนการเรียนรู้"};
    String parttitle[] = {"ส่วนที่ 1","ส่วนที่ 2","ส่วนที่ 3"};

    final String[] answerLsit = {"ส่วนที่ 1 ข้อคำถามกลางของมหาวิทยาลัย", "1.สอนอย่างเป็นระบบ", "2.สอนให้คิดวิเคราะห์ วิจารณ์", "3.วิธีสอนให้น่าสนใจเเละน่าติดตาม", "4.จัดให้แสดงความคิดเห็น", "5.สามารถประเมินความเข้าใจ",
            "6.ทำให้เห็นความสัมพันธ์กับวิชาอื่นที่เกี่ยวข้อง", "7.ใช้สื่อและอุปกรณ์ช่วยสอนได้ดี","8.แนะนำแหล่งค้นคว้าข้อมูลเพิ่มเติมให้","ส่วนที่ 2 ข้อคำถามของคณะ/หน่วยงาน", "1.ผู้สอนแจ้งวัตถุประสงค์และเนื้อหาตามเค้าโครงการสอนอย่างชัดเจน","2.ผู้สอนแจ้งเกณฑ์และวิธีประเมินผล ล่วงหน้าชัดเจน",
            "3.ผู้สอนเข้าสอนและเลิกสอนตรงเวลา","4.ผู้สอนมาสอนสม่ำเสมอ","5.ผู้สอนสอนเนื้อหาครบถ้วนและสอดคล้องตามเค้าโครงการสอน","6.ผู้สอนมีการเตรียมการสอนมาอย่างดี","7.ผู้สอนชี้แนะจุดมุ่งหมายหรือข้อสรุปที่เป็นเนื้อหาสาระสำคัญ",
            "8.ผู้สอนแทรกเนื้อหาเกี่ยวกับคุณธรรมจริยธรรมในการเรียนการสอน","9.อาจารย์ให้คำปรึกษาและช่วยเหลือนักศึกษาในห้องฝึกปฏิบัติ","10.อาจารย์ตรวจงาน และให้ข้อคิดเห็นที่เป็นประโยชน์","11.อาจารย์ให้เวลานักศึกษาตลอดการปฏิบัติงาน","ส่วนที่ 3 สภาพแวดล้อมและสิ่งสนับสนุนการเรียนรู้","1.อุปกรณ์ช่วยสอนในห้องเรียนมีคุณภาพพร้อมใช้งาน",
            "2.สภาพห้องเรียนหรือห้องปฏิบัติการมีคุณภาพพร้อมใช้งาน","3.จำนวนอุปกรณ์ในการเรียนการสอนเพียงพอและเหมาะสมต่อจำนวน","4.เจ้าหน้าที่อำนวยความสะดวกในการให้บริการ"};


    int partIndex = 0;

    public void assessmentCheck() {
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);

        backIcon = (Button) findViewById(R.id.back_icon);
        if(k==0) {
            backIcon = (Button) findViewById(R.id.back_icon);
            backIcon.setVisibility(View.GONE);

        }
        for (int i = 0; i < radioGroup.getChildCount() ; i++) {
            final int j = i;


            radioGroup.getChildAt(j).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        new CountDownTimer(500, 600) {
                            @Override
                            public void onTick(long l) {}

                            @Override
                            public void onFinish() {

                                try {

                                   int answers = j;
                                    if(checkpresent==k){
                                        backfirst=0;
                                    }
                                    if(backfirst==0){
                                        previusIcon = (Button)findViewById(R.id.previous_icon);
                                        previusIcon.setVisibility(View.GONE);
                                    }
                                    if(k==questions.size()-2){
                                        previusIcon = (Button)findViewById(R.id.previous_icon);
                                        previusIcon.setVisibility(View.GONE);
                                    }
                                    questions.get(k).setAnswer(answers);
                                    Log.d(TAG, "onFinish: "+k);
                                        k++;
                                    if(k>=questions.size()) {

                                        new CountDownTimer(500, 600) {
                                            @Override
                                            public void onTick(long l) {}
                                            @Override
                                            public void onFinish() {

                                                ProgressDialog pd = new ProgressDialog(context);
                                                pd.setMessage("กำลังเข้าสู่การยืนยันการประเมิน");
                                                completeAssess ();


                                            }
                                        }.start();

                                    } else {
                                        if(backfirst==0) {
                                            clearBtn();
                                        }
                                        else{
                                            ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
                                            ((RadioButton) radioGroup.getChildAt(1)).setChecked(true);
                                            ((RadioButton) radioGroup.getChildAt(questions.get(k).getAnswer())).setChecked(true);
                                        }


                                        if(k==8 || k==19 ) {

                                            LinearLayout layout = (LinearLayout) findViewById(R.id.part_layout);
                                            layout.setVisibility(View.VISIBLE);

                                            TextView textView = (TextView) findViewById(R.id.part_text);
                                            textView.setText(part[partIndex]);
                                            TextView partTitleTextView = (TextView) findViewById(R.id.part_title);
                                            partTitleTextView.setText(parttitle[partIndex]);
                                            partIndex++;

                                            new CountDownTimer(1900, 1500) {
                                                @Override
                                                public void onTick(long l) {}
                                                @Override
                                                public void onFinish() {

                                                    LinearLayout layout = (LinearLayout) findViewById(R.id.part_layout);
                                                    layout.setVisibility(View.INVISIBLE);

                                                }
                                            }.start();


                                        }



                                        backIcon = (Button) findViewById(R.id.back_icon);
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

    public void previousNo (View view) {
        k++;
        if(k>0){
            backIcon = (Button) findViewById(R.id.back_icon);
            backIcon.setVisibility(View.VISIBLE);
        }
        if(checkpresent<k||k==questions.size()-1){
            previusIcon = (Button)findViewById(R.id.previous_icon);
            previusIcon.setVisibility(View.GONE);
            backfirst=0;
            clearBtn();
        }

        TextView textView = (TextView) findViewById(R.id.article_text);
        textView.setText(questions.get(k).getDetail());
        TextView textView1 = (TextView) findViewById(R.id.article_num);
        textView1.setText(questions.get(k).getNo()+"/"+questions.size());
        if(checkpresent>=k) {
            ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
            ((RadioButton) radioGroup.getChildAt(1)).setChecked(true);
            ((RadioButton) radioGroup.getChildAt(questions.get(k).getAnswer())).setChecked(true);
        }

        assessmentCheck();

    }

    public void backNo (View view){

        k--;
        if(backfirst==0){
            checkpresent=k;
            backfirst++;
        }
        previusIcon = (Button)findViewById(R.id.previous_icon);
        previusIcon.setVisibility(View.VISIBLE);

        if(k==0) {
            backIcon = (Button) findViewById(R.id.back_icon);

            Log.d(TAG, "backNo: *************" +k);

            if(backfirst!=0){
                previusIcon.setVisibility(View.VISIBLE);

            }
        }
        TextView textView = (TextView) findViewById(R.id.article_text);
        textView.setText(questions.get(k).getDetail());
        TextView textView1 = (TextView) findViewById(R.id.article_num);
        textView1.setText(questions.get(k).getNo()+"/"+questions.size());

        ((RadioButton)radioGroup.getChildAt(0)).setChecked(true);
        ((RadioButton)radioGroup.getChildAt(1)).setChecked(true);
        ((RadioButton)radioGroup.getChildAt(questions.get(k).getAnswer())).setChecked(true);

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

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.comfirm_dialog);
        dialog.setTitle("ยืนยันการประเมิน");
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ListView lv = (ListView ) dialog.findViewById(R.id.lv_confirm);
        EditAssesListActivity adapter = new EditAssesListActivity(this,answerLsit,questions);
        lv.setAdapter(adapter);

        Button confirmbtn = (Button)dialog.findViewById(R.id.button2) ;
        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentActivity.this, TeacherListActivity.class);
                intent.putExtra("student", student);
                intent.putExtra("position", position);
                courses.set(position, course);
                intent.putParcelableArrayListExtra("question", questions);
                intent.putParcelableArrayListExtra("course", courses);
                intent.putExtra("coursetmp",course);
                intent.putExtra("teachername",teacher.getName());
                intent.putExtra("checkfirst",false);
                intent.putExtra("dialog", false);
                course.setQuestions(questions);
                TeacherResult.put(String.valueOf(position),questions);
                Log.d(TAG, "onClickbeforesend: "+TeacherResult.get(String.valueOf(position)).get(0).getAnswer());
                intent.putExtra("kuy",TeacherResult);
                intent.putExtra("check", check);
                dialog.dismiss();
                startActivity(intent);
            }
        });
        Button dismissbtn = (Button)dialog.findViewById(R.id.button3);
        dismissbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void editAsess(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_dialog);
        dialog.setTitle("เลือกข้อที่ต้องการแก้ไข");
        ListView lv = (ListView ) dialog.findViewById(R.id.lv);
        EditAssesListActivity adapter = new EditAssesListActivity(this,answerLsit,questions);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                Log.d("position", "onItemClick: " + position);
                if(position==0) {
                    Log.d("punn", "onItemClick: ");
                }
                else if(position>0&&position<9) {
                    position-=1;
                    editAsessAnswer(position);
                }
                else if(position==9) {
                    Log.d("punn", "onItemClick: ");
                }
                else if(position>9 && position<=21) {
                    position-=2;
                    editAsessAnswer(position);
                }
                else if (position==21) {
                    Log.d("punn", "onItemClick: ");
                }
                else if(position>21) {
                    position-=3;
                    editAsessAnswer(position);
                }

            }
        });
        Button button = (Button) dialog.findViewById(R.id.cancel_edit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }


    public void editAsessAnswer(int position) {


        backIcon = (Button) findViewById(R.id.back_icon);
        backIcon.setVisibility(View.INVISIBLE);

        LinearLayout layout = (LinearLayout) findViewById(R.id.complete_layout);
        layout.setVisibility(View.INVISIBLE);

        Button button = (Button) findViewById(R.id.finish);
        button.setVisibility(View.VISIBLE);

        TextView textView = (TextView) findViewById(R.id.article_text);
        textView.setText(questions.get(position).getDetail());

        TextView textView1 = (TextView) findViewById(R.id.article_num);
        textView1.setText(questions.get(position).getNo()+"/"+questions.size());

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        ((RadioButton)radioGroup.getChildAt(0)).setChecked(true);
        ((RadioButton)radioGroup.getChildAt(1)).setChecked(true);
        ((RadioButton)radioGroup.getChildAt(questions.get(position).getAnswer())).setChecked(true);
       /* final int
        for (int i = 0; i < radioGroup.getChildCount() ; i++) {
            final int j = i;
            radioGroup.getChildAt(j).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    questions.get(postion).setAnswer(j);
                }
            });
        }
*/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completeAssess();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            showNoComplete();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showNoComplete() {
        final Dialog welcomedialog= new Dialog(this);
        welcomedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        welcomedialog.setContentView(R.layout.no_complete_dialog);
        TextView complete = (TextView)welcomedialog.findViewById(R.id.title_dialog) ;
        TextView studentname = (TextView)welcomedialog.findViewById(R.id.detail);
        TextView sub = (TextView) welcomedialog.findViewById(R.id.subtitle);

        complete.setText("ท่านยังประเมินอาจารย์ไม่ครบ");
        studentname.setText("ท่านต้องการกลับสู่หน้าหลักหรือไม่");
        sub.setText("*ระบบจะไม่บันทึกข้อมูลการประเมิน");
        complete.setTextSize(18);
        studentname.setTextSize(18);
        sub.setTextSize(15);

        sub.setTextColor(Color.RED);

        Button abtn = (Button)welcomedialog.findViewById(R.id.accept_logout);
        abtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                welcomedialog.dismiss();
            }
        });

        Button cancelbtn = (Button)welcomedialog.findViewById(R.id.cacel_logout);
        cancelbtn.setVisibility(View.GONE);
        welcomedialog.show();

    }


    protected Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        Log.d(TAG, "getImageUri: "+path);
        return Uri.parse(path);
    }//cast bitmap to uri


    protected void zoomImageFromThumb(final View thumbView, String uri) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.



        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }



        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) context.findViewById(
                R.id.expand_image2);

        Picasso.with(contxt).load(uri).into(expandedImageView);



        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        context.findViewById(R.id.imageprofile2)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);
        final ImageView imageView = (ImageView) findViewById(R.id.zoomout);
        imageView.setVisibility(View.VISIBLE);
        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        imageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        imageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;



            }
        });
    }//zoom image




    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }



}
