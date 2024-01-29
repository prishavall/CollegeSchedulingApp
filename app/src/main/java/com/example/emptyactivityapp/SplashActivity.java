package com.example.emptyactivityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent; import android.os.Bundle; import android.os.Handler;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//<<<<<<< HEAD
//=======
        // Check if action bar is not null before hiding
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
//>>>>>>> fc1449a (Fixed nullpointer exceptions)

        // Check if action bar is not null before hiding
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        final Intent i = new Intent(SplashActivity.this, MainActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                finish();
            }
        }, 1000);
    }
}