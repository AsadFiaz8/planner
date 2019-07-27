package net.eagledev.planner;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import net.eagledev.planner.Dialog.NeedPremiumDialog;
import net.eagledev.planner.Dialog.SelectColorDialog;
import net.eagledev.planner.Dialog.SelectIconDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlanNextDayActivity extends AppCompatActivity implements View.OnClickListener, NeedPremiumDialog.NeedPremiumDialogListener,
        SelectColorDialog.SelectColorDialogListener, SelectIconDialog.SelectIconDialogListener {

    TimePickerDialog tpd;
    ImageButton btnStart;
    ImageButton btnStop;
    TextView startText;
    TextView stopText;
    public static final int CODE_ACTIONS = 0;
    public static final int CODE_ICONS = 1;
    public static final int CODE_COLORS = 2;
    int iconID;
    int premiumIcon;
    int colorID;
    int premiumColor;
    EditText textView;
    boolean checked = false;
    Calendar date_start;
    Calendar date_stop;
    Formatter f = new Formatter();
    ImageView btn_select_icon;
    int hour = 0;
    int minute = 0;
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
        textView = findViewById(R.id.input_action_name);
        actionColor = Color.argb(200, colorValue, colorValue, colorValue);
        btnStart = findViewById(R.id.action_start_time_button);
        btnStart.setOnClickListener(this);
        startText = findViewById(R.id.action_start_time_text);
        btnStop = findViewById(R.id.action_stop_time_button);
        btnStop.setOnClickListener(this);
        stopText = findViewById(R.id.action_stop_time_text);
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
        startText.setText(f.Time(date_start) );
        date_stop.set(Calendar.HOUR_OF_DAY,1);
        date_stop.set(Calendar.MINUTE,0);
        stopText.setText(f.Time(date_stop) );
        ReadData();
        setupPieChartBackground();
        setupPieChart();
        setupUpdate();


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.pndAdd:
                confirm();
                break;
            case R.id.action_start_time_button:

                tpd = new TimePickerDialog(PlanNextDayActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int mHour, int mMinute) {
                        date_start.set(Calendar.HOUR_OF_DAY, mHour);
                        date_start.set(Calendar.MINUTE, mMinute);
                        startText.setText(f.Time(date_start) );
                        if(date_start.get(Calendar.HOUR_OF_DAY)>date_stop.get(Calendar.HOUR_OF_DAY)){
                            date_stop.set(Calendar.HOUR_OF_DAY, mHour+1);
                            date_stop.set(Calendar.MINUTE, 0);
                            hour = date_stop.get(Calendar.HOUR_OF_DAY);
                            minute = date_stop.get(Calendar.MINUTE);
                            stopText.setText(f.Time(date_stop));
                        }
                        if(date_start.get(Calendar.HOUR_OF_DAY) == date_stop.get(Calendar.HOUR_OF_DAY) && date_start.get(Calendar.MINUTE) - date_stop.get(Calendar.MINUTE)<=30){
                            date_stop.set(Calendar.HOUR_OF_DAY, mHour+1);
                            date_stop.set(Calendar.MINUTE, 0);
                            hour = date_stop.get(Calendar.HOUR_OF_DAY);
                            minute = date_stop.get(Calendar.MINUTE);
                            stopText.setText(f.Time(date_stop));
                        }
                    }
                }, hour, minute, true);
                tpd.show();
            break;
            case R.id.action_stop_time_button:
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
                        stopText.setText(f.Time(date_stop));
                        hour = mHour;
                        minute = mMinute;
                    }
                }, date_start.get(Calendar.HOUR_OF_DAY)+1, 0, true);
                tpd.show();

                break;

            case R.id.pndExit:
                finish();
                break;

            case R.id.color_view:
                SelectColorDialog colorDialog = new SelectColorDialog(context);
                colorDialog.ShowDialog(0);

                break;
            case R.id.icon_view:
                SelectIconDialog dialog = new SelectIconDialog(context);
                dialog.ShowDialog(0);
                break;
        }

    }

    private void confirm() {
        Checker checker = new Checker();


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

            List<Action> actionsFromDay = MainActivity.appDatabase.appDao().getActionsFromDay(date_start.get(Calendar.DAY_OF_MONTH), date_start.get(Calendar.MONTH), date_start.get(Calendar.YEAR));
            int actionsCount = actionsFromDay.size();
            //Log.e("actionsCount", String.valueOf(actionsCount) + "   " + getResources().getInteger(R.integer.premium_max_actions_one_day));

            if(actionsCount < getResources().getInteger(R.integer.premium_max_actions_one_day) || MainActivity.valueHolder.canUsePremium()) {
                //Spełnia warunki lub jest premium
                CreateAction();
            } else {
                //Brak premium
                NeedPremiumDialog pd = new NeedPremiumDialog(this, CODE_ACTIONS);
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
                startText.setText(f.Time(date_start));
                stopText.setText(f.Time(date_stop));
            } else finish();
        } else {
            date_start.set(Calendar.HOUR_OF_DAY, date_stop.get(Calendar.HOUR_OF_DAY));
            date_start.set(Calendar.MINUTE, date_stop.get(Calendar.MINUTE));
            startText.setText(f.Time(date_start) );
            date_stop.set(Calendar.HOUR_OF_DAY,date_start.get(Calendar.HOUR_OF_DAY)+1);
            date_stop.set(Calendar.MINUTE,0);
            stopText.setText(f.Time(date_stop));
            colorID = MainActivity.colors[0];
            iconID = MainActivity.icons[0];
            int[] ints = {0};
            int[][] all = {ints};
            int[] colors = {MainActivity.colors[0]};
            imageColor.setBackgroundTintList(new ColorStateList(all,colors));
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
                            pieEntries.add(new PieEntry(currentAction.getTime(), acID,getDrawable(f.newIcon(currentAction.getIcon()))));
                        } else {
                            //Czas trwania mniejszy niż 90 minut

                            //Drawable d = getDrawable(currentAction.getIcon());
                            if(currentAction.getStopMinutes()-currentAction.getStartMinutes()>45){
                                pieEntries.add(new PieEntry(currentAction.getTime(), acID));
                            } else {
                                pieEntries.add(new PieEntry(currentAction.getTime(), acID));
                            }
                        }

                        pieColors.add(f.newColor(currentAction.getColor()));
                        t=currentAction.getStopMinutes();
                        actionTime = 24*60;
                    } else {
                        if(currentAction.getStopMinutes()-currentAction.getStartMinutes()>90){
                            pieEntries.add(new PieEntry(currentAction.getTime(), acID,getDrawable(f.newIcon(currentAction.getIcon()))));
                        } else {
                            //Czas trwania mniejszy niż 90 minut
                            if(currentAction.getStopMinutes()-currentAction.getStartMinutes()>45){
                                pieEntries.add(new PieEntry(currentAction.getTime(), acID));
                            } else {
                                pieEntries.add(new PieEntry(currentAction.getTime(), acID));
                            }
                        }

                        pieColors.add(f.newColor(currentAction.getColor()));
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




    private void setColor() {
        int[] ints = {0};
        int[][] all = {ints};
        int[] colors = {colorID};
        imageColor.setBackgroundTintList(new ColorStateList(all,colors));
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

    @Override
    public void getPremiumDialogResultCode(int resultCode) {
        MainActivity.valueHolder.changePremiumPoints(-1);
        switch (resultCode){
            case CODE_ACTIONS:
                CreateAction();
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
                imageIcon.setImageDrawable(getDrawable(f.newIcon(iconID)));
                break;
        }
    }

    @Override
    public void getColorPickerDialogColor(int requestCode, int color) {
        colorID = color;
        int[] ints = {0};
        int[][] all = {ints};
        int[] colors = {MainActivity.colors[colorID]};
        imageColor.setBackgroundTintList(new ColorStateList(all,colors));
    }

    @Override
    public void getIconPickedIcon(int requestCode, int icon) {
        iconID = icon;
        imageIcon.setImageDrawable(getDrawable(MainActivity.icons[iconID]));
    }
}
