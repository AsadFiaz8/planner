package net.eagledev.planner;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import android.os.*;

import java.util.logging.LogRecord;

public class BackgroundService extends Service {

    public Context context = this;
    public android.os.Handler handler  = new Handler();
    public static Runnable runnable = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e("Service", "Service crated!");

        runnable = new Runnable() {
            public void run() {
                Log.e("Service", "Service is still running!");
                handler.postDelayed(runnable, 30000);
            }
        };

        handler.postDelayed(runnable, 15000);
    }

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        handler.removeCallbacks(runnable);
        Log.e("Service", "Service stopped!");
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Log.e("Service", "Service started by user!");
    }

}
