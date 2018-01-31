package com.example.ktbffh.wardrobe;

import android.app.Application;

import com.evernote.android.job.JobManager;
import com.example.ktbffh.wardrobe.Job.DemoJobCreator;
import com.karumi.dexter.Dexter;

import timber.log.Timber;


public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Dexter.initialize(this);
        JobManager.create(this).addJobCreator(new DemoJobCreator());
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Timber.i("-- init --");


    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
