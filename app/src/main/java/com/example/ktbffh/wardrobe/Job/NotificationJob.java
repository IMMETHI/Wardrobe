package com.example.ktbffh.wardrobe.Job;

import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.example.ktbffh.wardrobe.service.NotifactionService;
import com.example.ktbffh.wardrobe.utils.AppConstant;
import com.example.ktbffh.wardrobe.utils.Utility;

import java.util.Calendar;
import java.util.concurrent.CountDownLatch;

/**
 * Created by ktbffh on 29/01/18.
 */

public class NotificationJob extends Job {

    public static final String TAG = "Notification_JOB";

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        new Thread() {
            @Override
            public void run() {
                Intent service1 = new Intent(getContext(), NotifactionService.class);
                service1.setData((Uri.parse("custom://" + System.currentTimeMillis())));
                getContext().startService(service1);
                scheduleExactJob();
                SystemClock.sleep(3_000L);
                countDownLatch.countDown();
            }
        }.start();

        try {
            countDownLatch.await();
        } catch (InterruptedException ignored) {
        }
        return Result.SUCCESS;
    }

    public static void scheduleExactJob() {
        if (!Utility.isScheduled(NotificationJob.TAG)) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 6);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            long timeFromNow = calendar.getTimeInMillis() - System.currentTimeMillis();
            if (timeFromNow < AppConstant.One_Millisecond)
                timeFromNow = timeFromNow + AppConstant.Millis_IN_DAY;
            int jobId = new JobRequest.Builder(NotificationJob.TAG)
                    .setExact(timeFromNow)
                    .build()
                    .schedule();
        }
    }

}