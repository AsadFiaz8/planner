package net.eagledev.planner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartOnBootReceiver extends BroadcastReceiver {
    Intent serviceIntent;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            if(serviceIntent == null) {
                serviceIntent = new Intent(context, NotificationService.class);
            }
            serviceIntent.putExtra("tittle", context.getResources().getString(R.string.no_scheduled_activity));
            serviceIntent.putExtra("text",context.getResources().getString(R.string.add_now));
            context.startService(serviceIntent);
            Log.e("StartOnBootReceiver", "startService");
        }
    }
}
