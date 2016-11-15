package com.example.maaster.teacherassessment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class AssessmentActivity extends AppCompatActivity {

    private final String TAG = "error";
    final String[] answer = {"1.สอนอย่างเป็นระบบ", "2.สอนให้คิดวิเคราะห์ วิจารณ์", "3.วิธีสอนให้น่าสนใจเเละน่าติดตาม", "4.จัดให้แสดงความคิดเห็น", "5.สามารถประเมินความเข้าใจ"};
    int k = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_assessment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("การประเมิน");
        assessmentCheck();



    }



    public void assessmentCheck() {

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);


        for (int i = 0; i < radioGroup.getChildCount() ; i++) {
            final int j = i;

            radioGroup.getChildAt(j).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    k++;
                    if(k>=answer.length) {
                        LinearLayout layout = (LinearLayout) findViewById(R.id.complete_layout);
                        layout.setVisibility(View.VISIBLE);
                        ImageView imageView = (ImageView) findViewById(R.id.status_image);
                        Resources res = getResources();
                        imageView.setImageDrawable(res.getDrawable(R.drawable.complete_status));
                        getSupportActionBar().setTitle("ยืนยันการประเมิน");
                    } else {

                        TextView textView = (TextView) findViewById(R.id.article_text);
                        textView.setText(answer[k]);


                    }


                }
            });
        }



    }

}
