package com.example.ktbffh.wardrobe.Job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * Created by ktbffh on 29/01/18.
 */

public class DemoJobCreator implements JobCreator {

    @Override
    @Nullable
    public Job create(@NonNull String tag) {
        switch (tag) {
            case NotificationJob.TAG:
                return new NotificationJob();
            default:
                return null;
        }
    }
}
