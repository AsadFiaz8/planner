package net.eagledev.planner.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import net.eagledev.planner.Aim;
import net.eagledev.planner.BuyPremiumActivity;
import net.eagledev.planner.Formatter;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;
import net.eagledev.planner.WatchPremiumAdActivity;

import java.util.Calendar;
import java.util.List;

public class AddAimActivity extends Activity implements View.OnClickListener {

    Button b;
    NumberPicker typePicker;
    NumberPicker timePickrer;
    Formatter f = new Formatter();
    int type = 0;
    Calendar start = Calendar.getInstance();
    Calendar stop = Calendar.getInstance();
    Calendar now = Calendar.getInstance();
    TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aim);
        setupPickers();
        textView = findViewById(R.id.input_name);
        findViewById(R.id.toolbar_confirm).setOnClickListener(this);
        findViewById(R.id.toolbar_cancel).setOnClickListener(this);
    }





    private void setupPickers() {

        typePicker = findViewById(R.id.aim_picker1);
        timePickrer = findViewById(R.id.aim_picker2);
        start.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        stop.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        typePicker.setMinValue(0);
        typePicker.setMaxValue(2);
        typePicker.setDisplayedValues(new String[] {"Dzień", "Miesiąc" , " Rok"});
        typePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Calendar now = Calendar.getInstance();
                type = i1;
                switch (i1){
                    case 0:
                        timePickrer.setMinValue(0);
                        timePickrer.setMaxValue(1);
                        timePickrer.setDisplayedValues(new String[] {"Dzisiaj","Jutro"});
                        break;
                    case 1:
                        timePickrer.setMinValue(0);
                        timePickrer.setMaxValue(1);
                        int m1 = now.get(Calendar.MONTH);
                        int m2;
                        if (m1<11) {
                            m2 = m1+1;
                        } else m2 = 0;
                        timePickrer.setDisplayedValues(new String[] {f.Month(m1),f.Month(m2)});
                        break;

                    case 2:
                        timePickrer.setMinValue(0);
                        timePickrer.setMaxValue(1);
                        timePickrer.setDisplayedValues(new String[] {String.valueOf(now.get(Calendar.YEAR)),String.valueOf(now.get(Calendar.YEAR)+1)});
                        break;
                }

            }
        });
        timePickrer.setMinValue(0);
        timePickrer.setMaxValue(1);
        timePickrer.setDisplayedValues(new String[] {"Dzisiaj","Jutro"});
        timePickrer.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                if(i1 == 0) {
                    switch (type){
                        case 0:
                            start.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                            stop.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                            break;
                        case 1:
                            start.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                            start.set(Calendar.DAY_OF_MONTH, 1);
                            stop.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                            stop.set(Calendar.DAY_OF_MONTH, stop.getActualMaximum(Calendar.DAY_OF_MONTH));
                            break;
                        case 2:
                            start.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                            start.set(Calendar.DAY_OF_MONTH, 1);
                            start.set(Calendar.MONTH, 0);
                            stop.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH));
                            stop.set(Calendar.MONTH, 11);
                            stop.set(Calendar.DAY_OF_MONTH, stop.getActualMaximum(Calendar.DAY_OF_MONTH));

                            break;
                    }
                } else if (i1 == 1) {
                    switch (type) {
                        case 0:
                            start.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                            start.add(Calendar.DATE, 1);
                            stop.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH));
                            break;
                        case 1:
                            start.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                            start.set(Calendar.DAY_OF_MONTH, 1);
                            start.add(Calendar.MONTH, 1);
                            stop.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

                            stop.set(Calendar.MONTH, start.get(Calendar.MONTH));
                            stop.set(Calendar.DAY_OF_MONTH, stop.getActualMaximum(Calendar.DAY_OF_MONTH));
                            break;
                        case 2:
                            start.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                            start.add(Calendar.YEAR, 1);
                            start.set(Calendar.DAY_OF_MONTH, 1);
                            start.set(Calendar.MONTH, 0);
                            stop.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH));
                            stop.set(Calendar.MONTH, 11);
                            stop.set(Calendar.DAY_OF_MONTH, stop.getActualMaximum(Calendar.DAY_OF_MONTH));

                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){


            case R.id.toolbar_confirm:
                int id = MainActivity.appDatabase.appDao().getMaxAimsID()+1;
                //String name = (String) textView.getText();
                List<Aim> aimList = MainActivity.appDatabase.appDao().getAimsDateType(start.get(Calendar.YEAR), start.get(Calendar.MONTH),start.get(Calendar.DAY_OF_MONTH), type);
                if(getResources().getInteger(R.integer.premium_max_goals_one_day) > aimList.size() || MainActivity.valueHolder.isPremiumUser() || MainActivity.valueHolder.getAdsPremiumActive()) {
                    Aim aim = new Aim(id, textView.getText().toString(),type, start, stop);
                    MainActivity.appDatabase.appDao().addAim(aim);
                    MainActivity.needRefresh = true;
                    finish();
                }
                else {
                    if(MainActivity.valueHolder.getAdsPremium()){
                        //Premium reklamowe wygasło
                        Intent adPremiumIntent = new Intent(this, WatchPremiumAdActivity.class);
                        startActivity(adPremiumIntent);
                    } else {
                        Intent premiumIntent = new Intent(this, BuyPremiumActivity.class);
                        premiumIntent.putExtra("messageID", 5);
                        startActivity(premiumIntent);
                    }
                }

                break;

            case R.id.toolbar_cancel:
                finish();
                break;
        }
    }
}
