package net.eagledev.planner.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import net.eagledev.planner.BuyPremiumActivity;
import net.eagledev.planner.Checker;
import net.eagledev.planner.Formatter;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;
import net.eagledev.planner.Routine;
import net.eagledev.planner.WatchPremiumAdActivity;

import java.util.Calendar;
import java.util.List;

public class EditRoutineActivity extends Activity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener{

    CheckBox CheckboxMonday;
    CheckBox CheckboxTuesday;
    CheckBox CheckboxWednesday;
    CheckBox CheckboxThursday;
    CheckBox CheckboxFriday;
    CheckBox CheckboxSaturday;
    CheckBox CheckboxSunday;
    CheckBox CheckboxWorkDays;
    CheckBox CheckboxWeekends;

    boolean monday;
    boolean tuesday;
    boolean wednesday;
    boolean thursday;
    boolean friday;
    boolean saturday;
    boolean sunday;
    boolean workDays;
    boolean weekends;

    String name;
    Calendar start;
    Calendar stop;
    int icon;
    int premiumIcon;
    int color;
    int premiumColor;
    Formatter f = new Formatter();
    Dialog d1;
    Dialog d2;

    EditText nameText;
    Button btnStartHour;
    Button btnStopHour;
    NumberPicker startHourPicker;
    NumberPicker startMinutePicker;
    NumberPicker stopHourPicker;
    NumberPicker stopMinutePicker;
    Button btnSelectIcon;
    TimePickerDialog tpd;
    Routine selectedRoutine;
    int routineID;
    ImageView imageConfirm;
    ImageView imageCancel;
    ImageView imageDelete;
    ImageView imageIcon;
    ImageView imageColor;
    Checker checker = new Checker();

    RelativeLayout buttonsLayout;
    RelativeLayout pickersLayout;
    ViewGroup.LayoutParams paramsButtons;
    ViewGroup.LayoutParams paramsPickers;

    private final static int REQUEST_CODE_2 = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routine);

        setButtons();
        nameText = findViewById(R.id.input_routine_name);
        start = Calendar.getInstance();
        stop = Calendar.getInstance();
        setupCheckboxes();
        setValues();
        imageIcon = findViewById(R.id.icon_view);
        imageColor = findViewById(R.id.color_view);
        setColor();
        imageIcon.setImageDrawable(getDrawable(icon));

        startHourPicker = findViewById(R.id.start_hour_picker);
        startMinutePicker = findViewById(R.id.start_minute_picker);
        stopHourPicker = findViewById(R.id.stop_hour_picker);
        stopMinutePicker = findViewById(R.id.stop_minute_picker);
        buttonsLayout = findViewById(R.id.date_buttons);
        pickersLayout = findViewById(R.id.date_pickers);

        paramsButtons = buttonsLayout.getLayoutParams();
        paramsPickers =  pickersLayout.getLayoutParams();
        setupDate();
    }

    private void setupDate() {
        setTimePicker(MainActivity.valueHolder.isDatePickerButton());

        // Number pickers

        String[] displayHours = new String[24];
        String[] displayMinutes = {0+"0", String.valueOf(10), String.valueOf(20), String.valueOf(30), String.valueOf(40), String.valueOf(50)};
        for (int h = 0; h < displayHours.length; h++) {
            displayHours[h] = f.z(h);
        }
        startHourPicker.setMinValue(0);
        startHourPicker.setMaxValue(23);
        startHourPicker.setDisplayedValues(displayHours);
        startMinutePicker.setMinValue(0);
        startMinutePicker.setMaxValue(5);
        startMinutePicker.setValue(0);
        startMinutePicker.setDisplayedValues(displayMinutes);
        stopHourPicker.setMinValue(0);
        stopHourPicker.setMaxValue(23);
        stopHourPicker.setDisplayedValues(displayHours);
        stopMinutePicker.setMinValue(0);
        stopMinutePicker.setMaxValue(5);
        startMinutePicker.setValue(0);
        stopMinutePicker.setDisplayedValues(displayMinutes);
        startHourPicker.setValue(start.get(Calendar.HOUR_OF_DAY));
        stopHourPicker.setValue(stop.get(Calendar.HOUR_OF_DAY));
        startMinutePicker.setValue(Math.round(start.get(Calendar.MINUTE)/10));
        stopMinutePicker.setValue(Math.round(stop.get(Calendar.MINUTE)/10));
        stop.set(Calendar.MINUTE, stopMinutePicker.getValue()*10);


        startHourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {


                start.set(Calendar.HOUR_OF_DAY, startHourPicker.getValue());
            }
        });

        startMinutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                start.set(Calendar.MINUTE, startMinutePicker.getValue()*10);
            }
        });

        stopHourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                stop.set(Calendar.HOUR_OF_DAY, stopHourPicker.getValue());
                if(stop.get(Calendar.HOUR_OF_DAY) == 0 && stop.get(Calendar.HOUR_OF_DAY)==0){
                    stop.set(Calendar.HOUR_OF_DAY, 23);
                    stop.set(Calendar.MINUTE, 59);
                }
            }
        });

        stopMinutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                stop.set(Calendar.MINUTE, stopMinutePicker.getValue()*10);
                if(stop.get(Calendar.HOUR_OF_DAY) == 0 && stop.get(Calendar.HOUR_OF_DAY)==0){
                    stop.set(Calendar.HOUR_OF_DAY, 23);
                    stop.set(Calendar.MINUTE, 59);
                }
            }
        });

    }

    private void setValues() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        selectedRoutine = new Routine();
        if (bundle!=null) {
            routineID=(int) bundle.get("ID");
            selectedRoutine = MainActivity.appDatabase.appDao().idRoutine(routineID);
            name=selectedRoutine.getName();
            nameText.setText(name);
            icon=selectedRoutine.getIcon();
            color=selectedRoutine.getColor();
            start=selectedRoutine.getStart();
            btnStartHour.setText(f.Time(start));
            stop=selectedRoutine.getStop();
            btnStopHour.setText(f.Time(stop));
            monday=selectedRoutine.isMonday();
            CheckboxMonday.setChecked(monday);
            tuesday=selectedRoutine.isTuesday();
            CheckboxTuesday.setChecked(tuesday);
            wednesday=selectedRoutine.isWednesday();
            CheckboxWednesday.setChecked(wednesday);
            thursday=selectedRoutine.isThursday();
            CheckboxThursday.setChecked(thursday);
            friday=selectedRoutine.isFriday();
            CheckboxFriday.setChecked(friday);
            saturday=selectedRoutine.isSaturday();
            CheckboxSaturday.setChecked(saturday);
            sunday=selectedRoutine.isSunday();
            CheckboxSunday.setChecked(sunday);
        }


    }

    private void setButtons() {
        imageConfirm = findViewById(R.id.toolbar_confirm);
        imageConfirm.setOnClickListener(this);
        btnStartHour = findViewById(R.id.input_routine_start);
        btnStartHour.setOnClickListener(this);
        btnStopHour = findViewById(R.id.input_routine_stop);
        btnStopHour.setOnClickListener(this);
        findViewById(R.id.icon_view).setOnClickListener(this);
        imageCancel = findViewById(R.id.toolbar_cancel);
        imageCancel.setOnClickListener(this);
        imageDelete = findViewById(R.id.toolbar_delete);
        imageDelete.setVisibility(View.VISIBLE);
        imageDelete.setOnClickListener(this);
        findViewById(R.id.color_view).setOnClickListener(this);

    }

    private void setupCheckboxes() {
        CheckboxMonday = findViewById(R.id.check_monday);
        CheckboxMonday.setOnCheckedChangeListener(this);
        CheckboxTuesday = findViewById(R.id.check_tuesday);
        CheckboxTuesday.setOnCheckedChangeListener(this);
        CheckboxWednesday = findViewById(R.id.check_wednesday);
        CheckboxWednesday.setOnCheckedChangeListener(this);
        CheckboxThursday = findViewById(R.id.check_thursday);
        CheckboxThursday.setOnCheckedChangeListener(this);
        CheckboxFriday = findViewById(R.id.check_friday);
        CheckboxFriday.setOnCheckedChangeListener(this);
        CheckboxSaturday = findViewById(R.id.check_saturday);
        CheckboxSaturday.setOnCheckedChangeListener(this);
        CheckboxSunday = findViewById(R.id.check_sunday);
        CheckboxSunday.setOnCheckedChangeListener(this);
        CheckboxWorkDays = findViewById(R.id.check_work_days);
        CheckboxWorkDays.setOnCheckedChangeListener(this);
        CheckboxWeekends = findViewById(R.id.check_weekends);
        CheckboxWeekends.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.check_monday:
                monday = b;
                checkWorkDays();
                break;
            case R.id.check_tuesday:
                tuesday = b;
                checkWorkDays();
                break;
            case R.id.check_wednesday:
                wednesday = b;
                checkWorkDays();
                break;
            case R.id.check_thursday:
                thursday = b;
                checkWorkDays();
                break;
            case R.id.check_friday:
                friday = b;
                checkWorkDays();
                break;
            case R.id.check_saturday:
                saturday = b;
                checkWeekends();
                break;
            case R.id.check_sunday:
                sunday = b;
                checkWeekends();
                break;
            case R.id.check_work_days:
                workDays = b;
                if(b) {
                    monday = b;
                    tuesday = b;
                    wednesday = b;
                    thursday = b;
                    friday = b;
                    CheckboxMonday.setChecked(b);
                    CheckboxTuesday.setChecked(b);
                    CheckboxWednesday.setChecked(b);
                    CheckboxThursday.setChecked(b);
                    CheckboxFriday.setChecked(b);
                } else {
                    if(monday && tuesday && wednesday && thursday && friday) {
                        monday = b;
                        tuesday = b;
                        wednesday = b;
                        thursday = b;
                        friday = b;
                        CheckboxMonday.setChecked(b);
                        CheckboxTuesday.setChecked(b);
                        CheckboxWednesday.setChecked(b);
                        CheckboxThursday.setChecked(b);
                        CheckboxFriday.setChecked(b);
                    }
                    else {
                        CheckboxMonday.setChecked(monday);
                        CheckboxTuesday.setChecked(tuesday);
                        CheckboxWednesday.setChecked(wednesday);
                        CheckboxThursday.setChecked(thursday);
                        CheckboxFriday.setChecked(friday);
                    }
                }

                break;
            case R.id.check_weekends:
                weekends = b;
                if(b) {
                    sunday = b;
                    saturday = b;
                    CheckboxSaturday.setChecked(b);
                    CheckboxSunday.setChecked(b);
                } else {
                    if(sunday && saturday) {
                        sunday = b;
                        saturday = b;
                        CheckboxSaturday.setChecked(b);
                        CheckboxSunday.setChecked(b);
                    }
                    else {
                        CheckboxSaturday.setChecked(saturday);
                        CheckboxSunday.setChecked(sunday);
                    }
                }

                break;



        }
    }

    private void checkWorkDays() {
        if(monday && tuesday && wednesday && thursday && friday) {
            CheckboxWorkDays.setChecked(true);
        } else
        {
            CheckboxWorkDays.setChecked(false);
        }
    }

    private void checkWeekends() {
        if(saturday && sunday) {
            CheckboxWeekends.setChecked(true);
        } else CheckboxWeekends.setChecked(false);
    }

    @Override
    public void onClick(View view) {
        clickColor(view.getId());
        clickIcon(view.getId());
        switch (view.getId()) {
            case R.id.toolbar_confirm:
                boolean isOK = true;
                name = String.valueOf(nameText.getText());
                int newID = MainActivity.appDatabase.appDao().getMaxRoutinesID();

                if(monday) {
                    List<Routine> routines = MainActivity.appDatabase.appDao().getMonday();
                    for (int r= 0; r<routines.size(); r++ ) {
                        if(checker.TimeCollision(start, stop, routines.get(r).getStart(), routines.get(r).getStop()) && routineID != routines.get(r).getId()){
                            isOK = false;
                        }
                    }
                }
                if(tuesday) {
                    List<Routine> routines = MainActivity.appDatabase.appDao().getTuesday();
                    for (int r= 0; r<routines.size(); r++ ) {
                        if(checker.TimeCollision(start, stop, routines.get(r).getStart(), routines.get(r).getStop()) && routineID != routines.get(r).getId()){
                            isOK = false;
                        }
                    }
                }
                if(wednesday) {
                    List<Routine> routines = MainActivity.appDatabase.appDao().getWednesday();
                    for (int r= 0; r<routines.size(); r++ ) {
                        if(checker.TimeCollision(start, stop, routines.get(r).getStart(), routines.get(r).getStop()) && routineID != routines.get(r).getId()){
                            isOK = false;
                        }
                    }
                }
                if(thursday) {
                    List<Routine> routines = MainActivity.appDatabase.appDao().getThursday();
                    for (int r= 0; r<routines.size(); r++ ) {
                        if(checker.TimeCollision(start, stop, routines.get(r).getStart(), routines.get(r).getStop()) && routineID != routines.get(r).getId()){
                            isOK = false;
                        }
                    }
                }
                if(friday) {
                    List<Routine> routines = MainActivity.appDatabase.appDao().getFriday();
                    for (int r= 0; r<routines.size(); r++ ) {
                        if(checker.TimeCollision(start, stop, routines.get(r).getStart(), routines.get(r).getStop()) && routineID != routines.get(r).getId()){
                            isOK = false;
                        }
                    }
                }
                if(saturday) {
                    List<Routine> routines = MainActivity.appDatabase.appDao().getSaturday();
                    for (int r= 0; r<routines.size(); r++ ) {
                        if(checker.TimeCollision(start, stop, routines.get(r).getStart(), routines.get(r).getStop()) && routineID != routines.get(r).getId()){
                            isOK = false;
                        }
                    }
                }
                if(sunday) {
                    List<Routine> routines = MainActivity.appDatabase.appDao().getSunday();
                    for (int r= 0; r<routines.size(); r++ ) {
                        if(checker.TimeCollision(start, stop, routines.get(r).getStart(), routines.get(r).getStop()) && routineID != routines.get(r).getId()){
                            isOK = false;
                        }
                    }
                }
                if(isOK) {
                    Routine newRoutine = new Routine(routineID, name, icon, color, start, stop, monday, tuesday, wednesday ,thursday, friday, saturday, sunday);
                    MainActivity.appDatabase.appDao().updateRoutine(newRoutine);
                    MainActivity.fDatabase.AddRoutine(newRoutine);
                    refresh();
                    finish();
                } else Toast.makeText(getApplicationContext(), R.string.routine_cant_interfere, Toast.LENGTH_LONG).show();


                break;

            case R.id.input_routine_start:
                tpd = new TimePickerDialog(EditRoutineActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        start.set(Calendar.HOUR_OF_DAY, hour);
                        start.set(Calendar.MINUTE, minute);
                        btnStartHour.setText(f.Time(start));
                    }
                }, 0, 0 , true);
                tpd.show();
                break;

            case R.id.input_routine_stop:
                tpd = new TimePickerDialog(EditRoutineActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        if(hour == 0 && minute == 0) {
                            stop.set(Calendar.HOUR_OF_DAY, 23);
                            stop.set(Calendar.MINUTE, 59);
                        } else {
                            stop.set(Calendar.HOUR_OF_DAY, hour);
                            stop.set(Calendar.MINUTE, minute);
                        }
                        btnStopHour.setText(f.Time(stop));
                    }
                }, 0, 0 , true);
                tpd.show();
                break;

            case R.id.toolbar_cancel:
                finish();
                break;

            case R.id.toolbar_delete:
                MainActivity.appDatabase.appDao().deleteRoutine(selectedRoutine.getId());
                refresh();
                finish();
                Toast.makeText(getApplicationContext(), R.string.routine_deleted, Toast.LENGTH_LONG).show();
                break;


        }
        imageIcon.setImageDrawable(getDrawable(icon));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);
        // The returned result data is identified by requestCode.
        // The request code is specified in startActivityForResult(intent, REQUEST_CODE_1); method.
        switch (requestCode)
        {
            // This request code is set by startActivityForResult(intent, REQUEST_CODE_1) method.
            case 1:
                if(resultCode == RESULT_OK)
                {
                    String messageReturn = dataIntent.getStringExtra("message_return");
                    //icon = Integer.parseInt(messageReturn);
                    icon=MainActivity.icons[Integer.parseInt(messageReturn)];

                }
                break;
        }
    }

    private void refresh(){
        MainActivity.needRefresh = true;
        Intent intent = new Intent();
        intent.putExtra("message_return", "refresh");
        setResult(RESULT_OK, intent);
    }

    private void clickColor(int id) {
        switch (id) {

            case R.id.color_view:
                d2 = new Dialog(EditRoutineActivity.this);
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
                color = MainActivity.colors[0];
                setColor();
                d2.dismiss();
                break;
            case R.id.color_button1:
                color = MainActivity.colors[1];
                setColor();
                d2.dismiss();
                break;
            case R.id.color_button2:
                color = MainActivity.colors[2];
                setColor();
                d2.dismiss();
                break;
            case R.id.color_button3:
                color = MainActivity.colors[3];
                setColor();
                d2.dismiss();
                break;
            case R.id.color_button4:
                color = MainActivity.colors[4];
                setColor();
                d2.dismiss();
                break;
            case R.id.color_button5:
                color = MainActivity.colors[5];
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
        if(MainActivity.valueHolder.isPremiumUser() || MainActivity.valueHolder.getAdsPremiumActive()){
            color = MainActivity.colors[premiumColor];
            //imageColor.setBackgroundColor(color);
            int[] ints = {0};
            int[][] all = {ints};
            int[] colors = {color};
            imageColor.setBackgroundTintList(new ColorStateList(all,colors));
            d2.dismiss();
        } else {
            if (MainActivity.valueHolder.getAdsPremium()){
                Intent adPremiumIntent = new Intent(this, WatchPremiumAdActivity.class);
                startActivity(adPremiumIntent);
            }else {
                Intent premiumIntent = new Intent(this, BuyPremiumActivity.class);
                premiumIntent.putExtra("messageID", 4);
                startActivity(premiumIntent);
            }
        }
    }

    private void setColor() {
        int[] ints = {0};
        int[][] all = {ints};
        int[] colors = {color};
        imageColor.setBackgroundTintList(new ColorStateList(all,colors));
    }

    private void clickIcon(int id) {
        switch (id){
            case R.id.icon_view:
                d1 = new Dialog(EditRoutineActivity.this);
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
                icon = 0;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
            case R.id.image_button2:
                d1.dismiss();
                icon = 1;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
            case R.id.image_button3:
                d1.dismiss();
                icon = 2;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
            case R.id.image_button4:
                d1.dismiss();
                icon = 3;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
            case R.id.image_button5:
                d1.dismiss();
                icon = 4;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
            case R.id.image_button6:
                d1.dismiss();
                icon = 5;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
            case R.id.image_button7:
                d1.dismiss();
                icon = 6;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
            case R.id.image_button8:
                d1.dismiss();
                icon = 7;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
            case R.id.image_button9:
                d1.dismiss();
                icon = 8;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
            case R.id.image_button10:
                d1.dismiss();
                icon = 9;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
            case R.id.image_button11:
                d1.dismiss();
                icon = 10;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
            case R.id.image_button12:
                d1.dismiss();
                icon = 11;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
            case R.id.image_button13:
                d1.dismiss();
                icon = 12;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
            case R.id.image_button14:
                d1.dismiss();
                icon = 13;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
            case R.id.image_button15:
                d1.dismiss();
                icon = 14;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
            case R.id.image_button16:
                d1.dismiss();
                icon = 15;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
            case R.id.image_button17:
                d1.dismiss();
                icon = 16;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
            case R.id.image_button18:
                d1.dismiss();
                icon = 17;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
            case R.id.image_button19:
                d1.dismiss();
                icon = 18;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
            case R.id.image_button20:
                d1.dismiss();
                icon = 19;
                icon = MainActivity.icons[icon];
                imageIcon.setImageDrawable(getDrawable(icon));
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
        if(MainActivity.valueHolder.isPremiumUser() || MainActivity.valueHolder.getAdsPremiumActive()){
            icon = MainActivity.icons[premiumIcon];
            d1.dismiss();
            imageIcon.setImageDrawable(getDrawable(icon));
        } else {
            if (MainActivity.valueHolder.getAdsPremium()){
                Intent adPremiumIntent = new Intent(this, WatchPremiumAdActivity.class);
                startActivity(adPremiumIntent);
            }else {
                Intent premiumIntent = new Intent(this, BuyPremiumActivity.class);
                premiumIntent.putExtra("messageID", 3);
                startActivity(premiumIntent);
            }
        }
    }

    private void setTimePicker(boolean buttonPicker) {
        if(!buttonPicker) {

            //dateLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(0,0));
            buttonsLayout.setVisibility(View.INVISIBLE);
            //dateRelativeLayout.setLayoutParams(paramsRelative);
            pickersLayout.setVisibility(View.VISIBLE);
        } else {
            buttonsLayout.setLayoutParams(paramsButtons);
            buttonsLayout.setVisibility(View.VISIBLE);
            pickersLayout.setLayoutParams(new RelativeLayout.LayoutParams(0,0));
            pickersLayout.setVisibility(View.INVISIBLE);
        }
    }

}