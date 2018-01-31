package com.example.ktbffh.wardrobe.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.example.ktbffh.wardrobe.model.Combination;
import com.example.ktbffh.wardrobe.model.Pants;
import com.example.ktbffh.wardrobe.model.Shirt;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by ktbffh on 26/01/18.
 */

public class Utility {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
   //Genrate Random Combination
    public static Combination getRandomCombination(List<Shirt> shirtList, List<Pants> pantsList) {
        Combination combination = new Combination();
        Random randomGenerator = new Random();
        if(shirtList.size()>0) {
            int indexShirt = randomGenerator.nextInt(shirtList.size());
            Shirt shirt = shirtList.get(indexShirt);
            combination.setShirt(shirt);
            combination.setShirtIndex(indexShirt);
        }
        if(pantsList.size()>0) {
            int indexPant = randomGenerator.nextInt(pantsList.size());
            Pants pants = pantsList.get(indexPant);
            combination.setPants(pants);
            combination.setPantIndex(indexPant);
        }
        return combination;
    }

    public static boolean isScheduled(String jobTAG) {
        boolean jobScheduled = false;
        if (jobTAG != null) {
            Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(jobTAG);
            if (jobRequests != null) {
                for (JobRequest reqJob : jobRequests) {
                    jobScheduled = true;
                }
            }
        }
        return jobScheduled;
    }
}