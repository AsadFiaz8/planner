package net.eagledev.planner;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class AddActivity extends AppCompatActivity implements View.OnClickListener, NeedPremiumDialog.NeedPremiumDialogListener, HourPickerDialog.HourPickerDialogListener {
    Calendar c;
    DatePickerDialog dpd;
    TimePickerDialog tpd;
    TextView timeStartText;
    ImageButton timeStartButton;
    TextView timeStopText;
    ImageButton timeStopButton;

    int iconID;
    int premiumIcon;
    int colorID;
    int premiumColor;
    TextView textView;
    boolean checked = false;
    Calendar date_start;
    Calendar date_stop;
    Formatter f = new Formatter();
    boolean edit = false;
    int actionID;

    public static final int CODE_ACTIONS = 0;
    public static final int CODE_ICONS = 1;
    public static final int CODE_COLORS = 2;
    public static final int CODE_START = 0;
    public static final int CODE_STOP = 1;

    Context context;
    ValueHolder valueHolder;
    ImageView btn_select_icon;
    TextView dateText;
    ImageButton dateButton;
    int aDay;
    int aMonth;
    int aYear;


    private final static int REQUEST_CODE_1 = 1;

    ImageView imageConfirm;
    ImageView imageCancel;
    ImageView imageIcon;
    ImageView imageColor;
    Button colorButton;
    Button imageButton;
    Dialog d1;
    Dialog d2;

    Action selectedAction;
    String desc = "";
    ImageView imageDelete;


    int year = 0;
    int month = 0;
    int day = 0;
    int startHour = 0;
    int startMinute = 0;
    int stopHour = 0;
    int stopMinute = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_action);
        textView = findViewById(R.id.input_action_name);
        context = this;
        imageIcon = findViewById(R.id.icon_view);
        //imageIcon = new ImageView(context);
        imageIcon.setOnClickListener(this);
        date_start = Calendar.getInstance();
        date_stop = Calendar.getInstance();
        date_start.set(Calendar.HOUR_OF_DAY, 0);
        date_start.set(Calendar.MINUTE, 0);
        date_stop.set(Calendar.HOUR_OF_DAY, 1);
        date_stop.set(Calendar.MINUTE, 0);

        iconID = MainActivity.icons[0];
        imageCancel = findViewById(R.id.toolbar_cancel);
        imageCancel.setOnClickListener(this);
        imageConfirm = findViewById(R.id.toolbar_confirm);
        imageConfirm.setOnClickListener(this);
        imageColor = findViewById(R.id.color_view);
        imageColor.setOnClickListener(this);
        //imageColor.setBackgroundColor(MainActivity.colors[0]);
        int[] ints = {0};
        int[][] all = {ints};
        int[] colors = {MainActivity.colors[0]};
        imageColor.setBackgroundTintList(new ColorStateList(all,colors));



        //paramsLinear = dateLinearLayout.getLayoutParams();
        //paramsRelative =  dateRelativeLayout.getLayoutParams();
        SetupDate();
        timeStartText.setText(f.Time(date_start));
        timeStopText.setText(f.Time(date_stop));
        SetValues();
        if(edit){
            imageDelete = findViewById(R.id.toolbar_delete);
            imageDelete.setVisibility(View.VISIBLE);
            imageDelete.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        clickIcon(v.getId());
        clickColor(v.getId());
        switch (v.getId()) {
            case R.id.toolbar_cancel:
                finish();
                break;
            case R.id.toolbar_confirm:
                confirm();
                break;
            case R.id.toolbar_delete:
                MainActivity.appDatabase.appDao().deleteAction(actionID);
                MainActivity.fDatabase.DeleteAction(actionID);
                refresh();
                setResult(MainActivity.CODE_CREATED);
                finish();
                Toast.makeText(getApplicationContext(), R.string.action_deleted, Toast.LENGTH_LONG).show();
                break;

        }
    }

    @Override
    public void getPremiumDialogResultCode(int resultCode) {
        MainActivity.valueHolder.changePremiumPoints(-1);
        switch (resultCode){
            case CODE_ACTIONS:
                CreateAction();
                MainActivity.needRefresh = true;
                finish();
                break;
            case CODE_COLORS:
                colorID = MainActivity.colors[premiumColor];
                int[] ints = {0};
                int[][] all = {ints};
                int[] colors = {colorID};
                imageColor.setBackgroundTintList(new ColorStateList(all,colors));
                //imageColor.setBackgroundColor(colorID);
                d2.dismiss();
                break;
            case CODE_ICONS:
                iconID = MainActivity.icons[premiumIcon];
                d1.dismiss();
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
        }
    }

    @Override
    public void getHourPickerDialogTime(int requestCode, int hour, int minute) {
        if(requestCode == CODE_START){
            date_start.set(Calendar.HOUR_OF_DAY, hour);
            date_start.set(Calendar.MINUTE, minute);
            timeStartText.setText(f.Time(date_start) );
            if(date_start.get(Calendar.HOUR_OF_DAY)<23){
                date_stop.set(Calendar.HOUR_OF_DAY, hour+1);
                date_stop.set(Calendar.MINUTE,0);
                timeStopText.setText(f.Time(date_stop));
            } else {
                date_stop.set(Calendar.HOUR_OF_DAY, 23);
                date_stop.set(Calendar.MINUTE,59);
                timeStopText.setText(f.Time(date_stop));
            }
        } else if(requestCode == CODE_STOP){
            if(hour == 0 && minute == 0) {
                date_stop.set(Calendar.HOUR_OF_DAY, 23);
                date_stop.set(Calendar.MINUTE, 59);
            } else {
                date_stop.set(Calendar.HOUR_OF_DAY, hour);
                date_stop.set(Calendar.MINUTE, minute);
            }
            timeStopText.setText(f.Time(date_stop));
        }
    }

    private void refresh(){
        MainActivity.needRefresh = true;
        Intent intent = new Intent();
        intent.putExtra("message_return", "refresh");
        setResult(RESULT_OK, intent);
    }

    private void SetValues() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Action selectedAction = new Action();
        if(bundle!=null) {
            edit = (boolean) bundle.get("edit");
            if(edit){
                actionID = (int) bundle.get("ID");
                selectedAction = MainActivity.appDatabase.appDao().idAction(actionID);
                if(selectedAction == null){
                    finish();
                }
                findViewById(R.id.color_view).setOnClickListener(this);
                if(selectedAction != null){
                    desc = selectedAction.getDesc();
                    year = selectedAction.getStart_year();
                    month = selectedAction.getStart_month();
                    day = selectedAction.getStart_day();
                    startHour = selectedAction.getStart_hour();
                    startMinute = selectedAction.getStart_minute();
                    stopHour = selectedAction.getStop_hour();
                    stopMinute = selectedAction.getStop_minute();
                    date_start.set(year,month,day,startHour,startMinute);
                    date_stop.set(year,month,day,stopHour,stopMinute);
                    iconID = selectedAction.getIcon();
                    colorID = selectedAction.getColor();
                    textView.setText(desc);
                    dateText.setText(f.DateText(date_start));
                    imageIcon.setImageDrawable(getDrawable(iconID));
                    setColor();
                    textView.setText(desc);
                    timeStartText.setText(f.Time(date_start) );
                    timeStopText.setText(f.Time(date_stop));
                    imageDelete = findViewById(R.id.toolbar_delete);
                    imageDelete.setVisibility(View.VISIBLE);
                    imageDelete.setOnClickListener(this);
                } else {
                    finish();
                }
            }



        }
    }

    private void SetupDate() {
        c = Calendar.getInstance();
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int month = c.get(Calendar.MONTH);
        final int year = c.get(Calendar.YEAR);
        final int hour = c.get(Calendar.HOUR_OF_DAY)+1;
        final int min = c.get(Calendar.MINUTE);
        colorID = MainActivity.colors[0];

        if(MainActivity.valueHolder == null){
            MainActivity.setValueHolder();
        }

        // Number pickers

        String[] displayHours = new String[24];
        String[] displayMinutes = {0+"0", String.valueOf(10), String.valueOf(20), String.valueOf(30), String.valueOf(40), String.valueOf(50)};
        for (int h = 0; h < displayHours.length; h++) {
            displayHours[h] = f.z(h);
        }

        // Buttons
        dateText = findViewById(R.id.action_date_text);
        dateText.setText(f.Date(c));
        dateButton = findViewById(R.id.action_date_button);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateText.performClick();
            }
        });
        aDay = day;
        aMonth = month;
        aYear = year;
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dateText.setTextColor(getResources().getColor(R.color.colorAccent));
                dpd = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, final int mYear, final int mMonth, final int mDay) {
                        aDay = mDay;
                        aMonth = mMonth;
                        aYear = mYear;
                        date_start.set(Calendar.YEAR, mYear);
                        date_start.set(Calendar.MONTH, mMonth);
                        date_start.set(Calendar.DAY_OF_MONTH, mDay);
                        dateText.setText(f.Date(date_start));
                        date_stop.set(Calendar.YEAR, mYear);
                        date_stop.set(Calendar.MONTH, mMonth);
                        date_stop.set(Calendar.DAY_OF_MONTH, mDay);

                    }
                },year, month , day);
                dpd.show();
                //dateText.setTextColor(getResources().getColor(R.color.white));
            }
        });



        timeStartText = (TextView) findViewById(R.id.action_start_time_text);
        timeStartButton = findViewById(R.id.action_start_time_button);
        timeStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeStartText.performClick();
            }
        });
        timeStartText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!MainActivity.valueHolder.isDatePickerButton()) {

                    HourPickerDialog hourPickerDialog = new HourPickerDialog(context, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), 0);
                    hourPickerDialog.ShowDialog(CODE_START);
                }else {
                    tpd = new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int mHour, int mMinute) {
                            date_start.set(Calendar.HOUR_OF_DAY, mHour);
                            date_start.set(Calendar.MINUTE, mMinute);
                            timeStartText.setText(f.Time(date_start) );
                            if(date_start.get(Calendar.HOUR_OF_DAY)<23){
                                date_stop.set(Calendar.HOUR_OF_DAY, mHour+1);
                                date_stop.set(Calendar.MINUTE,0);
                                timeStopText.setText(f.Time(date_stop));
                            } else {
                                date_stop.set(Calendar.HOUR_OF_DAY, 23);
                                date_stop.set(Calendar.MINUTE,59);
                                timeStopText.setText(f.Time(date_stop));
                            }
                        }
                    }, hour, 0, true);
                    tpd.show();
                }
            }
        });


        timeStopText = (TextView) findViewById(R.id.action_stop_time_text);
        timeStopButton = findViewById(R.id.action_stop_time_button);
        timeStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeStopText.performClick();
            }
        });
        timeStopText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!MainActivity.valueHolder.isDatePickerButton()) {
                    HourPickerDialog hourPickerDialog = new HourPickerDialog(context, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), 0);
                    hourPickerDialog.ShowDialog(CODE_STOP);
                }else {
                    tpd = new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int mHour, int mMinute) {
                            if(mHour == 0 && mMinute == 0) {
                                date_stop.set(Calendar.HOUR_OF_DAY, 23);
                                date_stop.set(Calendar.MINUTE, 59);
                            } else {
                                date_stop.set(Calendar.HOUR_OF_DAY, mHour);
                                date_stop.set(Calendar.MINUTE, mMinute);
                            }
                            timeStopText.setText(f.Time(date_stop));
                        }
                    }, date_start.get(Calendar.HOUR_OF_DAY)+1, 0, true);
                    tpd.show();
                }
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);

        // The returned result data is identified by requestCode.
        // The request code is specified in startActivityForResult(intent, REQUEST_CODE_1); method.
        switch (requestCode)
        {
            // This request code is set by startActivityForResult(intent, REQUEST_CODE_1) method.
            case REQUEST_CODE_1:

                if(resultCode == RESULT_OK)
                {
                    String messageReturn = dataIntent.getStringExtra("message_return");
                    iconID = Integer.parseInt(messageReturn);



                }
        }
    }

    private void CreateAction() {
        int newID = MainActivity.appDatabase.appDao().getMaxActionID()+1;
        Action newAction = new Action(newID,textView.getText().toString(), date_start, date_stop, iconID, colorID );
        MainActivity.appDatabase.appDao().addAction(newAction);
        MainActivity.fDatabase.AddAction(newAction);
    }

    private void UpdateAction() {
        Action newAction = new Action(actionID,textView.getText().toString(), date_start, date_stop, iconID, colorID );
        MainActivity.appDatabase.appDao().updateAction(newAction);
        MainActivity.fDatabase.AddAction(newAction);
    }

    private void confirm() {
        Checker checker = new Checker();

        if(MainActivity.valueHolder == null){
            MainActivity.setValueHolder();
        }
        if(MainActivity.valueHolder.canUsePremium()) {
            if(checker.Before(date_start,date_stop)) {
                checked = true;
            }
            else {
                Toast.makeText(getApplicationContext(), R.string.stop_must_be_after_start, Toast.LENGTH_LONG).show();
            }
        }
        else {
            if(checker.DateTimeInFuture(date_start)) {
                if(checker.DateTimeInFuture(date_stop)) {
                    if(checker.Before(date_start,date_stop)) {
                        checked = true;
                    }
                    else {
                        Toast.makeText(getApplicationContext(), R.string.stop_must_be_after_start, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.stop_date_in_future, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.start_date_in_future, Toast.LENGTH_LONG).show();
            }
        }


        if(checked) {
            if(MainActivity.valueHolder == null){
                MainActivity.setValueHolder();
            }
            List<Action> actionsFromDay = MainActivity.appDatabase.appDao().getActionsFromDay(date_start.get(Calendar.DAY_OF_MONTH), date_start.get(Calendar.MONTH), date_start.get(Calendar.YEAR));
            int actionsCount = actionsFromDay.size();
            Log.e("actionsCount", String.valueOf(actionsCount) + "   " + getResources().getInteger(R.integer.premium_max_actions_one_day));

            if(actionsCount < getResources().getInteger(R.integer.premium_max_actions_one_day) || MainActivity.valueHolder.canUsePremium()) {
                //Spełnia warunki lub jest premium lub premium reklamowe
                setResult(MainActivity.CODE_CREATED);
                if(!edit) {
                    CreateAction();
                } else {
                    UpdateAction();
                    setResult(MainActivity.CODE_CREATED);
                }
                //MainActivity.needRefresh = true;

                setResult(MainActivity.CODE_CREATED);
                finish();
            } else {
                NeedPremiumDialog pd = new NeedPremiumDialog(context, CODE_ACTIONS);
                pd.ShowDialog(getString(R.string.premium_reason2));
            }



        }
    }

    private void clickColor(int id) {
        switch (id) {

            case R.id.color_view:
                d2 = new Dialog(AddActivity.this);
                d2.setTitle("Color Picker");
                d2.setContentView(R.layout.activity_select_color);
                d2.show();
                d2.findViewById(R.id.color_button0).setOnClickListener(this);
                d2.findViewById(R.id.color_button1).setOnClickListener(this);
                d2.findViewById(R.id.color_button2).setOnClickListener(this);
                d2.findViewById(R.id.color_button3).setOnClickListener(this);
                d2.findViewById(R.id.color_button4).setOnClickListener(this);
                d2.findViewById(R.id.color_button5).setOnClickListener(this);

                d2.findViewById(R.id.color_button_green1).setOnClickListener(this);
                d2.findViewById(R.id.color_button_green2).setOnClickListener(this);
                d2.findViewById(R.id.color_button_green3).setOnClickListener(this);
                d2.findViewById(R.id.color_button_green4).setOnClickListener(this);
                d2.findViewById(R.id.color_button_green5).setOnClickListener(this);
                d2.findViewById(R.id.color_button_green6).setOnClickListener(this);

                d2.findViewById(R.id.color_button_yellow1).setOnClickListener(this);
                d2.findViewById(R.id.color_button_yellow2).setOnClickListener(this);
                d2.findViewById(R.id.color_button_yellow3).setOnClickListener(this);
                d2.findViewById(R.id.color_button_yellow4).setOnClickListener(this);
                d2.findViewById(R.id.color_button_yellow5).setOnClickListener(this);
                d2.findViewById(R.id.color_button_yellow6).setOnClickListener(this);

                d2.findViewById(R.id.color_button_orange1).setOnClickListener(this);
                d2.findViewById(R.id.color_button_orange2).setOnClickListener(this);
                d2.findViewById(R.id.color_button_orange3).setOnClickListener(this);
                d2.findViewById(R.id.color_button_orange4).setOnClickListener(this);
                d2.findViewById(R.id.color_button_orange5).setOnClickListener(this);
                d2.findViewById(R.id.color_button_orange6).setOnClickListener(this);

                d2.findViewById(R.id.color_button_red1).setOnClickListener(this);
                d2.findViewById(R.id.color_button_red2).setOnClickListener(this);
                d2.findViewById(R.id.color_button_red3).setOnClickListener(this);
                d2.findViewById(R.id.color_button_red4).setOnClickListener(this);
                d2.findViewById(R.id.color_button_red5).setOnClickListener(this);
                d2.findViewById(R.id.color_button_red6).setOnClickListener(this);

                d2.findViewById(R.id.color_button_violet1).setOnClickListener(this);
                d2.findViewById(R.id.color_button_violet2).setOnClickListener(this);
                d2.findViewById(R.id.color_button_violet3).setOnClickListener(this);
                d2.findViewById(R.id.color_button_violet4).setOnClickListener(this);
                d2.findViewById(R.id.color_button_violet5).setOnClickListener(this);
                d2.findViewById(R.id.color_button_violet6).setOnClickListener(this);

                d2.findViewById(R.id.color_button_blue1).setOnClickListener(this);
                d2.findViewById(R.id.color_button_blue2).setOnClickListener(this);
                d2.findViewById(R.id.color_button_blue3).setOnClickListener(this);
                d2.findViewById(R.id.color_button_blue4).setOnClickListener(this);
                d2.findViewById(R.id.color_button_blue5).setOnClickListener(this);
                d2.findViewById(R.id.color_button_blue6).setOnClickListener(this);

                d2.findViewById(R.id.color_button_navy1).setOnClickListener(this);
                d2.findViewById(R.id.color_button_navy2).setOnClickListener(this);
                d2.findViewById(R.id.color_button_navy3).setOnClickListener(this);
                d2.findViewById(R.id.color_button_navy4).setOnClickListener(this);
                d2.findViewById(R.id.color_button_navy5).setOnClickListener(this);
                d2.findViewById(R.id.color_button_navy6).setOnClickListener(this);

                break;

            case R.id.color_button0:
                colorID = MainActivity.colors[0];
                setColor();
                d2.dismiss();
                break;
            case R.id.color_button1:
                colorID = MainActivity.colors[1];
                setColor();
                d2.dismiss();
                break;
            case R.id.color_button2:
                colorID = MainActivity.colors[2];
                setColor();
                d2.dismiss();
                break;
            case R.id.color_button3:
                colorID = MainActivity.colors[3];
                setColor();
                d2.dismiss();
                break;
            case R.id.color_button4:
                colorID = MainActivity.colors[4];
                setColor();
                d2.dismiss();
                break;
            case R.id.color_button5:
                colorID = MainActivity.colors[5];
                setColor();
                d2.dismiss();
                break;

            case R.id.color_button_green1:
                premiumColor = 6;
                setPremiumColor();
                break;
            case R.id.color_button_green2:
                premiumColor = 7;
                setPremiumColor();
                break;
            case R.id.color_button_green3:
                premiumColor = 8;
                setPremiumColor();
                break;
            case R.id.color_button_green4:
                premiumColor = 9;
                setPremiumColor();
                break;
            case R.id.color_button_green5:
                premiumColor = 10;
                setPremiumColor();
                break;
            case R.id.color_button_green6:
                premiumColor = 11;
                setPremiumColor();
                break;

            case R.id.color_button_yellow1:
                premiumColor = 12;
                setPremiumColor();
                break;
            case R.id.color_button_yellow2:
                premiumColor = 13;
                setPremiumColor();
                break;
            case R.id.color_button_yellow3:
                premiumColor = 14;
                setPremiumColor();
                break;
            case R.id.color_button_yellow4:
                premiumColor = 15;
                setPremiumColor();
                break;
            case R.id.color_button_yellow5:
                premiumColor = 16;
                setPremiumColor();
                break;
            case R.id.color_button_yellow6:
                premiumColor = 17;
                setPremiumColor();
                break;

            case R.id.color_button_orange1:
                premiumColor = 18;
                setPremiumColor();
                break;
            case R.id.color_button_orange2:
                premiumColor = 19;
                setPremiumColor();
                break;
            case R.id.color_button_orange3:
                premiumColor = 20;
                setPremiumColor();
                break;
            case R.id.color_button_orange4:
                premiumColor = 21;
                setPremiumColor();
                break;
            case R.id.color_button_orange5:
                premiumColor = 22;
                setPremiumColor();
                break;
            case R.id.color_button_orange6:
                premiumColor = 23;
                setPremiumColor();
                break;

            case R.id.color_button_red1:
                premiumColor = 24;
                setPremiumColor();
                break;
            case R.id.color_button_red2:
                premiumColor = 25;
                setPremiumColor();
                break;
            case R.id.color_button_red3:
                premiumColor = 26;
                setPremiumColor();
                break;
            case R.id.color_button_red4:
                premiumColor = 27;
                setPremiumColor();
                break;
            case R.id.color_button_red5:
                premiumColor = 28;
                setPremiumColor();
                break;
            case R.id.color_button_red6:
                premiumColor = 29;
                setPremiumColor();
                break;

            case R.id.color_button_violet1:
                premiumColor = 36;
                setPremiumColor();
                break;
            case R.id.color_button_violet2:
                premiumColor = 37;
                setPremiumColor();
                break;
            case R.id.color_button_violet3:
                premiumColor = 38;
                setPremiumColor();
                break;
            case R.id.color_button_violet4:
                premiumColor = 39;
                setPremiumColor();
                break;
            case R.id.color_button_violet5:
                premiumColor = 40;
                setPremiumColor();
                break;
            case R.id.color_button_violet6:
                premiumColor = 41;
                setPremiumColor();
                break;

            case R.id.color_button_blue1:
                premiumColor = 42;
                setPremiumColor();
                break;
            case R.id.color_button_blue2:
                premiumColor = 43;
                setPremiumColor();
                break;
            case R.id.color_button_blue3:
                premiumColor = 44;
                setPremiumColor();
                break;
            case R.id.color_button_blue4:
                premiumColor = 45;
                setPremiumColor();
                break;
            case R.id.color_button_blue5:
                premiumColor = 46;
                setPremiumColor();
                break;
            case R.id.color_button_blue6:
                premiumColor = 47;
                setPremiumColor();
                break;

            case R.id.color_button_navy1:
                premiumColor = 48;
                setPremiumColor();
                break;
            case R.id.color_button_navy2:
                premiumColor = 49;
                setPremiumColor();
                break;
            case R.id.color_button_navy3:
                premiumColor = 50;
                setPremiumColor();
                break;
            case R.id.color_button_navy4:
                premiumColor = 51;
                setPremiumColor();
                break;
            case R.id.color_button_navy5:
                premiumColor = 52;
                setPremiumColor();
                break;
            case R.id.color_button_navy6:
                premiumColor = 53;
                setPremiumColor();
                break;

        }
    }

    private void setPremiumColor() {
        if(MainActivity.valueHolder.canUsePremium()){
            colorID = MainActivity.colors[premiumColor];
            int[] ints = {0};
            int[][] all = {ints};
            int[] colors = {colorID};
            imageColor.setBackgroundTintList(new ColorStateList(all,colors));
            //imageColor.setBackgroundColor(colorID);
            d2.dismiss();
        } else {
            NeedPremiumDialog pd = new NeedPremiumDialog(context, CODE_COLORS);
            pd.ShowDialog(getString(R.string.premium_reason3));
        }
    }

    private void setColor() {
        int[] ints = {0};
        int[][] all = {ints};
        int[] colors = {colorID};
        imageColor.setBackgroundTintList(new ColorStateList(all,colors));
    }

    private void clickIcon(int id) {
        switch (id){
            case R.id.icon_view:
                d1 = new Dialog(AddActivity.this);
                d1.setTitle("Icon Picker");
                d1.setContentView(R.layout.activity_select_icon);
                d1.show();
                d1.findViewById(R.id.image_button1).setOnClickListener(this);
                d1.findViewById(R.id.image_button2).setOnClickListener(this);
                d1.findViewById(R.id.image_button3).setOnClickListener(this);
                d1.findViewById(R.id.image_button4).setOnClickListener(this);
                d1.findViewById(R.id.image_button5).setOnClickListener(this);
                d1.findViewById(R.id.image_button6).setOnClickListener(this);
                d1.findViewById(R.id.image_button7).setOnClickListener(this);
                d1.findViewById(R.id.image_button8).setOnClickListener(this);
                d1.findViewById(R.id.image_button9).setOnClickListener(this);
                d1.findViewById(R.id.image_button10).setOnClickListener(this);
                d1.findViewById(R.id.image_button11).setOnClickListener(this);
                d1.findViewById(R.id.image_button12).setOnClickListener(this);
                d1.findViewById(R.id.image_button13).setOnClickListener(this);
                d1.findViewById(R.id.image_button14).setOnClickListener(this);
                d1.findViewById(R.id.image_button15).setOnClickListener(this);
                d1.findViewById(R.id.image_button16).setOnClickListener(this);
                d1.findViewById(R.id.image_button17).setOnClickListener(this);
                d1.findViewById(R.id.image_button18).setOnClickListener(this);
                d1.findViewById(R.id.image_button19).setOnClickListener(this);
                d1.findViewById(R.id.image_button20).setOnClickListener(this);

                //Premium icons
                d1.findViewById(R.id.image_button21).setOnClickListener(this);
                d1.findViewById(R.id.image_button22).setOnClickListener(this);
                d1.findViewById(R.id.image_button23).setOnClickListener(this);
                d1.findViewById(R.id.image_button24).setOnClickListener(this);
                d1.findViewById(R.id.image_button25).setOnClickListener(this);
                d1.findViewById(R.id.image_button26).setOnClickListener(this);
                d1.findViewById(R.id.image_button27).setOnClickListener(this);
                d1.findViewById(R.id.image_button28).setOnClickListener(this);
                d1.findViewById(R.id.image_button29).setOnClickListener(this);
                d1.findViewById(R.id.image_button30).setOnClickListener(this);
                d1.findViewById(R.id.image_button31).setOnClickListener(this);
                d1.findViewById(R.id.image_button32).setOnClickListener(this);
                d1.findViewById(R.id.image_button33).setOnClickListener(this);
                d1.findViewById(R.id.image_button34).setOnClickListener(this);
                d1.findViewById(R.id.image_button35).setOnClickListener(this);
                d1.findViewById(R.id.image_button36).setOnClickListener(this);
                d1.findViewById(R.id.image_button37).setOnClickListener(this);
                d1.findViewById(R.id.image_button38).setOnClickListener(this);
                d1.findViewById(R.id.image_button40).setOnClickListener(this);
                d1.findViewById(R.id.image_button41).setOnClickListener(this);
                d1.findViewById(R.id.image_button42).setOnClickListener(this);
                d1.findViewById(R.id.image_button43).setOnClickListener(this);
                d1.findViewById(R.id.image_button44).setOnClickListener(this);
                d1.findViewById(R.id.image_button45).setOnClickListener(this);
                d1.findViewById(R.id.image_button46).setOnClickListener(this);
                d1.findViewById(R.id.image_button47).setOnClickListener(this);
                d1.findViewById(R.id.image_button48).setOnClickListener(this);
                d1.findViewById(R.id.image_button49).setOnClickListener(this);
                d1.findViewById(R.id.image_button50).setOnClickListener(this);
                d1.findViewById(R.id.image_button51).setOnClickListener(this);
                d1.findViewById(R.id.image_button52).setOnClickListener(this);
                d1.findViewById(R.id.image_button53).setOnClickListener(this);
                d1.findViewById(R.id.image_button54).setOnClickListener(this);
                d1.findViewById(R.id.image_button55).setOnClickListener(this);
                d1.findViewById(R.id.image_button56).setOnClickListener(this);
                d1.findViewById(R.id.image_button57).setOnClickListener(this);
                d1.findViewById(R.id.image_button58).setOnClickListener(this);
                d1.findViewById(R.id.image_button59).setOnClickListener(this);
                d1.findViewById(R.id.image_button60).setOnClickListener(this);
                d1.findViewById(R.id.image_button51).setOnClickListener(this);
                d1.findViewById(R.id.image_button62).setOnClickListener(this);
                d1.findViewById(R.id.image_button63).setOnClickListener(this);
                d1.findViewById(R.id.image_button64).setOnClickListener(this);
                d1.findViewById(R.id.image_button65).setOnClickListener(this);
                d1.findViewById(R.id.image_button66).setOnClickListener(this);
                d1.findViewById(R.id.image_button67).setOnClickListener(this);
                d1.findViewById(R.id.image_button68).setOnClickListener(this);
                d1.findViewById(R.id.image_button69).setOnClickListener(this);
                d1.findViewById(R.id.image_button70).setOnClickListener(this);
                d1.findViewById(R.id.image_button71).setOnClickListener(this);
                d1.findViewById(R.id.image_button72).setOnClickListener(this);
                d1.findViewById(R.id.image_button73).setOnClickListener(this);
                d1.findViewById(R.id.image_button74).setOnClickListener(this);
                d1.findViewById(R.id.image_button75).setOnClickListener(this);
                d1.findViewById(R.id.image_button76).setOnClickListener(this);
                d1.findViewById(R.id.image_button77).setOnClickListener(this);
                d1.findViewById(R.id.image_button78).setOnClickListener(this);
                d1.findViewById(R.id.image_button79).setOnClickListener(this);
                d1.findViewById(R.id.image_button80).setOnClickListener(this);
                d1.findViewById(R.id.image_button81).setOnClickListener(this);
                break;

            case R.id.image_button1:
                d1.dismiss();
                iconID = 0;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
            case R.id.image_button2:
                d1.dismiss();
                iconID = 1;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
            case R.id.image_button3:
                d1.dismiss();
                iconID = 2;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
            case R.id.image_button4:
                d1.dismiss();
                iconID = 3;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
            case R.id.image_button5:
                d1.dismiss();
                iconID = 4;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
            case R.id.image_button6:
                d1.dismiss();
                iconID = 5;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
            case R.id.image_button7:
                d1.dismiss();
                iconID = 6;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
            case R.id.image_button8:
                d1.dismiss();
                iconID = 7;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
            case R.id.image_button9:
                d1.dismiss();
                iconID = 8;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
            case R.id.image_button10:
                d1.dismiss();
                iconID = 9;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
            case R.id.image_button11:
                d1.dismiss();
                iconID = 10;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
            case R.id.image_button12:
                d1.dismiss();
                iconID = 11;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
            case R.id.image_button13:
                d1.dismiss();
                iconID = 12;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
            case R.id.image_button14:
                d1.dismiss();
                iconID = 13;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
            case R.id.image_button15:
                d1.dismiss();
                iconID = 14;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
            case R.id.image_button16:
                d1.dismiss();
                iconID = 15;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
            case R.id.image_button17:
                d1.dismiss();
                iconID = 16;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
            case R.id.image_button18:
                d1.dismiss();
                iconID = 17;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
            case R.id.image_button19:
                d1.dismiss();
                iconID = 18;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;
            case R.id.image_button20:
                d1.dismiss();
                iconID = 19;
                iconID = MainActivity.icons[iconID];
                imageIcon.setImageDrawable(getDrawable(iconID));
                break;

            //Premium icons
            case R.id.image_button21:
                premiumIcon = 20;
                selectPremiumIcon();
                break;
            case R.id.image_button22:
                premiumIcon = 21;
                selectPremiumIcon();
                break;
            case R.id.image_button23:
                premiumIcon = 22;
                selectPremiumIcon();
                break;
            case R.id.image_button24:
                premiumIcon = 23;
                selectPremiumIcon();
                break;
            case R.id.image_button25:
                premiumIcon = 24;
                selectPremiumIcon();
                break;
            case R.id.image_button26:
                premiumIcon = 25;
                selectPremiumIcon();
                break;
            case R.id.image_button27:
                premiumIcon = 26;
                selectPremiumIcon();
                break;
            case R.id.image_button28:
                premiumIcon = 27;
                selectPremiumIcon();
                break;
            case R.id.image_button29:
                premiumIcon = 28;
                selectPremiumIcon();
                break;
            case R.id.image_button30:
                premiumIcon = 29;
                selectPremiumIcon();
                break;
            case R.id.image_button31:
                premiumIcon = 30;
                selectPremiumIcon();
                break;
            case R.id.image_button32:
                premiumIcon = 31;
                selectPremiumIcon();
                break;
            case R.id.image_button33:
                premiumIcon = 32;
                selectPremiumIcon();
                break;
            case R.id.image_button34:
                premiumIcon = 33;
                selectPremiumIcon();
                break;
            case R.id.image_button35:
                premiumIcon = 34;
                selectPremiumIcon();
                break;
            case R.id.image_button36:
                premiumIcon = 35;
                selectPremiumIcon();
                break;
            case R.id.image_button37:
                premiumIcon = 36;
                selectPremiumIcon();
                break;
            case R.id.image_button38:
                premiumIcon = 37;
                selectPremiumIcon();
                break;
            case R.id.image_button40:
                premiumIcon = 38;
                selectPremiumIcon();
                break;
            case R.id.image_button41:
                premiumIcon = 39;
                selectPremiumIcon();
                break;
            case R.id.image_button42:
                premiumIcon = 40;
                selectPremiumIcon();
                break;
            case R.id.image_button43:
                premiumIcon = 41;
                selectPremiumIcon();
                break;
            case R.id.image_button44:
                premiumIcon = 42;
                selectPremiumIcon();
                break;
            case R.id.image_button45:
                premiumIcon = 43;
                selectPremiumIcon();
                break;
            case R.id.image_button46:
                premiumIcon = 44;
                selectPremiumIcon();
                break;
            case R.id.image_button47:
                premiumIcon = 45;
                selectPremiumIcon();
                break;
            case R.id.image_button48:
                premiumIcon = 46;
                selectPremiumIcon();
                break;
            case R.id.image_button49:
                premiumIcon = 47;
                selectPremiumIcon();
                break;
            case R.id.image_button50:
                premiumIcon = 48;
                selectPremiumIcon();
                break;
            case R.id.image_button51:
                premiumIcon = 49;
                selectPremiumIcon();
                break;
            case R.id.image_button52:
                premiumIcon = 50;
                selectPremiumIcon();
                break;
            case R.id.image_button53:
                premiumIcon = 51;
                selectPremiumIcon();
                break;
            case R.id.image_button54:
                premiumIcon = 52;
                selectPremiumIcon();
                break;
            case R.id.image_button55:
                premiumIcon = 53;
                selectPremiumIcon();
                break;
            case R.id.image_button56:
                premiumIcon = 54;
                selectPremiumIcon();
                break;
            case R.id.image_button57:
                premiumIcon = 55;
                selectPremiumIcon();
                break;
            case R.id.image_button58:
                premiumIcon = 56;
                selectPremiumIcon();
                break;
            case R.id.image_button59:
                premiumIcon = 57;
                selectPremiumIcon();
                break;
            case R.id.image_button60:
                premiumIcon = 58;
                selectPremiumIcon();
                break;
            case R.id.image_button61:
                premiumIcon = 59;
                selectPremiumIcon();
                break;
            case R.id.image_button62:
                premiumIcon = 60;
                selectPremiumIcon();
                break;
            case R.id.image_button63:
                premiumIcon = 61;
                selectPremiumIcon();
                break;
            case R.id.image_button64:
                premiumIcon = 62;
                selectPremiumIcon();
                break;
            case R.id.image_button65:
                premiumIcon = 63;
                selectPremiumIcon();
                break;
            case R.id.image_button66:
                premiumIcon = 64;
                selectPremiumIcon();
                break;
            case R.id.image_button67:
                premiumIcon = 65;
                selectPremiumIcon();
                break;
            case R.id.image_button68:
                premiumIcon = 66;
                selectPremiumIcon();
                break;
            case R.id.image_button69:
                premiumIcon = 67;
                selectPremiumIcon();
                break;
            case R.id.image_button70:
                premiumIcon = 68;
                selectPremiumIcon();
                break;
            case R.id.image_button71:
                premiumIcon = 69;
                selectPremiumIcon();
                break;
            case R.id.image_button72:
                premiumIcon = 70;
                selectPremiumIcon();
                break;
            case R.id.image_button73:
                premiumIcon = 71;
                selectPremiumIcon();
                break;
            case R.id.image_button74:
                premiumIcon = 72;
                selectPremiumIcon();
                break;
            case R.id.image_button75:
                premiumIcon = 73;
                selectPremiumIcon();
                break;
            case R.id.image_button76:
                premiumIcon = 74;
                selectPremiumIcon();
                break;
            case R.id.image_button77:
                premiumIcon = 75;
                selectPremiumIcon();
                break;
            case R.id.image_button78:
                premiumIcon = 76;
                selectPremiumIcon();
                break;
            case R.id.image_button79:
                premiumIcon = 77;
                selectPremiumIcon();
                break;
            case R.id.image_button80:
                premiumIcon = 78;
                selectPremiumIcon();
                break;
            case R.id.image_button81:
                premiumIcon = 79;
                selectPremiumIcon();
                break;
        }

    }

    private void selectPremiumIcon() {
        if(MainActivity.valueHolder.canUsePremium()){
            iconID = MainActivity.icons[premiumIcon];
            d1.dismiss();
            imageIcon.setImageDrawable(getDrawable(iconID));
        } else {
            NeedPremiumDialog pd = new NeedPremiumDialog(context, CODE_ICONS);
            pd.ShowDialog(getString(R.string.premium_reason3));
        }
    }
}
