package com.example.maaster.teacherassessment;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class StartScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start_screen);



        new CountDownTimer(1900, 1500) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                // Here do what you like...
                Intent intent = new Intent(StartScreenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }.start();
    }
}
