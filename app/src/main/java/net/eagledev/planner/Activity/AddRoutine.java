package net.eagledev.planner.Activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import net.eagledev.planner.Checker;
import net.eagledev.planner.Dialog.SelectColorDialog;
import net.eagledev.planner.Dialog.SelectIconDialog;
import net.eagledev.planner.Formatter;
import net.eagledev.planner.Dialog.HourPickerDialog;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.Dialog.NeedPremiumDialog;
import net.eagledev.planner.R;
import net.eagledev.planner.Routine;

import java.util.Calendar;
import java.util.List;

public class AddRoutine extends AppCompatActivity implements  View.OnClickListener, NeedPremiumDialog.NeedPremiumDialogListener, HourPickerDialog.HourPickerDialogListener, SelectColorDialog.SelectColorDialogListener, SelectIconDialog.SelectIconDialogListener {


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
    TextView btnStartHour;
    TextView btnStopHour;

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
    public static final int CODE_START = 1;
    public static final int CODE_STOP = 2;

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
        icon = 0;
        color = 0;
        int[] ints = {0};
        int[][] all = {ints};
        int[] colors = {MainActivity.colors[color]};
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

                if(icon < MainActivity.icons.length && icon >= 0){
                    icon = selectedRoutine.getIcon();
                } else {
                    for(int i = 0; i<MainActivity.icons.length; i++){
                        if(selectedRoutine.getIcon() == MainActivity.icons[i]){
                            icon = i;
                        }
                    }
                    if(icon >= MainActivity.icons.length || icon < 0) icon = 0;
                }

                Log.e(TAG, String.valueOf(icon));
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
                Log.e(TAG, String.valueOf(color));
                if(color < MainActivity.colors.length && color >= 0){
                    color = selectedRoutine.getColor();
                    Log.e(TAG, "1   "+ color);
                } else {
                    for(int i = 0; i<MainActivity.colors.length; i++){
                        if(selectedRoutine.getColor() == MainActivity.colors[i]){
                            color = i;
                            Log.e(TAG, "2   "+ color);
                        }
                    }
                    if(color >= MainActivity.colors.length || color < 0) color = 0;
                    Log.e(TAG, "3   "+ color);
                }
                int[] ints = {0};
                int[][] all = {ints};
                int[] colors = {MainActivity.colors[color]};
                imageColor.setBackgroundTintList(new ColorStateList(all,colors));
                imageIcon.setImageDrawable(getDrawable(MainActivity.icons[icon]));
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
        btnStartHour = findViewById(R.id.routine_start_time_text);
        btnStartHour.setOnClickListener(this);
        ImageButton startHourImageButton = findViewById(R.id.routine_start_time_button);
        startHourImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStartHour.performClick();
            }
        });
        btnStopHour = findViewById(R.id.routine_stop_time_text);
        btnStopHour.setOnClickListener(this);
        ImageButton stopHourImageButton = findViewById(R.id.routine_stop_time_button);
        stopHourImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStopHour.performClick();
            }
        });
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
        clickIcon(view.getId());
        switch (view.getId()) {
            case R.id.toolbar_confirm:
                confirm();
                break;

            case R.id.routine_start_time_text:
                if(!MainActivity.valueHolder.isDatePickerButton()) {

                    HourPickerDialog hourPickerDialog = new HourPickerDialog(context, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), 0);
                    hourPickerDialog.ShowDialog(CODE_START);
                }else {
                    tpd = new TimePickerDialog(AddRoutine.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            start.set(Calendar.HOUR_OF_DAY, hour);
                            start.set(Calendar.MINUTE, minute);
                            btnStartHour.setText(f.Time(start));
                        }
                    }, 0, 0 , true);
                    tpd.show();
                }

                break;

            case R.id.routine_stop_time_text:
                if(!MainActivity.valueHolder.isDatePickerButton()) {

                    HourPickerDialog hourPickerDialog = new HourPickerDialog(context, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), 0);
                    hourPickerDialog.ShowDialog(CODE_STOP);
                }else {

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
                }

                break;

            case R.id.toolbar_cancel:
                finish();
                break;

            case R.id.toolbar_delete:
                MainActivity.appDatabase.appDao().deleteRoutine(routineID);
                MainActivity.fDatabase.DeleteRoutine(routineID);
                refresh();
                setResult(MainActivity.CODE_CREATED);
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
            case R.id.color_view:
                SelectColorDialog colorDialog = new SelectColorDialog(context);
                colorDialog.ShowDialog(0);
                break;

        }
        imageIcon.setImageDrawable(getDrawable(MainActivity.icons[icon]));
    }

    private void setDay(boolean day, TextView textView) {

        int[] ints = {0};
        int[][] all = {ints};
        int[] colorBackground = {getColor(R.color.background)};
        int[] colorWhite = {getColor(R.color.white)};
        int[] colorAccent = {getColor(R.color.colorAccent)};
        if(day){
            textView.setBackground(getDrawable(R.drawable.task_day_selected));
            textView.setTextColor(new ColorStateList(all, colorWhite));
        } else {
            textView.setBackground(getDrawable(R.drawable.task_day_unselected));
            textView.setTextColor(new ColorStateList(all, colorAccent));
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
            setResult(MainActivity.CODE_CREATED);
            if(!edit){
                Routine newRoutine = new Routine(newID+1, name, icon, color, start, stop, monday, tuesday, wednesday ,thursday, friday, saturday, sunday);
                MainActivity.appDatabase.appDao().addRoutine(newRoutine);
                MainActivity.fDatabase.AddRoutine(newRoutine);
                finish();
            } else {
                Routine newRoutine = new Routine(routineID, name, icon, color, start, stop, monday, tuesday, wednesday ,thursday, friday, saturday, sunday);
                MainActivity.appDatabase.appDao().updateRoutine(newRoutine);
                MainActivity.fDatabase.AddRoutine(newRoutine);
                finish();
            }

        } else Toast.makeText(getApplicationContext(), R.string.routine_cant_interfere, Toast.LENGTH_LONG).show();

    }




    private void clickIcon(int id) {
        switch (id){
            case R.id.icon_view:
               SelectIconDialog dialog = new SelectIconDialog(context);
               dialog.ShowDialog(0);
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

    @Override
    public void getHourPickerDialogTime(int requestCode, int hour, int minute) {
        if(requestCode == CODE_START){
            start.set(Calendar.HOUR_OF_DAY, hour);
            start.set(Calendar.MINUTE, minute);
            btnStartHour.setText(f.Time(start));
        }
        if(requestCode == CODE_STOP){
            if(hour == 0 && minute == 0) {
                stop.set(Calendar.HOUR_OF_DAY, 23);
                stop.set(Calendar.MINUTE, 59);
            } else {
                stop.set(Calendar.HOUR_OF_DAY, hour);
                stop.set(Calendar.MINUTE, minute);
            }
            btnStopHour.setText(f.Time(stop));
        }
    }

    @Override
    public void getColorPickerDialogColor(int requestCode, int color) {
        this.color = color;
        int[] ints = {0};
        int[][] all = {ints};
        int[] colors = {MainActivity.colors[color]};
        imageColor.setBackgroundTintList(new ColorStateList(all,colors));
    }

    @Override
    public void getIconPickedIcon(int requestCode, int icon) {
        this.icon = icon;
        imageIcon.setImageDrawable(getDrawable(MainActivity.icons[icon]));
    }
}
