package net.eagledev.planner;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import static net.eagledev.planner.App.CHANNEL_ID;

public class NotificationService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String tittle = intent.getStringExtra("tittle");
        String text = intent.getStringExtra("text");

        Intent notifiactionIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifiactionIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(tittle)
                .setContentText(text)
                .setSmallIcon(R.drawable.finance43)
                .setContentIntent(pendingIntent)
                .setShowWhen(false)
                .setPriority(Notification.PRIORITY_LOW)
                .build();
        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
