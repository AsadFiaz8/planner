package net.eagledev.planner;

import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

public class NotificationJobService extends JobService {

    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("NotificationJobService", "Job started");

        //PendingIntent pendingIntent = PendingIntent.getActivity(this, new Intent(this, MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
        return true;
    }



    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("NotificationJobService", "Job Cancelled before completion");
        jobCancelled = true;
        return true;
    }
}
