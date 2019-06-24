package net.eagledev.planner;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlanNextDayActivity extends AppCompatActivity implements View.OnClickListener {

    Calendar c;

    TimePickerDialog tpd;
    Button btnStart;
    Button btnStop;
    Drawable d;

    int iconID;
    int premiumIcon;
    int colorID;
    int premiumColor;
    TextView textView;
    boolean checked = false;
    Calendar date_start;
    Calendar date_stop;
    Formatter f = new Formatter();

    ImageView btn_select_icon;

    int hour = 0;
    int minute = 0;
    int aDay;
    int aMonth;
    int aYear;

    ImageView imageIcon;
    ImageView imageColor;
    Dialog d1;
    Dialog d2;
    List<Action> ac = new ArrayList<Action>();
    List<Integer> startRoutinesTime = new ArrayList<>();
    List<Integer> stopRoutinesTime = new ArrayList<>();
    public String emptyLabel = "432534253425345435345342532453425";
    int grayValue = 100;
    PieChart chart;
    int colorGray;
    TextView actionInfoText;
    TextView actionTimeText;
    Button btnEdit;
    Button btnEditRoutine;
    TextView actionInfo;
    String showDate;
    Button btn_new_action;
    ImageView clockArrow;
    int selectedID;
    Context context;
    List<Action> todayActions = new ArrayList<>();
    List<Action> actionList = new ArrayList<>();
    Calendar currentDate = Calendar.getInstance();

    int actionColor;
    int colorValue = 255;
    boolean colorUp = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_next_day);
        context = this;
        colorGray = Color.argb(200, grayValue, grayValue, grayValue);
        btn_select_icon = findViewById(R.id.icon_view);
        btn_select_icon.setOnClickListener(this);
        //findViewById(R.id.color_view).setOnClickListener(this);
        textView = findViewById(R.id.pndTittle);
        actionColor = Color.argb(200, colorValue, colorValue, colorValue);
        btnStart = findViewById(R.id.pndStartHour);
        btnStart.setOnClickListener(this);
        btnStop = findViewById(R.id.pndStopHour);
        btnStop.setOnClickListener(this);
        findViewById(R.id.pndAdd).setOnClickListener(this);
        findViewById(R.id.pndExit).setOnClickListener(this);
        imageIcon = findViewById(R.id.icon_view);
        date_start = Calendar.getInstance();
        date_start.add(Calendar.DATE,1);
        date_stop = Calendar.getInstance();
        date_stop.add(Calendar.DATE,1);
        iconID = MainActivity.icons[0];
        imageColor = findViewById(R.id.color_view);
        imageColor.setOnClickListener(this);
        //imageColor.setBackgroundColor(MainActivity.colors[0]);
        int[] ints = {0};
        int[][] all = {ints};
        int[] colors = {MainActivity.colors[0]};
        imageColor.setBackgroundTintList(new ColorStateList(all,colors));
        date_start.set(Calendar.HOUR_OF_DAY,0);
        date_start.set(Calendar.MINUTE,0);
        btnStart.setText(f.Time(date_start) );
        date_stop.set(Calendar.HOUR_OF_DAY,1);
        date_stop.set(Calendar.MINUTE,0);
        btnStop.setText(f.Time(date_stop) );
        ReadData();
        setupPieChartBackground();
        setupPieChart();
        setupUpdate();


    }

    @Override
    public void onClick(View v) {
        clickIcon(v.getId());
        clickColor(v.getId());

        switch (v.getId()){

            case R.id.pndAdd:
                confirm();
                break;
            case R.id.pndStartHour:

                tpd = new TimePickerDialog(PlanNextDayActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int mHour, int mMinute) {
                        date_start.set(Calendar.HOUR_OF_DAY, mHour);
                        date_start.set(Calendar.MINUTE, mMinute);
                        btnStart.setText(f.Time(date_start) );
                        if(date_start.get(Calendar.HOUR_OF_DAY)>date_stop.get(Calendar.HOUR_OF_DAY)){
                            date_stop.set(Calendar.HOUR_OF_DAY, mHour+1);
                            date_stop.set(Calendar.MINUTE, 0);
                            hour = date_stop.get(Calendar.HOUR_OF_DAY);
                            minute = date_stop.get(Calendar.MINUTE);
                            btnStop.setText(f.Time(date_stop));
                        }
                        if(date_start.get(Calendar.HOUR_OF_DAY) == date_stop.get(Calendar.HOUR_OF_DAY) && date_start.get(Calendar.MINUTE) - date_stop.get(Calendar.MINUTE)<=30){
                            date_stop.set(Calendar.HOUR_OF_DAY, mHour+1);
                            date_stop.set(Calendar.MINUTE, 0);
                            hour = date_stop.get(Calendar.HOUR_OF_DAY);
                            minute = date_stop.get(Calendar.MINUTE);
                            btnStop.setText(f.Time(date_stop));
                        }
                    }
                }, hour, minute, true);
                tpd.show();
            break;
            case R.id.pndStopHour:
                tpd = new TimePickerDialog(PlanNextDayActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int mHour, int mMinute) {

                        if(mHour == 0 && mMinute == 0) {
                            date_stop.set(Calendar.HOUR_OF_DAY, 23);
                            date_stop.set(Calendar.MINUTE, 59);
                        } else {
                            date_stop.set(Calendar.HOUR_OF_DAY, mHour);
                            date_stop.set(Calendar.MINUTE, mMinute);
                        }
                        btnStop.setText(f.Time(date_stop));
                        hour = mHour;
                        minute = mMinute;
                    }
                }, date_start.get(Calendar.HOUR_OF_DAY)+1, 0, true);
                tpd.show();

                break;

            case R.id.pndExit:
                finish();
                break;
        }

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
            //Log.e("actionsCount", String.valueOf(actionsCount) + "   " + getResources().getInteger(R.integer.premium_max_actions_one_day));

            if(actionsCount < getResources().getInteger(R.integer.premium_max_actions_one_day) || MainActivity.valueHolder.canUsePremium()) {
                //Spełnia warunki lub jest premium
                CreateAction();
            } else {
                //Brak premium
                NeedPremiumDialog pd = new NeedPremiumDialog(this);
                pd.ShowDialog(getString(R.string.premium_reason2));
            }



        }
    }

    private void CreateAction() {
        int newID = MainActivity.appDatabase.appDao().getMaxActionID()+1;
        Action newAction = new Action(newID,textView.getText().toString(), date_start, date_stop, iconID, colorID );
        MainActivity.appDatabase.appDao().addAction(newAction);
        hour = date_stop.get(Calendar.HOUR_OF_DAY);
        minute = date_stop.get(Calendar.MINUTE);
        if(date_stop.get(Calendar.HOUR_OF_DAY)==23){
            if (date_stop.get(Calendar.MINUTE) <= 30){
                date_start = date_stop;
                date_stop.set(Calendar.MINUTE, 59);
                btnStart.setText(f.Time(date_start));
                btnStop.setText(f.Time(date_stop));
            } else finish();
        } else {
            date_start.set(Calendar.HOUR_OF_DAY, date_stop.get(Calendar.HOUR_OF_DAY));
            date_start.set(Calendar.MINUTE, date_stop.get(Calendar.MINUTE));
            btnStart.setText(f.Time(date_start) );
            date_stop.set(Calendar.HOUR_OF_DAY,date_start.get(Calendar.HOUR_OF_DAY)+1);
            date_stop.set(Calendar.MINUTE,0);
            btnStop.setText(f.Time(date_stop));
            colorID = MainActivity.colors[0];
            iconID = MainActivity.icons[0];
            setColor();
            imageIcon.setImageDrawable(getDrawable(iconID));
            textView.setText("");
        }
        //Odśwież wykres

        ReadData();





    }

    private void Update() {
        if(colorUp){
            if(colorValue<=240){
                colorValue += 10;
            } else {
                colorUp = false;
                colorValue -=10;
            }
        } else {
            if(colorValue>=100){
                colorValue -= 10;
            } else {
                colorUp = true;
                colorValue +=10;
            }
        }
        actionColor = Color.argb(200, colorValue, colorValue, colorValue);
        ReadData();
        setupPieChart();
    }


    private void setupPieChart() {
        final List<PieEntry> pieEntries = new ArrayList<>();
        List<Integer> pieColors = new ArrayList<>();
        int t = 0;
        boolean isRoutine = false;

        if(ac.size() > 0) {
            Action currentAction = ac.get(0);
            int currentActionNumber = 0;
            int actionTime = 24*60;
            //Powtarzaj dopki czas nie osiągie 24h
            while(t<24*60) {
                if(!ac.isEmpty()) {
                    for (int i = 0; i<ac.size(); i++) {
                        if (actionTime > ac.get(i).getStartMinutes()) {
                            actionTime = ac.get(i).getStartMinutes();
                            currentAction = ac.get(i);
                            currentActionNumber = i;
                        }
                    } ac.remove(currentActionNumber);
                    isRoutine = false;
                    //Sprawdzanie akcja czy rutyna
                    if(startRoutinesTime.size() > 0) {
                        for (int r = 0; startRoutinesTime.size()>r ; r++) {
                            if (startRoutinesTime.get(r) == currentAction.getStartMinutes() && stopRoutinesTime.get(r) == currentAction.getStopMinutes()){
                                isRoutine = true;
                            }
                        }
                    }
                    String acID = String.valueOf(currentAction.getId());
                    if(isRoutine) {
                        acID="r"+String.valueOf(currentAction.getId());
                    }
                    if(currentAction.getStartMinutes() > t) {
                        pieEntries.add(new PieEntry(currentAction.getStartMinutes()-t,emptyLabel));
                        pieColors.add(colorGray);
                        if(currentAction.getStopMinutes()-currentAction.getStartMinutes()>90){
                            pieEntries.add(new PieEntry(currentAction.getTime(), acID,getDrawable(currentAction.getIcon())));
                        } else {
                            //Czas trwania mniejszy niż 90 minut

                            //Drawable d = getDrawable(currentAction.getIcon());
                            if(currentAction.getStopMinutes()-currentAction.getStartMinutes()>45){
                                pieEntries.add(new PieEntry(currentAction.getTime(), acID));
                            } else {
                                pieEntries.add(new PieEntry(currentAction.getTime(), acID));
                            }
                        }

                        pieColors.add(currentAction.getColor());
                        t=currentAction.getStopMinutes();
                        actionTime = 24*60;
                    } else {
                        if(currentAction.getStopMinutes()-currentAction.getStartMinutes()>90){
                            pieEntries.add(new PieEntry(currentAction.getTime(), acID,getDrawable(currentAction.getIcon())));
                        } else {
                            //Czas trwania mniejszy niż 90 minut
                            if(currentAction.getStopMinutes()-currentAction.getStartMinutes()>45){
                                pieEntries.add(new PieEntry(currentAction.getTime(), acID));
                            } else {
                                pieEntries.add(new PieEntry(currentAction.getTime(), acID));
                            }
                        }

                        pieColors.add(currentAction.getColor());
                        t=currentAction.getStopMinutes();
                        actionTime = 24*60;
                    }
                } else {
                    pieEntries.add(new PieEntry(24*60-t, emptyLabel));
                    pieColors.add(colorGray);
                    t=24*60;
                }
            }
        }
        else {
            pieEntries.add(new PieEntry(24*60,emptyLabel));
            pieColors.add(colorGray);
            t=24*60;
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, "Test");
        int color[] = new int[pieColors.size()];
        for (int i = 0; i<pieColors.size(); i++) {
            color[i] = pieColors.get(i);
        }
        dataSet.setColors(color);
        dataSet.setValueTextSize(0);
        dataSet.setSliceSpace(2);
        PieData data = new PieData(dataSet);
        //Get the chart
        chart = (PieChart) findViewById(R.id.pndChart);
        chart.setHighlightPerTapEnabled(true);
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // pobieranie actions z danego dnia
                //sprawdzanie czy nazwa oraz długość akcji w minutach są takie same jak pie entry
                PieEntry pe = (PieEntry) e;
                String s = pe.getLabel();

                if(!s.equals(emptyLabel)){
                    if(s.charAt(0)=='r') {
                        s = s.replaceFirst("r","");
                        Routine selectedRoutine = MainActivity.appDatabase.appDao().idRoutine(Integer.parseInt(s));
                        selectedID = Integer.parseInt(s);



                    } else {
                        Action selectedAction = MainActivity.appDatabase.appDao().idAction(Integer.parseInt(s));
                        selectedID = Integer.parseInt(s);
                    }
                } else {
                }
            }
            @Override
            public void onNothingSelected() {
                //HideButtons();
            }
        });
        chart.setDragDecelerationEnabled(false);
        chart.setRotationEnabled(false);
        chart.setDrawSliceText(false);
        chart.setData(data);
        chart.animateY(0);
        chart.setTransparentCircleRadius(0f);
        Description desc = new Description();
        desc.setText("");
        chart.setDescription(desc);
        chart.invalidate();
        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        //Setup clock
        clockArrow = findViewById(R.id.clock);
    }

    private void setupPieChartBackground() {
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            pieEntries.add(new PieEntry(60));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        int c = 100;
        dataSet.setColor(Color.argb(255, c, c, c));
        dataSet.setValueTextSize(0);
        dataSet.setSliceSpace(2);
        PieData data = new PieData(dataSet);
        //Get the chart
        PieChart bgchart = (PieChart) findViewById(R.id.pndChartBg);
        bgchart.setDragDecelerationEnabled(false);
        bgchart.setDrawSliceText(false);
        bgchart.setData(data);
        bgchart.animateY(0);
        bgchart.setRotationEnabled(false);
        bgchart.setTransparentCircleRadius(0f);
        bgchart.setCenterText("Test");
        Description desc = new Description();
        desc.setText("");
        bgchart.setDescription(desc);
        bgchart.invalidate();
        Legend legend = bgchart.getLegend();
        legend.setEnabled(false);
    }

    private void ReadData() {
        MainActivity.appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "planner").allowMainThreadQueries().build();
        startRoutinesTime.clear();
        stopRoutinesTime.clear();
        ac.clear();
        boolean today = false;
        //Log.e("date equals", String.valueOf(checker.DateEquals(currentDate, Calendar.getInstance())));

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,1);
        //if(checker.DateEquals(currentDate, Calendar.getInstance())){
          //  today = true;
         //   actionList.clear();
        //}
        try{
            todayActions= MainActivity.appDatabase.appDao().getActionsFromDay(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
        } catch (Exception e){
            Log.e("PlanNextDayActivity", e.getMessage());
        }

        Action action = new Action(0,textView.getText().toString(), date_start, date_stop, iconID, actionColor );
        actionList.add(action);
        ac.add(action);
        for(int i = 0; i<todayActions.size(); i++) {
            ac.add(todayActions.get(i));
            if(today){
                actionList.add(todayActions.get(i));
            }


        }

        List<Routine> routineAdapter = new ArrayList<>();
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        switch (currentDate.get(Calendar.DAY_OF_WEEK)){
            case 2:
                routineAdapter = MainActivity.appDatabase.appDao().getMonday();
                break;
            case 3:
                routineAdapter = MainActivity.appDatabase.appDao().getTuesday();
                break;
            case 4:
                routineAdapter = MainActivity.appDatabase.appDao().getWednesday();
                break;
            case 5:
                routineAdapter = MainActivity.appDatabase.appDao().getThursday();
                break;
            case 6:
                routineAdapter = MainActivity.appDatabase.appDao().getFriday();
                break;
            case 7:
                routineAdapter = MainActivity.appDatabase.appDao().getSaturday();
                break;
            case 1:
                routineAdapter = MainActivity.appDatabase.appDao().getSunday();
                break;
        }
        for(int i = 0; i<routineAdapter.size(); i++) {
            Routine r = routineAdapter.get(i);
            Calendar start =  r.getStart();
            int startR = start.get(Calendar.HOUR_OF_DAY)*60+start.get(Calendar.MINUTE);
            Calendar stop = r.getStop();
            int stopR = stop.get(Calendar.HOUR_OF_DAY)*60+stop.get(Calendar.MINUTE);
            boolean createAction = true;
            int startM = 0;
            int startH = 0;
            int stopM = 0;
            int stopH = 0;
            for (int l = 0; l<ac.size(); l++) {

                //Funkcja sprawdz tylko kolizję z jedną akcją

                int startA = ac.get(l).getStart_hour()*60+ac.get(l).getStart_minute();
                int stopA = ac.get(l).getStop_hour()*60+ac.get(l).getStop_minute();
                //Nie koliduje
                if(stopA <= startR || startA >= stopR) {
                    //Log.e(r.getName(),"Nie koliduje z "+ac.get(l).getDesc());
                }
                //Koliduje na początku
                if(stopA > startR && stopA < stopR && startA < startR) {
                    //Log.e(r.getName(),"Koliduje na początku z "+ac.get(l).getDesc());
                    startR = stopA;
                }
                //Koliduje na końcu
                if(startA < stopR && startA > startR && stopA > stopR) {
                    //Log.e(r.getName(),"Koliduje na końcu z "+ac.get(l).getDesc());
                    stopR = startA;
                }
                //Kolidujee wewnątrz
                if(startA >= startR && stopA <= stopR) {
                    //Log.e(r.getName(),"Kolidujee wewnątrz z "+ac.get(l).getDesc());
                    if(startA == startR) {
                        startR = stopA;
                    }
                    if(stopA == stopR) {
                        stopR = startA;
                    }
                    if(startA == startR && stopA == stopR || startA > startR && stopA < stopR){
                        createAction = false;
                    }
                }
                //Całkowicie koliduje
                if(startA <= startR && stopA >= stopR) {
                    //Log.e(r.getName(),"Całkowicie koliduje z "+ac.get(l).getDesc());
                    createAction = false;
                }
            }
            boolean test = true;
            if (createAction && test) {

                while(startR > 0) {
                    if(startR>=60) {
                        startR -= 60;
                        startH++;
                    } else {
                        startM = startR;
                        startR = 0;
                    }
                }
                while (stopR > 0) {
                    if(stopR>=60) {
                        stopR -=60;
                        stopH++;
                    } else {
                        stopM = stopR;
                        stopR = 0;
                    }
                }
                Calendar startTimeR = Calendar.getInstance();
                startTimeR.set(Calendar.HOUR_OF_DAY, startH);
                startTimeR.set(Calendar.MINUTE, startM);
                Calendar stopTimeR = Calendar.getInstance();
                stopTimeR.set(Calendar.HOUR_OF_DAY, stopH);
                stopTimeR.set(Calendar.MINUTE, stopM);
                //Log.e(r.getName(), "Crating action from routine");
                Action a = new Action(r.getId(), r.getName(), startTimeR, stopTimeR, r.getIcon(), r.getColor());

                    startRoutinesTime.add(a.getStartMinutes());
                    stopRoutinesTime.add(a.getStopMinutes());
                    ac.add(a);
                    actionList.add(a);


            }
        }


    }

    private void clickColor(int id) {
        switch (id) {

            case R.id.color_view:
                d2 = new Dialog(PlanNextDayActivity.this);
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
            NeedPremiumDialog pd = new NeedPremiumDialog(this);
            pd.ShowDialog(getString(R.string.premium_reason4));
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
                d1 = new Dialog(PlanNextDayActivity.this);
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
            NeedPremiumDialog pd = new NeedPremiumDialog(context);
            pd.ShowDialog(getString(R.string.premium_reason3));
        }
    }

    private void setupUpdate() {
        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(50);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Update();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();
    }
}
