package net.eagledev.planner.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import net.eagledev.planner.Aim;
import net.eagledev.planner.Formatter;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;

import java.util.Calendar;

public class EditAimActivity extends Activity implements View.OnClickListener {

    Button b;
    NumberPicker typePicker;
    NumberPicker timePickrer;
    Formatter f = new Formatter();
    int type = 0;
    Calendar start = Calendar.getInstance();
    Calendar stop = Calendar.getInstance();
    Calendar now = Calendar.getInstance();
    TextView textView;
    Aim selectedAim;
    boolean completed;
    int id;
    Switch aimSwitch;
    ImageView imageDelete;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aim);

        SetValues();
        setupPickers();
        findViewById(R.id.toolbar_confirm).setOnClickListener(this);
        findViewById(R.id.toolbar_cancel).setOnClickListener(this);
        imageDelete = findViewById(R.id.toolbar_delete);
        imageDelete.setVisibility(View.VISIBLE);
        imageDelete.setOnClickListener(this);


    }

    private void SetValues() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        selectedAim = new Aim();
        aimSwitch = findViewById(R.id.aim_switch);
        aimSwitch.setVisibility(View.VISIBLE);
        textView = findViewById(R.id.input_name);
        if(bundle!=null) {
        selectedAim = MainActivity.appDatabase.appDao().idAim((int) bundle.get("ID"));
        if(selectedAim == null){
            finish();
        }
        id = selectedAim.getId();
        type = selectedAim.getType();
        textView.setText(selectedAim.getName());
        start.set(selectedAim.getStartYear(), selectedAim.getStartMonth(), selectedAim.getStartDay());
        stop.set(selectedAim.getYear(), selectedAim.getMonth(), selectedAim.getDay());
        completed = selectedAim.isCompleted();
        aimSwitch.setChecked(completed);
        aimSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                completed = b;
            }
        });
        } else {
            finish();
        }


    }


    private void setupPickers() {

        typePicker = findViewById(R.id.aim_picker1);
        timePickrer = findViewById(R.id.aim_picker2);
        start.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        stop.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        typePicker.setMinValue(0);
        typePicker.setMaxValue(2);
        typePicker.setDisplayedValues(new String[] {String.valueOf(R.string.day), String.valueOf(R.string.month), String.valueOf(R.string.year)});
        typePicker.setValue(type);
        typePicker.setEnabled(false);
        timePickrer.setEnabled(false);
        typePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Calendar now = Calendar.getInstance();
                type = i1;
                switch (i1){
                    case 0:
                        timePickrer.setMinValue(0);
                        timePickrer.setMaxValue(1);
                        timePickrer.setDisplayedValues(new String[] {String.valueOf(R.string.today), String.valueOf(R.string.tomorrow)});
                        timePickrer.setValue(0);
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
                        timePickrer.setValue(0);
                        break;

                    case 2:
                        timePickrer.setMinValue(0);
                        timePickrer.setMaxValue(1);
                        timePickrer.setDisplayedValues(new String[] {String.valueOf(now.get(Calendar.YEAR)),String.valueOf(now.get(Calendar.YEAR)+1)});
                        timePickrer.setValue(0);
                        break;
                }

            }
        });
        timePickrer.setMinValue(0);
        timePickrer.setMaxValue(1);
        timePickrer.setDisplayedValues(new String[] {String.valueOf(R.string.today), String.valueOf(R.string.tomorrow)});
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
                Aim aim = new Aim(id, textView.getText().toString(),type, start, stop, completed);
                MainActivity.appDatabase.appDao().updateAim(aim);
                refresh();
                finish();
                break;

            case R.id.toolbar_cancel:
                finish();
                break;

            case R.id.toolbar_delete:
                MainActivity.appDatabase.appDao().deleteAim(selectedAim.getId());
                refresh();
                finish();
                Toast.makeText(getApplicationContext(), R.string.aim_deleted, Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void refresh(){
        MainActivity.needRefresh = true;
        Intent intent = new Intent();
        intent.putExtra("message_return", "refresh");
        setResult(RESULT_OK, intent);
    }
}

