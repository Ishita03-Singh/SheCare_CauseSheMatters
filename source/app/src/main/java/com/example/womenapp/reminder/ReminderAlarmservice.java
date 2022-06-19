package com.example.womenapp.reminder;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.example.womenapp.DestinationActivity;
import com.example.womenapp.R;


public class ReminderAlarmservice extends IntentService {
    private static final String TAG = ReminderAlarmservice.class.getSimpleName();

    private static final int NOTIFICATION_ID = 42;
    //This is a deep link intent, and needs the task stack
    public static PendingIntent getReminderPendingIntent(Context context, Uri uri) {
        Intent action = new Intent(context, ReminderAlarmservice.class);
        action.setData(uri);
        return PendingIntent.getService(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public ReminderAlarmservice() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Create an explicit intent for an Activity in your app


        Intent resultIntent = new Intent(this, DestinationActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(DestinationActivity.class);

// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DESCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"YOUR_CHANNEL_ID" )
                .setSmallIcon(R.drawable.womenintro)
                .setContentTitle(" Hey There ")
                .setContentText(" Press me ! ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                . setContentIntent(resultPendingIntent)
                .setAutoCancel(true);


// notificationID allows you to update the notification later on.
        mNotificationManager.notify(123, builder.build());
    }
}