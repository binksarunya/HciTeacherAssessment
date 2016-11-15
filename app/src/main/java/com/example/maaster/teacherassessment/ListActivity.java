package com.example.maaster.teacherassessment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    public void openAssessment(View view) {
        Intent intent = new Intent(this, AssessmentActivity.class);
        startActivity(intent);
    }
}
