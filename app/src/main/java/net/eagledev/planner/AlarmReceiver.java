package net.eagledev.planner;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    Checker checker;
    public static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {



        checker = new Checker();
        // Get id & message from intent.
        int notificationId = intent.getIntExtra("ID", 0);
        String message = intent.getStringExtra("TEXT");
        String tittle = intent.getStringExtra("TITTLE");
        switch (notificationId){


            case -1:
                // When notification is tapped, call MainActivity.
                Intent i1 = new Intent(context, PlanNextDayActivity.class);
                PendingIntent pi1 = PendingIntent.getActivity(context, 0, i1, 0);

                NotificationManager nm1 =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                // Prepare notification.
                Notification.Builder b1 = new Notification.Builder(context);
                b1.setSmallIcon(R.drawable.finance43)
                        .setContentTitle(context.getResources().getString(R.string.day_is_coming_to_end))
                        .setContentText(context.getResources().getString(R.string.plan_next_day))
                        .setAutoCancel(true)
                        .setShowWhen(true)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pi1)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setCategory(Notification.CATEGORY_MESSAGE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelId = "REMINDERS";
                    NotificationChannel channel = new NotificationChannel(channelId,
                            context.getResources().getString(R.string.notify_plan_next_day),
                            NotificationManager.IMPORTANCE_HIGH);
                    nm1.createNotificationChannel(channel);
                    b1.setChannelId(channelId);
                }
                Formatter f = new Formatter();

                if(MainActivity.planNextDayCal != null){
                    Log.e(TAG, "planNextDayCal   "+f.dateWithTime(MainActivity.planNextDayCal));
                    Log.e(TAG, "now   "+f.dateWithTime(Calendar.getInstance()));
                    if(checker.TimeEquals(MainActivity.planNextDayCal, Calendar.getInstance())){
                        nm1.notify(notificationId, b1.build());
                        Log.e(TAG, "Match");
                    } else {
                        nm1.notify(notificationId, b1.build());
                        Log.e(TAG, "Not match");
                    }

                }


                break;



            case 0:
                // When notification is tapped, call MainActivity.
                Intent i2 = new Intent(context, MainActivity.class);
                PendingIntent pi2 = PendingIntent.getActivity(context, 0, i2, 0);

                NotificationManager nm2 =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                // Prepare notification.
                Notification.Builder b2 = new Notification.Builder(context);
                b2.setSmallIcon(R.drawable.finance43)
                        .setContentTitle("Test")
                        .setContentText("123")
                        .setAutoCancel(true)
                        .setShowWhen(true)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pi2)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setCategory(Notification.CATEGORY_MESSAGE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelId = "REMINDERS";
                    NotificationChannel channel = new NotificationChannel(channelId,
                            context.getResources().getString(R.string.reminders),
                            NotificationManager.IMPORTANCE_HIGH);
                    nm2.createNotificationChannel(channel);
                    b2.setChannelId(channelId);
                }
                nm2.notify(notificationId, b2.build());
                break;


                default:
                    Log.e(TAG, "default");
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
