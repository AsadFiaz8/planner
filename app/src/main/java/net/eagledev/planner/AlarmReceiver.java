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

        switch (notificationId){

            case -1:
                // When notification is tapped, call MainActivity.
                Intent i1 = new Intent(context, MainActivity.class);
                PendingIntent pi1 = PendingIntent.getActivity(context, 0, i1, 0);

                NotificationManager nm1 =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                // Prepare notification.
                Notification.Builder b1 = new Notification.Builder(context);
                b1.setSmallIcon(R.drawable.finance43)
                        .setContentTitle("Dzień dobiega końca")
                        .setContentText("Zaplanuj kolejny!")
                        .setAutoCancel(true)
                        .setShowWhen(true)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pi1)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setCategory(Notification.CATEGORY_MESSAGE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelId = "REMINDERS";
                    NotificationChannel channel = new NotificationChannel(channelId,
                            context.getResources().getString(R.string.reminders),
                            NotificationManager.IMPORTANCE_HIGH);
                    nm1.createNotificationChannel(channel);
                    b1.setChannelId(channelId);
                }
                nm1.notify(notificationId, b1.build());
                break;

                default:
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
                    break;
        }



    }
}
