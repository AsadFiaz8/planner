package net.eagledev.planner.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import net.eagledev.planner.BuyPremiumActivity;
import net.eagledev.planner.Checker;
import net.eagledev.planner.Formatter;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.NeedPremiumDialog;
import net.eagledev.planner.R;
import net.eagledev.planner.Routine;

import java.util.Calendar;
import java.util.List;

public class AddRoutine extends Activity implements  View.OnClickListener, NeedPremiumDialog.NeedPremiumDialogListener {


    public static final String TAG = "AddRoutine";
    public static final int CODE_ICONS = 1;
    public static final int CODE_COLORS = 2;

    TextView mondayBtn;
    TextView tuesdayBtn;
    TextView wednesdayBtn;
    TextView thursdayBtn;
    TextView fridayBtn;
    TextView saturdayBtn;
    TextView sundayBtn;


    Context context;
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
    Checker checker = new Checker();
    boolean edit = false;
    Routine selectedRoutine;
    int routineID;

    EditText nameText;
    Button btnStartHour;
    Button btnStopHour;

    Button btnSelectIcon;
    TimePickerDialog tpd;
    ImageView imageConfirm;
    ImageView imageCancel;
    ImageView imageDelete;
    ImageView imageIcon;
    ImageView imageColor;



    Formatter f = new Formatter();
    Dialog d1;
    Dialog d2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routine);
        setButtons();
        nameText = findViewById(R.id.input_routine_name);
        start = Calendar.getInstance();
        stop = Calendar.getInstance();
        imageIcon = findViewById(R.id.icon_view);
        imageColor = findViewById(R.id.color_view);
        icon = MainActivity.icons[0];
        color = MainActivity.colors[0];
        int[] ints = {0};
        int[][] all = {ints};
        int[] colors = {color};
        imageColor.setBackgroundTintList(new ColorStateList(all,colors));
        context = this;
        setValues();
        setupDate();
    }

    private void setValues() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        selectedRoutine = new Routine();
        if (bundle!=null) {
            edit = (boolean) bundle.get("edit");
            if(edit) {
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
                setDay(monday, mondayBtn);
                tuesday=selectedRoutine.isTuesday();
                setDay(tuesday, tuesdayBtn);
                wednesday=selectedRoutine.isWednesday();
                setDay(wednesday, wednesdayBtn);
                thursday=selectedRoutine.isThursday();
                setDay(thursday, thursdayBtn);
                friday=selectedRoutine.isFriday();
                setDay(friday, fridayBtn);
                saturday=selectedRoutine.isSaturday();
                setDay(saturday, saturdayBtn);
                sunday=selectedRoutine.isSunday();
                setDay(sunday, sundayBtn);
                setColor();
                imageIcon.setImageDrawable(getDrawable(icon));
                imageDelete = findViewById(R.id.toolbar_delete);
                imageDelete.setVisibility(View.VISIBLE);
                imageDelete.setOnClickListener(this);
            }

        }


    }

    private void setupDate() {
        if(!edit){
            start.set(Calendar.HOUR_OF_DAY,0);
            stop.set(Calendar.HOUR_OF_DAY,1);
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
        //btnSelectIcon.setOnClickListener(this);
        imageCancel = findViewById(R.id.toolbar_cancel);
        imageCancel.setOnClickListener(this);
        findViewById(R.id.color_view).setOnClickListener(this);

        mondayBtn = findViewById(R.id.routine_mo);
        mondayBtn.setOnClickListener(this);
        tuesdayBtn = findViewById(R.id.routine_tu);
        tuesdayBtn.setOnClickListener(this);
        wednesdayBtn = findViewById(R.id.routine_we);
        wednesdayBtn.setOnClickListener(this);
        thursdayBtn = findViewById(R.id.routine_th);
        thursdayBtn.setOnClickListener(this);
        fridayBtn = findViewById(R.id.routine_fr);
        fridayBtn.setOnClickListener(this);
        saturdayBtn = findViewById(R.id.routine_sa);
        saturdayBtn.setOnClickListener(this);
        sundayBtn = findViewById(R.id.routine_su);
        sundayBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        clickColor(view.getId());
        clickIcon(view.getId());
        switch (view.getId()) {
            case R.id.toolbar_confirm:
                confirm();
                break;

            case R.id.input_routine_start:
                tpd = new TimePickerDialog(AddRoutine.this, new TimePickerDialog.OnTimeSetListener() {
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
                tpd = new TimePickerDialog(AddRoutine.this, new TimePickerDialog.OnTimeSetListener() {
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
                MainActivity.appDatabase.appDao().deleteRoutine(routineID);
                MainActivity.fDatabase.DeleteRoutine(routineID);
                refresh();
                finish();
                Toast.makeText(getApplicationContext(), R.string.routine_deleted, Toast.LENGTH_LONG).show();
                break;

            case R.id.routine_mo:
                monday = !monday;
                setDay(monday, mondayBtn);
                break;
            case R.id.routine_tu:
                tuesday = !tuesday;
                setDay(tuesday, tuesdayBtn);
                break;
            case R.id.routine_we:
                wednesday = !wednesday;
                setDay(wednesday, wednesdayBtn);
                break;
            case R.id.routine_th:
                thursday = !thursday;
                setDay(thursday, thursdayBtn);
                break;
            case R.id.routine_fr:
                friday = !friday;
                setDay(friday, fridayBtn);
                break;
            case R.id.routine_sa:
                saturday = !saturday;
                setDay(saturday, saturdayBtn);
                break;
            case R.id.routine_su:
                sunday = !sunday;
                setDay(sunday, sundayBtn);
                break;

        }
        imageIcon.setImageDrawable(getDrawable(icon));
    }

    private void setDay(boolean day, TextView textView) {

        int[] ints = {0};
        int[][] all = {ints};
        int[] colorBackground = {getColor(R.color.background)};
        int[] colorAccent = {getColor(R.color.colorAccent)};
        if(day){
            textView.setBackgroundTintList(new ColorStateList(all, colorAccent));
        } else {
            textView.setBackgroundTintList(new ColorStateList(all, colorBackground));
        }
    }

    private void refresh(){
        MainActivity.needRefresh = true;
        Intent intent = new Intent();
        intent.putExtra("message_return", "refresh");
        setResult(RESULT_OK, intent);
    }

    private void confirm() {

        boolean isOK = true;
        name = String.valueOf(nameText.getText());
        int newID = MainActivity.appDatabase.appDao().getMaxRoutinesID();

        if(monday) {
            List<Routine> routines = MainActivity.appDatabase.appDao().getMonday();
            int delete = -1;
            for(int i = 0; i<routines.size(); i++){
                if(routines.get(i).getId() == routineID){
                    delete = i;
                }
            }
            if(delete != -1){
                routines.remove(delete);
            }
            for (int r= 0; r<routines.size(); r++ ) {
                if(checker.TimeCollision(start, stop, routines.get(r).getStart(), routines.get(r).getStop())){
                    isOK = false;
                }
            }
        }
        if(tuesday) {
            List<Routine> routines = MainActivity.appDatabase.appDao().getTuesday();
            int delete = -1;
            for(int i = 0; i<routines.size(); i++){
                if(routines.get(i).getId() == routineID){
                    delete = i;
                }
            }
            if(delete != -1){
                routines.remove(delete);
            }
            for (int r= 0; r<routines.size(); r++ ) {
                if(checker.TimeCollision(start, stop, routines.get(r).getStart(), routines.get(r).getStop())){
                    isOK = false;
                }
            }
        }
        if(wednesday) {
            List<Routine> routines = MainActivity.appDatabase.appDao().getWednesday();
            int delete = -1;
            for(int i = 0; i<routines.size(); i++){
                if(routines.get(i).getId() == routineID){
                    delete = i;
                }
            }
            if(delete != -1){
                routines.remove(delete);
            }
            for (int r= 0; r<routines.size(); r++ ) {
                if(checker.TimeCollision(start, stop, routines.get(r).getStart(), routines.get(r).getStop())){
                    isOK = false;
                }
            }
        }
        if(thursday) {
            List<Routine> routines = MainActivity.appDatabase.appDao().getThursday();
            int delete = -1;
            for(int i = 0; i<routines.size(); i++){
                if(routines.get(i).getId() == routineID){
                    delete = i;
                }
            }
            if(delete != -1){
                routines.remove(delete);
            }
            for (int r= 0; r<routines.size(); r++ ) {
                if(checker.TimeCollision(start, stop, routines.get(r).getStart(), routines.get(r).getStop())){
                    isOK = false;
                }
            }
        }
        if(friday) {
            List<Routine> routines = MainActivity.appDatabase.appDao().getFriday();
            int delete = -1;
            for(int i = 0; i<routines.size(); i++){
                if(routines.get(i).getId() == routineID){
                    delete = i;
                }
            }
            if(delete != -1){
                routines.remove(delete);
            }
            for (int r= 0; r<routines.size(); r++ ) {
                if(checker.TimeCollision(start, stop, routines.get(r).getStart(), routines.get(r).getStop())){
                    isOK = false;
                }
            }
        }
        if(saturday) {
            List<Routine> routines = MainActivity.appDatabase.appDao().getSaturday();
            int delete = -1;
            for(int i = 0; i<routines.size(); i++){
                if(routines.get(i).getId() == routineID){
                    delete = i;
                }
            }
            if(delete != -1){
                routines.remove(delete);
            }
            for (int r= 0; r<routines.size(); r++ ) {
                if(checker.TimeCollision(start, stop, routines.get(r).getStart(), routines.get(r).getStop())){
                    isOK = false;
                }
            }
        }
        if(sunday) {
            List<Routine> routines = MainActivity.appDatabase.appDao().getSunday();
            int delete = -1;
            for(int i = 0; i<routines.size(); i++){
                if(routines.get(i).getId() == routineID){
                    delete = i;
                }
            }
            if(delete != -1){
                routines.remove(delete);
            }
            for (int r= 0; r<routines.size(); r++ ) {
                if(checker.TimeCollision(start, stop, routines.get(r).getStart(), routines.get(r).getStop())){
                    isOK = false;
                }
            }
        }

        if(isOK){
            if(!edit){
                Routine newRoutine = new Routine(newID+1, name, icon, color, start, stop, monday, tuesday, wednesday ,thursday, friday, saturday, sunday);
                MainActivity.appDatabase.appDao().addRoutine(newRoutine);
                MainActivity.fDatabase.AddRoutine(newRoutine);
                MainActivity.needRefresh = true;
                finish();
            } else {
                Routine newRoutine = new Routine(routineID, name, icon, color, start, stop, monday, tuesday, wednesday ,thursday, friday, saturday, sunday);
                MainActivity.appDatabase.appDao().updateRoutine(newRoutine);
                MainActivity.fDatabase.AddRoutine(newRoutine);
                refresh();
                finish();
            }

        } else Toast.makeText(getApplicationContext(), R.string.routine_cant_interfere, Toast.LENGTH_LONG).show();

    }

    private void clickColor(int id) {
        switch (id) {

            case R.id.color_view:
                d2 = new Dialog(AddRoutine.this);
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
        if(MainActivity.valueHolder.canUsePremium()){
            color = MainActivity.colors[premiumColor];
            //imageColor.setBackgroundColor(color);
            int[] ints = {0};
            int[][] all = {ints};
            int[] colors = {color};
            imageColor.setBackgroundTintList(new ColorStateList(all,colors));
            d2.dismiss();
        } else {
            NeedPremiumDialog pd = new NeedPremiumDialog(context, CODE_COLORS);
            pd.ShowDialog(getString(R.string.premium_reason4));
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
                d1 = new Dialog(AddRoutine.this);
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
        if(MainActivity.valueHolder.canUsePremium()){
            icon = MainActivity.icons[premiumIcon];
            d1.dismiss();
            imageIcon.setImageDrawable(getDrawable(icon));
        } else {
            NeedPremiumDialog pd = new NeedPremiumDialog(context, CODE_ICONS);
            pd.ShowDialog(getString(R.string.premium_reason3));
        }
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
                    icon = MainActivity.icons[Integer.parseInt(messageReturn)];
                }
        }
    }


    @Override
    public void getPremiumDialogResultCode(int resultCode) {
        switch (resultCode){
            case CODE_COLORS:
                color = MainActivity.colors[premiumColor];
                //imageColor.setBackgroundColor(color);
                int[] ints = {0};
                int[][] all = {ints};
                int[] colors = {color};
                imageColor.setBackgroundTintList(new ColorStateList(all,colors));
                d2.dismiss();
                break;
            case CODE_ICONS:
                icon = MainActivity.icons[premiumIcon];
                d1.dismiss();
                imageIcon.setImageDrawable(getDrawable(icon));
                break;
        }
    }
}
