package com.example.mahe.eecevents3106.Classes;

/*
 * Created by Mahe on 01-01-2018.
 */

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.mahe.eecevents3106.Department.AutomobileActivity;
import com.example.mahe.eecevents3106.Department.CSEActivity;
import com.example.mahe.eecevents3106.Department.CivilActivity;
import com.example.mahe.eecevents3106.Department.ECEActivity;
import com.example.mahe.eecevents3106.Department.EEEActivity;
import com.example.mahe.eecevents3106.Department.EIEActivity;
import com.example.mahe.eecevents3106.Department.ITActivity;
import com.example.mahe.eecevents3106.Department.MechanicalActivity;
import com.example.mahe.eecevents3106.HomeActivity;
import com.example.mahe.eecevents3106.NavActivities.SportsActivity;
import com.example.mahe.eecevents3106.NavActivities.VulcanEventsActivity;
import com.example.mahe.eecevents3106.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

@SuppressLint("Registered")
public class MyFirebaseMessagingService extends FirebaseMessagingService{


    String desc;
    String title;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //Log.v("Desc:", desc);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        RemoteMessage.Notification notification = remoteMessage.getNotification();

        if(notification != null)
        {

            title = notification.getTitle();
            desc =  notification.getBody();

            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "100")
                    .setContentTitle(title)
                    .setContentText(desc)
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent)
                    .setContentInfo(title)
                    .setLargeIcon(icon)
                    .setSmallIcon(R.mipmap.easwarilog);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if(notificationManager != null)
            {
                notificationManager.notify(0, notificationBuilder.build());
            }

        }

    }
}
