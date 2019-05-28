package net.eagledev.planner.Activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import net.eagledev.planner.*;
import net.eagledev.planner.Checker;
import net.eagledev.planner.Formatter;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;
import net.eagledev.planner.Reminder;

import java.util.Calendar;

public class AddReminder extends AppCompatActivity implements View.OnClickListener {

    Button cancelButton;
    Button createButton;
    ImageView createImage;
    ImageView cancelImage;
    Button dateButton;
    Button timeButton;
    EditText nameText;
    Context context;

    int id;
    DatePickerDialog dpd;
    TimePickerDialog tpd;
    Formatter f = new Formatter();

    String name;
    Calendar date = Calendar.getInstance();
    public static final int MY_NOTIFICATION_JOB = 0;


    private int sDay;
    private int sMonth;
    private int sYear;
    private int sHour;
    private int sMinute;
    private int notificationID = 1;

    //private AlarmManager alarm;
    //private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        setButtons();
        date = Calendar.getInstance();
        date.setTimeInMillis(System.currentTimeMillis());
        dateButton.setText(f.DateText(date));
        context = this;

    }

    private void setNotification() {

    }

    private void setButtons() {
        Calendar c =  Calendar.getInstance();;
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int month = c.get(Calendar.MONTH);
        final int year = c.get(Calendar.YEAR);
        final int hour = c.get(Calendar.HOUR_OF_DAY)+1;
        final int min = c.get(Calendar.MINUTE);
        final Checker checker = new Checker();
        cancelImage = findViewById(R.id.toolbar_cancel);
        cancelImage.setOnClickListener(this);
        createImage = findViewById(R.id.toolbar_confirm);
        createImage.setOnClickListener(this);
        dateButton = findViewById(R.id.btn_reminder_date);
        timeButton = findViewById(R.id.btn_reminder_time);
        nameText = findViewById(R.id.reminder_name);

        //setNotification();





        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpd = new DatePickerDialog(AddReminder.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        sYear = mYear;
                        sMonth = mMonth;
                        sDay = mDay;
                        date.set(Calendar.YEAR, mYear);
                        date.set(Calendar.MONTH, mMonth);
                        date.set(Calendar.DAY_OF_MONTH, mDay);
                        dateButton.setText(f.DateText(date));
                    }
                },year, month , day);
                dpd.show();
            }
        });

        timeButton . setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tpd = new TimePickerDialog(AddReminder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int mHour, int mMinute) {
                        sHour = mHour;
                        sMinute = mMinute;
                        date.set(Calendar.HOUR_OF_DAY,mHour);
                        date.set(Calendar.MINUTE, mMinute);
                        timeButton.setText(f.Time(date));

                    }
                }, hour, 0, true);
                tpd.show();

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.toolbar_cancel:
                finish();
                break;
            case R.id.toolbar_confirm:
                confirm();
                break;
        }
    }

    private void confirm() {
        Checker checker = new Checker();

        date.set(Calendar.SECOND, 0);
        Log.e("date", f.Date(date) +" "+ f.Time(date));
        if(checker.DateTimeInFuture(date)){
            String name = String.valueOf(nameText.getText());
            int newID = MainActivity.appDatabase.appDao().getMaxRemindersID()+1;
            Reminder reminder = new Reminder(newID, name, date);
            MainActivity.appDatabase.appDao().addReminder(reminder);
            id = newID;
            //setNotification();

            // Set notificationId & text.
            Intent intent = new Intent(AddReminder.this, ReminderReceiver.class);

            intent.putExtra("ID", newID);
            intent.putExtra("TITTLE", R.string.reminder);
            intent.putExtra("TEXT", name);




            Intent nIntent = new Intent(MainActivity.context, ReminderReceiver.class);
            nIntent.putExtra("ID", newID);
            nIntent.putExtra("TITTLE", getResources().getString(R.string.reminder));
            nIntent.putExtra("TEXT", name);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(MainActivity.context, 0, nIntent, 0);
            AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

            date.set(Calendar.SECOND, 0);


            alarm.set(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), alarmIntent);

            //scheduleJob();
            
            finish();
        } else {
            Toast.makeText(getApplicationContext(), R.string.date_in_future , Toast.LENGTH_LONG).show();
        }

    }

    private void scheduleJob() {

        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putInt("ID", id);
        persistableBundle.putString("TITTLE", getResources().getString(R.string.reminder));
        persistableBundle.putString("TEXT", name);
        JobScheduler js = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo job = new JobInfo.Builder(MY_NOTIFICATION_JOB, new ComponentName(context, AlarmService.class))
                .setPeriodic(date.getTimeInMillis())
                .setExtras(persistableBundle)
                .build();
        js.schedule(job);

    }
}
