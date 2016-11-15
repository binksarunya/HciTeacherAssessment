package com.example.maaster.teacherassessment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login2);
    }

    public void openListTeacher(View view) {

        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
