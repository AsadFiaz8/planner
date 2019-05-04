package net.eagledev.planner.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import net.eagledev.planner.BuyPremiumActivity;
import net.eagledev.planner.Checker;
import net.eagledev.planner.Formatter;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;
import net.eagledev.planner.WatchPremiumAdActivity;

import java.util.Calendar;
import java.util.List;

public class AddActionAtivity extends Activity  implements View.OnClickListener{

    Calendar c;
    DatePickerDialog dpd;
    TimePickerDialog tpd;
    Button dateActionStartButton;
    Button dateActionStopButton;
    NumberPicker startHourPicker;
    NumberPicker startMinutePicker;
    NumberPicker stopHourPicker;
    NumberPicker stopMinutePicker;
    int iconID;
    int premiumIcon;
    int colorID;
    int premiumColor;
    TextView textView;
    boolean checked = false;
    Calendar date_start;
    Calendar date_stop;
    Formatter f = new Formatter();

    Button btn_select_icon;
    Button btn_date;
    int aDay;
    int aMonth;
    int aYear;


    private final static int REQUEST_CODE_1 = 1;

    ImageView imageConfirm;
    ImageView imageCancel;
    ImageView imageIcon;
    ImageView imageColor;
    Dialog d1;
    Dialog d2;
    LinearLayout dateLinearLayout;
    RelativeLayout dateRelativeLayout;
    int lWindth;
    int lHeinght;
    int rWindth;
    int rHeight;
    ViewGroup.LayoutParams paramsLinear;
    ViewGroup.LayoutParams paramsRelative;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_action);
        btn_select_icon = (Button) findViewById(R.id.btn_select_icon);
        btn_select_icon.setOnClickListener(this);
        findViewById(R.id.btn_select_color).setOnClickListener(this);
        textView = findViewById(R.id.input_action_name);
        findViewById(R.id.btn_date_left).setOnClickListener(this);
        findViewById(R.id.btn_date_right).setOnClickListener(this);

        imageIcon = findViewById(R.id.icon_view);
        date_start = Calendar.getInstance();
        date_stop = Calendar.getInstance();
        iconID = MainActivity.icons[0];
        imageCancel = findViewById(R.id.toolbar_cancel);
        imageCancel.setOnClickListener(this);
        imageConfirm = findViewById(R.id.toolbar_confirm);
        imageConfirm.setOnClickListener(this);
        imageColor = findViewById(R.id.color_view);
        imageColor.setBackgroundColor(MainActivity.colors[0]);

        startHourPicker = findViewById(R.id.start_hour_picker);
        startMinutePicker = findViewById(R.id.start_minute_picker);
        stopHourPicker = findViewById(R.id.stop_hour_picker);
        stopMinutePicker = findViewById(R.id.stop_minute_picker);
        dateLinearLayout = findViewById(R.id.date_linear);
        dateRelativeLayout = findViewById(R.id.date_relative);

        paramsLinear = dateLinearLayout.getLayoutParams();
        paramsRelative =  dateRelativeLayout.getLayoutParams();





        SetupDate();



    }

    @Override
    public void onClick(View view) {
        clickIcon(view.getId());
        clickColor(view.getId());
        switch (view.getId()) {
            case R.id.toolbar_cancel:
                finish();
                break;
            case R.id.toolbar_confirm:
                confirm();
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

            case R.id.btn_select_color:
                d2 = new Dialog(AddActionAtivity.this);
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
                imageColor.setBackgroundColor(colorID);
                d2.dismiss();
                break;
            case R.id.color_button1:
                colorID = MainActivity.colors[1];
                imageColor.setBackgroundColor(colorID);
                d2.dismiss();
                break;
            case R.id.color_button2:
                colorID = MainActivity.colors[2];
                imageColor.setBackgroundColor(colorID);
                d2.dismiss();
                break;
            case R.id.color_button3:
                colorID = MainActivity.colors[3];
                imageColor.setBackgroundColor(colorID);
                d2.dismiss();
                break;
            case R.id.color_button4:
                colorID = MainActivity.colors[4];
                imageColor.setBackgroundColor(colorID);
                d2.dismiss();
                break;
            case R.id.color_button5:
                colorID = MainActivity.colors[5];
                imageColor.setBackgroundColor(colorID);
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
            imageColor.setBackgroundColor(colorID);
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

    private void clickIcon(int id) {
        switch (id){
            case R.id.btn_select_icon:
                d1 = new Dialog(AddActionAtivity.this);
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

    private void SetupDate() {
        c = Calendar.getInstance();
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int month = c.get(Calendar.MONTH);
        final int year = c.get(Calendar.YEAR);
        final int hour = c.get(Calendar.HOUR_OF_DAY)+1;
        final int min = c.get(Calendar.MINUTE);
        colorID = MainActivity.colors[0];

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
        startMinutePicker.setDisplayedValues(displayMinutes);
        stopHourPicker.setMinValue(0);
        stopHourPicker.setMaxValue(23);
        stopHourPicker.setDisplayedValues(displayHours);
        stopMinutePicker.setMinValue(0);
        stopMinutePicker.setMaxValue(5);
        stopMinutePicker.setDisplayedValues(displayMinutes);


        startHourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                Log.e("start Hour", String.valueOf(startHourPicker.getValue()));
                date_start.set(Calendar.HOUR_OF_DAY, startHourPicker.getValue());
            }
        });

        startMinutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.e("start Minute", String.valueOf(startMinutePicker.getValue()));
                date_start.set(Calendar.MINUTE, startMinutePicker.getValue()*10);
            }
        });

        stopHourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.e("stop Hour", String.valueOf(stopHourPicker.getValue()));
                date_stop.set(Calendar.HOUR_OF_DAY, stopHourPicker.getValue());
            }
        });

        stopMinutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.e("stop Minute", String.valueOf(stopMinutePicker.getValue()));
                date_stop.set(Calendar.MINUTE, stopMinutePicker.getValue()*10);
            }
        });

        // Buttons
        btn_date = findViewById(R.id.btn_date);
        btn_date.setText(f.Date(c));
        aDay = day;
        aMonth = month;
        aYear = year;
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_date.setTextColor(getResources().getColor(R.color.colorAccent));
                dpd = new DatePickerDialog(AddActionAtivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, final int mYear, final int mMonth, final int mDay) {
                        aDay = mDay;
                        aMonth = mMonth;
                        aYear = mYear;
                        date_start.set(Calendar.YEAR, mYear);
                        date_start.set(Calendar.MONTH, mMonth);
                        date_start.set(Calendar.DAY_OF_MONTH, mDay);
                        btn_date.setText(f.Date(date_start));
                        date_stop.set(Calendar.YEAR, mYear);
                        date_stop.set(Calendar.MONTH, mMonth);
                        date_stop.set(Calendar.DAY_OF_MONTH, mDay);

                    }
                },year, month , day);
                dpd.show();
                btn_date.setTextColor(getResources().getColor(R.color.white));
            }
        });
        dateActionStartButton = (Button) findViewById(R.id.input_action_date_start);
        dateActionStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        tpd = new TimePickerDialog(AddActionAtivity.this, new TimePickerDialog.OnTimeSetListener() {
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

                        tpd = new TimePickerDialog(AddActionAtivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int mHour, int mMinute) {

                                if(mHour == 0 && mMinute == 0) {
                                    date_stop.set(Calendar.HOUR_OF_DAY, 23);
                                    date_stop.set(Calendar.MINUTE, 59);
                                } else {
                                    date_stop.set(Calendar.HOUR_OF_DAY, mHour);
                                    date_stop.set(Calendar.MINUTE, mMinute);
                                }
                                dateActionStopButton.setText(f.Time(date_stop));
                            }
                        }, date_start.get(Calendar.HOUR_OF_DAY)+1, 0, true);
                        tpd.show();
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
    }

    private void confirm() {
        Checker checker = new Checker();


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

            List<Action> actionsFromDay = MainActivity.appDatabase.appDao().getActionsFromDay(date_start.get(Calendar.DAY_OF_MONTH), date_start.get(Calendar.MONTH), date_start.get(Calendar.YEAR));
            int actionsCount = actionsFromDay.size();
            Log.e("actionsCount", String.valueOf(actionsCount) + "   " + getResources().getInteger(R.integer.premium_max_actions_one_day));

            if(actionsCount < getResources().getInteger(R.integer.premium_max_actions_one_day) || MainActivity.valueHolder.isPremiumUser() || MainActivity.valueHolder.getAdsPremiumActive()) {
                //Spełnia warunki lub jest premium lub premium reklamowe
                CreateAction();
                MainActivity.needRefresh = true;
                finish();
            } else {
                if(MainActivity.valueHolder.getAdsPremium()){
                    //Premium reklamowe wygasło
                    Intent adPremiumIntent = new Intent(this, WatchPremiumAdActivity.class);
                    startActivity(adPremiumIntent);
                }
                //Brak premium
                Intent premiumIntent = new Intent(this, BuyPremiumActivity.class);
                premiumIntent.putExtra("messageID", 2);
                startActivity(premiumIntent);
            }



        }
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

}
