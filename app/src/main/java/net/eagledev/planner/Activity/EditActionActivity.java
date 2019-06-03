package net.eagledev.planner.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import net.eagledev.planner.Action;
import net.eagledev.planner.Adapter.CustomSpinnerAdapter;
import net.eagledev.planner.BuyPremiumActivity;
import net.eagledev.planner.Checker;
import net.eagledev.planner.Formatter;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;
import net.eagledev.planner.WatchPremiumAdActivity;

import java.util.Calendar;

public class EditActionActivity extends Activity implements View.OnClickListener
{

        Calendar c;
        DatePickerDialog dpd;
        TimePickerDialog tpd;
        Button dateActionStartButton;
        Button dateActionStopButton;
        int iconID;
        int premiumIcon;
        int colorID;
        int premiumColor;
        TextView textView;
        boolean checked = false;
        Checker checker;

        Calendar date_start;
        Calendar date_stop;

        ImageView btn_select_icon;
        Button btn_date;
        int aDay;
        int aMonth;
        int aYear;
        CustomSpinnerAdapter customSpinnerAdapter;
        int images[] = {R.drawable.color_red, R.drawable.color_green, R.drawable.color_blue, R.drawable.color_gray, R.drawable.color_yellow, R.drawable.color_gray};
        String names[] = {"red", "green", "blue", "gray", "yellow", "huj wi"};

        //int icons[] = {R.drawable.baseline_account_balance_black_18dp, R.drawable.baseline_build_black_18dp, R.drawable.baseline_favorite_border_black_18dp, R.drawable.baseline_headset_black_18dp, R.drawable.baseline_weekend_black_18dp};

        private final static int REQUEST_CODE_1 = 1;

        int actionID;
        Action selectedAction;
        String desc = "";
        ImageView imageDelete;
        Formatter f = new Formatter();

        Dialog d1;
        Dialog d2;

        int year = 0;
        int month = 0;
        int day = 0;
        int startHour = 0;
        int startMinute = 0;
        int stopHour = 0;
        int stopMinute = 0;

    ImageView imageConfirm;
    ImageView imageCancel;
    ImageView imageIcon;
    ImageView imageColor;

    NumberPicker startHourPicker;
    NumberPicker startMinutePicker;
    NumberPicker stopHourPicker;
    NumberPicker stopMinutePicker;
    LinearLayout dateLinearLayout;
    RelativeLayout dateRelativeLayout;
    ViewGroup.LayoutParams paramsLinear;
    ViewGroup.LayoutParams paramsRelative;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_action);
            textView = findViewById(R.id.input_action_name);
            setupObjects();
            date_start = Calendar.getInstance();
            date_stop =  Calendar.getInstance();
            setIcon();
            checker = new Checker();
            SetValues();
            SetupDate();
            setListeners();
            imageDelete = findViewById(R.id.toolbar_delete);
            imageDelete.setVisibility(View.VISIBLE);
            imageDelete.setOnClickListener(this);
            imageIcon = findViewById(R.id.icon_view);
            imageIcon.setImageDrawable(getDrawable(iconID));
            imageColor = findViewById(R.id.color_view);
            int[] ints = {0};
            int[][] all = {ints};
            int[] colors = {colorID};
            imageColor.setBackgroundTintList(new ColorStateList(all,colors));
        }

        private void SetValues() {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            selectedAction = new Action();
            if(bundle!=null) {
                actionID = (int) bundle.get("ID");
                selectedAction = MainActivity.appDatabase.appDao().idAction(actionID);
                if(selectedAction == null){
                    finish();
                }
                findViewById(R.id.color_view).setOnClickListener(this);
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

            }
        }

        private void SetupDate() {
            c = Calendar.getInstance();
            final int day = c.get(Calendar.DAY_OF_MONTH);
            final int month = c.get(Calendar.MONTH);
            final int year = c.get(Calendar.YEAR);
            final int hour = c.get(Calendar.HOUR_OF_DAY)+1;
            final int min = c.get(Calendar.MINUTE);

            setTimePicker(MainActivity.valueHolder.isDatePickerButton());
            // Number pickers

            String[] displayHours = new String[24];
            String[] displayMinutes = {00+"", String.valueOf(10), String.valueOf(20), String.valueOf(30), String.valueOf(40), String.valueOf(50)};
            for (int h = 0; h < displayHours.length; h++) {
                displayHours[h] = f.z(h);
            }
            startHourPicker.setMinValue(0);
            startHourPicker.setMaxValue(23);
            startHourPicker.setDisplayedValues(displayHours);
            startMinutePicker.setMinValue(0);
            startMinutePicker.setMaxValue(5);
            startMinutePicker.setDisplayedValues(displayMinutes);
            stopHourPicker.setMinValue(0);
            stopHourPicker.setMaxValue(23);
            stopHourPicker.setDisplayedValues(displayHours);
            stopMinutePicker.setMinValue(0);
            stopMinutePicker.setMaxValue(5);
            stopMinutePicker.setDisplayedValues(displayMinutes);



            startHourPicker.setValue(startHour);
            startHourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                    date_start.set(Calendar.HOUR_OF_DAY, startHourPicker.getValue());
                }
            });

            startMinutePicker.setValue(minute(startMinute));
            startMinutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    date_start.set(Calendar.MINUTE, startMinutePicker.getValue()*10);
                }
            });

            stopHourPicker.setValue(stopHour);
            stopHourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    date_stop.set(Calendar.HOUR_OF_DAY, stopHourPicker.getValue());
                    if(date_stop.get(Calendar.HOUR_OF_DAY) == 0 && date_stop.get(Calendar.HOUR_OF_DAY)==0){
                        date_stop.set(Calendar.HOUR_OF_DAY, 23);
                        date_stop.set(Calendar.MINUTE, 59);
                    }
                }
            });

            stopMinutePicker.setValue(minute(stopMinute));
            stopMinutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    date_stop.set(Calendar.MINUTE, stopMinutePicker.getValue()*10);
                    if(date_stop.get(Calendar.HOUR_OF_DAY) == 0 && date_stop.get(Calendar.HOUR_OF_DAY)==0){
                        date_stop.set(Calendar.HOUR_OF_DAY, 23);
                        date_stop.set(Calendar.MINUTE, 59);
                    }
                }
            });

            // Buttons

            btn_date = findViewById(R.id.btn_date);
            btn_date.setText(f.DateText(date_start));
            aDay = day;
            aMonth = month;
            aYear = year;
            btn_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dpd = new DatePickerDialog(EditActionActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, final int mYear, final int mMonth, final int mDay) {
                            btn_date.setText(mDay+"." + mMonth + "."+mYear);
                            aDay = mDay;
                            aMonth = mMonth;
                            aYear = mYear;
                            date_start.set(Calendar.YEAR, mYear);
                            date_start.set(Calendar.MONTH, mMonth);
                            date_start.set(Calendar.DAY_OF_MONTH, mDay);
                            date_stop.set(Calendar.YEAR, mYear);
                            date_stop.set(Calendar.MONTH, mMonth);
                            date_stop.set(Calendar.DAY_OF_MONTH, mDay);
                        }
                    },year, month , day);
                    dpd.show();
                }
            });
            dateActionStartButton = (Button) findViewById(R.id.input_action_date_start);
            dateActionStartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tpd = new TimePickerDialog(EditActionActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int mHour, int mMinute) {

                            date_start.set(Calendar.HOUR_OF_DAY, mHour);
                            date_start.set(Calendar.MINUTE, mMinute);
                            dateActionStartButton.setText(f.Time(date_start) );
                        }
                    }, hour, 0, true);
                    tpd.show();
                }
            });

            dateActionStopButton = (Button) findViewById(R.id.input_action_date_stop);
            dateActionStopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    tpd = new TimePickerDialog(EditActionActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int mHour, int mMinute) {
                            if(mHour == 0 && mMinute == 0) {
                                date_stop.set(Calendar.HOUR_OF_DAY, 23);
                                date_stop.set(Calendar.MINUTE, 59);
                            } else {
                                date_stop.set(Calendar.HOUR_OF_DAY, mHour);
                                date_stop.set(Calendar.MINUTE, mMinute);
                            }
                            dateActionStopButton.setText(f.Time(date_stop) );
                        }
                    }, date_start.get(Calendar.HOUR_OF_DAY)+1, 0, true);
                    tpd.show();
                }
            });
            btn_date.setText(f.DateText(date_start));
            dateActionStartButton.setText(f.z(startHour) + ":" + f.z(startMinute) );
            dateActionStopButton.setText(f.z(stopHour) + ":" + f.z(stopMinute) );
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
                        //iconID = icons[Integer.parseInt(messageReturn)];

                    }
            }
        }

        private void CreateAction() {
            Action newAction = new Action(selectedAction.getId(),textView.getText().toString(), date_start, date_stop, iconID, colorID );
            MainActivity.appDatabase.appDao().updateAction(newAction);
            MainActivity.fDatabase.AddAction(newAction);

        }

        private void setIcon() {
            btn_select_icon = (ImageView) findViewById(R.id.icon_view);
            btn_select_icon.setOnClickListener(this);
        }


        private void setListeners() {

            imageCancel = findViewById(R.id.toolbar_cancel);
            imageCancel.setOnClickListener(this);
            imageConfirm = findViewById(R.id.toolbar_confirm);
            imageConfirm.setOnClickListener(this);
            findViewById(R.id.btn_date_left).setOnClickListener(this);
            findViewById(R.id.btn_date_right).setOnClickListener(this);

        }


    @Override
    public void onClick(View view) {
            clickColor(view.getId());
            clickIcon(view.getId());

        switch (view.getId()) {
            case R.id.toolbar_confirm:
                confirm();
                break;
            case R.id.toolbar_cancel:
                finish();
                break;

            case R.id.toolbar_delete:
                MainActivity.appDatabase.appDao().deleteAction(selectedAction.getId());
                refresh();
                finish();
                Toast.makeText(getApplicationContext(), R.string.action_deleted, Toast.LENGTH_LONG).show();
                break;

            case R.id.btn_date_left:
                date_start.add(Calendar.DATE, -1);
                date_stop.add(Calendar.DATE, -1);
                btn_date.setText(f.Date(date_start));
                break;

            case R.id.btn_date_right:
                date_start.add(Calendar.DATE, 1);
                date_stop.add(Calendar.DATE, 1);
                btn_date.setText(f.Date(date_start));
                break;
        }


    }

    private void clickColor(int id) {
        switch (id) {

            case R.id.color_view:
                d2 = new Dialog(EditActionActivity.this);
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
        if(MainActivity.valueHolder.isPremiumUser() || MainActivity.valueHolder.getAdsPremiumActive()){
            colorID = MainActivity.colors[premiumColor];
            int[] ints = {0};
            int[][] all = {ints};
            int[] colors = {colorID};
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
        int[] colors = {colorID};
        imageColor.setBackgroundTintList(new ColorStateList(all,colors));
    }

    private void clickIcon(int id) {
        switch (id){
            case R.id.icon_view:
                d1 = new Dialog(EditActionActivity.this);
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
        if(MainActivity.valueHolder.isPremiumUser() || MainActivity.valueHolder.getAdsPremiumActive()){
            iconID = MainActivity.icons[premiumIcon];
            d1.dismiss();
            imageIcon.setImageDrawable(getDrawable(iconID));
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

    private void confirm() {
        if(MainActivity.valueHolder.isPremiumUser() || MainActivity.valueHolder.getAdsPremiumActive()) {
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
            CreateAction();
            refresh();
            finish();
        }
    }

    private void refresh(){
        MainActivity.needRefresh = true;
        Intent intent = new Intent();
        intent.putExtra("message_return", "refresh");
        setResult(RESULT_OK, intent);
    }

    private void setTimePicker(boolean buttonPicker) {
        if(!buttonPicker) {

            //dateLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(0,0));
            dateLinearLayout.setVisibility(View.INVISIBLE);
            //dateRelativeLayout.setLayoutParams(paramsRelative);
            dateRelativeLayout.setVisibility(View.VISIBLE);
        } else {
            dateLinearLayout.setLayoutParams(paramsLinear);
            dateLinearLayout.setVisibility(View.VISIBLE);
            dateRelativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(0,0));
            dateRelativeLayout.setVisibility(View.INVISIBLE);
        }
    }

    private void setupObjects() {


        startHourPicker = findViewById(R.id.start_hour_picker);
        startMinutePicker = findViewById(R.id.start_minute_picker);
        stopHourPicker = findViewById(R.id.stop_hour_picker);
        stopMinutePicker = findViewById(R.id.stop_minute_picker);
        dateLinearLayout = findViewById(R.id.date_linear);
        dateRelativeLayout = findViewById(R.id.date_relative);

        paramsLinear = dateLinearLayout.getLayoutParams();
        paramsRelative =  dateRelativeLayout.getLayoutParams();

    }
    int minute(int l) {

            if(l < 5) return 0;
            if(l <15) return 1;
        if(l <25) return 2;
        if(l <35) return 3;
        if(l <45) return 4;
        if(l <55) return 5;
        return 0;

    }
}
