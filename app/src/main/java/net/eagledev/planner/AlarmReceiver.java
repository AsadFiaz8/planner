package net.eagledev.planner;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        // Get id & message from intent.
        int notificationId = intent.getIntExtra("ID", 0);
        String message = intent.getStringExtra("TEXT");
        String tittle = intent.getStringExtra("TITTLE");

        // When notification is tapped, call MainActivity.
        Intent mainIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Prepare notification.
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.finance43)
                .setContentTitle(tittle)
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setShowWhen(true)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_MESSAGE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "REMINDERS";
            NotificationChannel channel = new NotificationChannel(channelId,
                    context.getResources().getString(R.string.reminders),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }
        notificationManager.notify(notificationId, builder.build());


    }
}
