package com.example.gears.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.example.gears.R;
import com.example.gears.Utilities.UserPreferanceManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final UserPreferanceManager userPreferanceManager = new UserPreferanceManager(getApplicationContext());
        new CountDownTimer(500,100) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if(userPreferanceManager.isLoggedIn(getApplicationContext())){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else{
                    startActivity(new Intent(getApplicationContext(), GetStarted.class));
                }
                finish();
            }
        }.start();

    }
}
