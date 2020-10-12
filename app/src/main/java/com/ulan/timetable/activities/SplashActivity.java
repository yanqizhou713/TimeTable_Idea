package com.ulan.timetable.activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.ulan.timetable.R;


public class SplashActivity extends AppCompatActivity {

    int SPLASH_TIME = 3000; //This is 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //Code to start timer and take action after the timer ends
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do any action here. Now we are moving to next page
                Intent mySuperIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(mySuperIntent);

                //This 'finish()' is for exiting the app when back button pressed from Home page which is LoginActivity
                finish();

            }
        }, SPLASH_TIME);
    }
}