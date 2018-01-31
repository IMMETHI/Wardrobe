package com.example.ktbffh.wardrobe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.example.ktbffh.wardrobe.BaseActivity;
import com.example.ktbffh.wardrobe.Job.NotificationJob;


public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotificationJob.scheduleExactJob();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(activity, WardrobeActivity.class));
                finish();
            }
        }, 3000);

    }
}
