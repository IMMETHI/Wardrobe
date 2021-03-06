package com.example.ktbffh.wardrobe.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.ktbffh.wardrobe.R;
import com.example.ktbffh.wardrobe.activities.NotificationActivity;

/**
 * Created by ktbffh on 28/01/18.
 */

public class NotifactionService extends JobIntentService{

        private NotificationManager notificationManager;
        private PendingIntent pendingIntent;
        private static int NOTIFICATION_ID = 1;
        Notification notification;

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Context context = this.getApplicationContext();
        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, NotificationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("test", "test");
        mIntent.putExtras(bundle);
        pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Resources res = this.getResources();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        notification = new NotificationCompat.Builder(this)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.fav_icon_selected)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.fav_icon_selected))
                .setTicker("ticker value")
                .setAutoCancel(true)
                .setPriority(8)
                .setSound(soundUri)
                .setContentTitle("Your Today Dress")
                .setContentText("Your Today Dress").build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
        notification.defaults |= Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
        notification.ledARGB = 0xFFFFA500;
        notification.ledOnMS = 800;
        notification.ledOffMS = 1000;
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
