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
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.maaster.teacherassessment.Model.Course;
import com.example.maaster.teacherassessment.Model.Student;
import com.example.maaster.teacherassessment.Model.Teacher;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

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
    private final ArrayList<Course> courses;




    public CustomListActivity(Activity context,
                              ArrayList<Teacher> teachers, String[] name, String[] course, String[] section, Student student, ArrayList<Course> courses) {

        super(context,R.layout.list_single,name);
        this.contxt=context;
        this.context = context;
        this.teachers = teachers;
        this.course = course;
        this.section = section;
        this.name = name;
        this.student = student;
        this.courses = courses;


    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);

        RelativeLayout teacherProfile = (RelativeLayout) rowView.findViewById(R.id.teacherprofile);
        RelativeLayout imageprofile = (RelativeLayout) rowView.findViewById(R.id.imageprofile);

        RelativeLayout completeStatus = (RelativeLayout) rowView.findViewById(R.id.completestatus);


            if(courses.get(position).getComplete() == 1) {
                completeStatus.setVisibility(View.VISIBLE);
                Log.d(TAG, "getView: "+ 5);

            }


        teacherProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(courses.get(position).getComplete() == 1) {
                    return;

                }
                Intent intent = new Intent(context, AssessmentActivity.class);//put extra
                intent.putExtra("teacher", teachers.get(position));
                intent.putExtra("student", student);
                intent.putParcelableArrayListExtra("course", courses);
                intent.putExtra("position", position);

                context.startActivity(intent);
            }
        });
        imageprofile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

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

                if(isStoragePermissionGranted()) {
                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    zoomImageFromThumb(zoombtn, getImageUri(context, bitmap));
                    ImageView imageView1 = (ImageView) context.findViewById(R.id.expand_image);
                }
            }
        });
        txtTeacherName.setText(teachers.get(position).getName());
        Log.d(TAG, teachers.get(position).getName());
        txtCourse.setText("รายวิชา "+course[position]);
        txtSection.setText("Section "+section[position]);
        imageView.setImageResource(teachers.get(position).getImageId());



        return rowView;
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


