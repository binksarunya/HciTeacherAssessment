package com.example.maaster.teacherassessment;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.maaster.teacherassessment.Model.Constance;
import com.example.maaster.teacherassessment.Model.Course;
import com.example.maaster.teacherassessment.Model.Question;
import com.example.maaster.teacherassessment.Model.Student;
import com.example.maaster.teacherassessment.Model.Teacher;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

public class CustomListActivity extends ArrayAdapter<String>{

    private final Activity context;
    private final Context contxt;
    private final ArrayList<Teacher> teachers;
    private final String[] course;
    private final String[] section;
    private final String[] name;
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;
    private final Student student;
    private ArrayList<Course> courses;
    private  Course courseSingel;
    private int position;
    private HashMap<String,ArrayList<Question>> teacherresult;
    private ImageView zoomout;
    private boolean check;




    public CustomListActivity(Activity context,
                              ArrayList<Teacher> teachers, String[] name, String[] course, String[] section, Student student, HashMap<String,ArrayList<Question>> teacherresult,ArrayList<Course> courses, boolean check) {

        super(context,R.layout.list_single,name);
        this.contxt=context;
        this.context = context;
        this.teachers = teachers;
        this.course = course;
        this.section = section;
        this.name = name;
        this.student = student;
        this.teacherresult=teacherresult;
        this.courses = courses;
        zoomout = (ImageView) context.findViewById(R.id.zoomout2);
        zoomout.setVisibility(View.GONE);
        this.check = check;



    }
    private void showSnack() {
        String message="";
        int color = 0;

        message = "ไม่มีการเชื่อมต่อเครือข่ายกับข้อมูลอิเตอร์เน็ต";
        color = Color.WHITE;


        Snackbar snackbar = Snackbar
                .make(context.findViewById(R.id.expand_image), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        sbView.setAlpha((float) 0.8);
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);

        final RelativeLayout teacherProfile = (RelativeLayout) rowView.findViewById(R.id.teacherprofile);
        RelativeLayout completeStatus = (RelativeLayout) rowView.findViewById(R.id.completestatus);
        this.position = position;

            if(courses.get(position).getComplete() == 1) {
                completeStatus.setVisibility(View.VISIBLE);

            }
        teacherProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(courses.get(position).getComplete() == 1) {

                   showResultAssess(teacherresult,position);
                    return;
                }
                else{


                    if (!Constance.isNetworkAvailable(context)) {
                        showSnack();
                        return;
                    }

                    Intent intent = new Intent(context, AssessmentActivity.class);//put extra
                    intent.putExtra("teacher", teachers.get(position));
                    intent.putExtra("student", student);
                    intent.putParcelableArrayListExtra("course", courses);
                    intent.putExtra("position", position);
                    intent.putExtra("teacherresult",teacherresult);
                    intent.putExtra("check", check);
                    ProgressDialog pd = new ProgressDialog(context);
                    pd.setMessage("กำลังเข้าสู่การประเมิน");
                    pd.show();
                    context.startActivity(intent);
                }

            }
        });

        final ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
        TextView txtTeacherName = (TextView) rowView.findViewById(R.id.name);
        TextView txtCourse = (TextView) rowView.findViewById(R.id.course);
        TextView txtSection = (TextView) rowView.findViewById(R.id.section);
        final ImageView zoombtn = (ImageView) rowView.findViewById(R.id.zoom);
        zoombtn.setImageResource(R.drawable.zoom_icon);
        zoombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    zoomImageFromThumb(zoombtn, teachers.get(position).getImage());
                    zoomout.setVisibility(View.VISIBLE);

            }
        });
        txtTeacherName.setText(teachers.get(position).getName());

        txtCourse.setText("รายวิชา "+course[position]);
        txtSection.setText("Section "+section[position]);


        Picasso.with(context).load(teachers.get(position).getImage()).into(imageView);



        return rowView;
    }

    final String[] answer = {"1.สอนอย่างเป็นระบบ", "2.สอนให้คิดวิเคราะห์ วิจารณ์", "3.วิธีสอนให้น่าสนใจเเละน่าติดตาม", "4.จัดให้แสดงความคิดเห็น", "5.สามารถประเมินความเข้าใจ",
            "6.ทำให้เห็นความสัมพันธ์กับวิชาอื่นที่เกี่ยวข้อง", "7.ใช้สื่อและอุปกรณ์ช่วยสอนได้ดี","8.แนะนำแหล่งค้นคว้าข้อมูลเพิ่มเติมให้","1.ผู้สอนแจ้งวัตถุประสงค์และเนื้อหาตามเค้าโครงการสอนอย่างชัดเจน","2.ผู้สอนแจ้งเกณฑ์และวิธีประเมินผลล่วงหน้าชัดเจน",
            "3.ผู้สอนเข้าสอนและเลิกสอนตรงเวลา","4.ผู้สอนมาสอนสม่ำเสมอ","5.ผู้สอนสอนเนื้อหาครบถ้วนและสอดคล้องตามเค้าโครงการสอน","6.ผู้สอนมีการเตรียมการสอนมาอย่างดี","7.ผู้สอนชี้แนะจุดมุ่งหมายหรือข้อสรุปที่เป็นเนื้อหาสาระสำคัญ",
            "8.ผู้สอนแทรกเนื้อหาเกี่ยวกับคุณธรรมจริยธรรมในการเรียนการสอน","9.อาจารย์ให้คำปรึกษาและช่วยเหลือนักศึกษาในห้องฝึกปฏิบัติ","10.อาจารย์ตรวจงาน และให้ข้อคิดเห็นที่เป็นประโยชน์","11.อาจารย์ให้เวลานักศึกษาตลอดการปฏิบัติงาน","1.อุปกรณ์ช่วยสอนในห้องเรียนมีคุณภาพพร้อมใช้งาน",
            "2.สภาพห้องเรียนหรือห้องปฏิบัติการมีคุณภาพพร้อมใช้งาน","3.จำนวนอุปกรณ์ในการเรียนการสอนเพียงพอและเหมาะสมต่อจำนวน"," 4.เจ้าหน้าที่อำนวยความสะดวกในการให้บริการ"};

    public void showResultAssess(HashMap<String,ArrayList<Question>> teacherresult,int position) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.result_answer);
        dialog.setTitle("สรุปผลการทำ");
        ListView lv = (ListView ) dialog.findViewById(R.id.list_result);


        EditAssesListActivity adapter = new EditAssesListActivity(context,answer,teacherresult.get(String.valueOf(position)));

        lv.setAdapter(adapter);

        Button button = (Button) dialog.findViewById(R.id.comfirm_result);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    protected   void zoomImageFromThumb(final View thumbView, String uri) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.



        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }



        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) context.findViewById(
                R.id.expand_image);

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
        context.findViewById(R.id.imageprofile)
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
                        zoomout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                        zoomout.setVisibility(View.GONE);
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


