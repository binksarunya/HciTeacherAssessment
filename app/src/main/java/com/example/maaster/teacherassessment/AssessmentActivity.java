package com.example.maaster.teacherassessment;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
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
    final String[] answer = {"1.สอนอย่างเป็นระบบ", "2.สอนให้คิดวิเคราะห์ วิจารณ์", "3.วิธีสอนให้น่าสนใจเเละน่าติดตาม", "4.จัดให้แสดงความคิดเห็น", "5.สามารถประเมินความเข้าใจ"};
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
        assessmentCheck();
        ImageView imageView = (ImageView) findViewById(R.id.image_teacher);
        TextView textView = (TextView) findViewById(R.id.name_teacher);
        imageView.setImageResource(teacher.getImageId());
        textView.setText(teacher.getName());
        backfirst=0;
        context=AssessmentActivity.this;
        contxt=AssessmentActivity.this;
        TeacherResult =new HashMap<String,ArrayList<Question>>();
        TeacherResult=(HashMap<String,ArrayList<Question>>) getIntent().getSerializableExtra("teacherresult");
        Log.d(TAG, "input from activity: "+TeacherResult.isEmpty());

        final ImageView imageteacher = (ImageView) findViewById(R.id.image_teacher);
        final ImageView zoombtn = (ImageView) findViewById(R.id.zoom_ass);
        zoombtn.setImageResource(R.drawable.zoom_icon);
        zoombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isStoragePermissionGranted()) {
                    Bitmap bitmap = ((BitmapDrawable) imageteacher.getDrawable()).getBitmap();
                    zoomImageFromThumb(zoombtn, getImageUri(context, bitmap));
                }

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
        ListQuestionActivity adapter = new ListQuestionActivity(this, answer,  questions);
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
        for (int i = 0; i <5 ; i++) {
            questions.add(new Question((i+1)+"", answer[i]));
        }
    }



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
                        new CountDownTimer(500, 700) {
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

                                        new CountDownTimer(500, 700) {
                                            @Override
                                            public void onTick(long l) {}
                                            @Override
                                            public void onFinish() {

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
        dialog.setTitle("สรุปผลการทำ");
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ListView lv = (ListView ) dialog.findViewById(R.id.lv_confirm);
        EditAssesListActivity adapter = new EditAssesListActivity(this,answer,questions);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
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
                course.setQuestions(questions);
                TeacherResult.put(String.valueOf(position),questions);
                Log.d(TAG, "onClickbeforesend: "+TeacherResult.get(String.valueOf(position)).get(0).getAnswer());
                intent.putExtra("kuy",TeacherResult);

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
        ListView lv = (ListView ) dialog.findViewById(R.id.lv);
        EditAssesListActivity adapter = new EditAssesListActivity(this,answer,questions);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                editAsessAnswer(position);

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


    public void editAsessAnswer(final int postion) {

        backIcon = (Button) findViewById(R.id.back_icon);
        backIcon.setVisibility(View.INVISIBLE);
        LinearLayout layout = (LinearLayout) findViewById(R.id.complete_layout);
        layout.setVisibility(View.INVISIBLE);
        Button button = (Button) findViewById(R.id.finish);
        button.setVisibility(View.VISIBLE);
        TextView textView = (TextView) findViewById(R.id.article_text);
        textView.setText(questions.get(postion).getDetail());
        TextView textView1 = (TextView) findViewById(R.id.article_num);
        textView1.setText(questions.get(postion).getNo()+"/"+questions.size());
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        ((RadioButton)radioGroup.getChildAt(questions.get(postion).getAnswer())).setChecked(true);
        for (int i = 0; i < radioGroup.getChildCount() ; i++) {
            final int j = i;
            radioGroup.getChildAt(j).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    questions.get(postion).setAnswer(j);
                }
            });
        }

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
        TextView complete = (TextView)welcomedialog.findViewById(R.id.tltle) ;
        TextView studentname = (TextView)welcomedialog.findViewById(R.id.detail);

        complete.setText("ท่านยังประเมินอาจารย์ไม่ครบ");
        studentname.setText("กรุณาประเมินอาจารย์ให้ครบก่อนกลับสู่หน้าหลัก");
        complete.setTextSize(18);
        studentname.setTextSize(15);

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


    protected   void zoomImageFromThumb(final View thumbView, Uri uri) {
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

                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                       
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
