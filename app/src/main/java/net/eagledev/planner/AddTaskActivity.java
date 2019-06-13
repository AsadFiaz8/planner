package net.eagledev.planner;

import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView toolbar_confirm;
    ImageView toolbar_cancel;
    TextView nameText;
    Button dateButton;
    Spinner repeatSpinner;
    EditText gapText;
    Spinner timeSpinner;
    Spinner labelSpinner;
    ImageButton priorityButton1;
    ImageButton priorityButton2;
    ImageButton priorityButton3;
    ImageButton priorityButton4;
    LinearLayout daysLayout;
    LinearLayout intervalsLayout;
    EditText commentText;
    TextView dayButton1;
    TextView dayButton2;
    TextView dayButton3;
    TextView dayButton4;
    TextView dayButton5;
    TextView dayButton6;
    TextView dayButton7;
    String name;
    String comment;
    int year;
    int month;
    int day;
    int hour;
    int minute;
    int time;
    Calendar calendar;
    Formatter f = new Formatter();
    int timeType;
    int gap;
    String label;
    List<String> labelList = new ArrayList<String>();

    int repeatType;
    int priority;
    String days = "0000000";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        setup();

    }

    private void setup() {
        daysLayout = findViewById(R.id.task_days_layout);
        intervalsLayout = findViewById(R.id.task_other_layout);
        nameText = findViewById(R.id.task_name);
        dateButton = findViewById(R.id.task_date_button);
        dateButton.setOnClickListener(this);
        repeatSpinner = findViewById(R.id.task_repeat_spinner);
        ArrayAdapter<CharSequence> repeatAdapter = ArrayAdapter.createFromResource(this,
                R.array.task_repeat_array,
                android.R.layout.simple_spinner_item);
        repeatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatSpinner.setAdapter(repeatAdapter);
        repeatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                repeatType = position;
                setRepeatLayout(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        gapText = findViewById(R.id.task_amout_edittext);
        timeSpinner = findViewById(R.id.task_time_spinner);
        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(this,
                R.array.task_time_array,
                android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);
        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timeType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        labelSpinner = findViewById(R.id.task_label_spinner);
        labelList.add("main");
        ArrayAdapter<String> labelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, labelList);
        labelSpinner.setAdapter(labelAdapter);
        labelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                label = labelList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        priorityButton1 = findViewById(R.id.task_priority1);
        priorityButton2 = findViewById(R.id.task_priority2);
        priorityButton3 = findViewById(R.id.task_priority3);
        priorityButton4 = findViewById(R.id.task_priority4);
        priorityButton1.setOnClickListener(this);
        priorityButton2.setOnClickListener(this);
        priorityButton3.setOnClickListener(this);
        priorityButton4.setOnClickListener(this);
        commentText = findViewById(R.id.task_comment);
        dayButton1 = findViewById(R.id.task_mo);
        dayButton2 = findViewById(R.id.task_tu);
        dayButton3 = findViewById(R.id.task_we);
        dayButton4 = findViewById(R.id.task_th);
        dayButton5 = findViewById(R.id.task_fr);
        dayButton6 = findViewById(R.id.task_sa);
        dayButton7 = findViewById(R.id.task_su);
        dayButton1.setOnClickListener(this);
        dayButton2.setOnClickListener(this);
        dayButton3.setOnClickListener(this);
        dayButton4.setOnClickListener(this);
        dayButton5.setOnClickListener(this);
        dayButton6.setOnClickListener(this);
        dayButton7.setOnClickListener(this);
        toolbar_confirm = findViewById(R.id.toolbar_confirm);
        toolbar_confirm.setOnClickListener(this);
        toolbar_cancel = findViewById(R.id.toolbar_cancel);
        toolbar_cancel.setOnClickListener(this);
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        dateButton.setText(f.DateText(calendar));
        setRepeatLayout(0);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.toolbar_confirm:
                createTask();
                break;
            case R.id.toolbar_cancel:
                finish();
                break;
            case R.id.task_date_button:
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int dyear, int dmonth, int ddayOfMonth) {
                        year = dyear;
                        month = dmonth;
                        day = ddayOfMonth;
                        calendar .set(year, month, day);
                        time = (int) calendar.getTimeInMillis();
                        dateButton.setText(f.DateText(calendar));
                    }
                },year, month, day);
                datePickerDialog.show();
                break;
                case R.id.task_priority1:
                    setPriority(1);
                    break;
            case R.id.task_priority2:
                setPriority(2);
                break;
            case R.id.task_priority3:
                setPriority(3);
                break;
            case R.id.task_priority4:
                setPriority(4);
                break;

        }


    }

    private void createTask() {
        name = String.valueOf(nameText.getText());
        comment = String.valueOf(commentText.getText());
        gap = Integer.parseInt(String.valueOf(gapText.getText()));


    }

    private void setRepeatLayout(int layout){
        switch (layout){
            case 0:
                daysLayout.setVisibility(View.INVISIBLE);
                intervalsLayout.setVisibility(View.INVISIBLE);
                break;
            case 1:
                daysLayout.setVisibility(View.VISIBLE);
                intervalsLayout.setVisibility(View.INVISIBLE);
                break;

            case 2:
                daysLayout.setVisibility(View.INVISIBLE);
                intervalsLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setPriority(int priority){
        this.priority = priority;
        int[] ints = {0};
        int[][] all = {ints};
        int[] colorBackground = {getColor(R.color.background)};
        int[] colorAccent = {getColor(R.color.colorAccent)};
        priorityButton1.setBackgroundTintList(new ColorStateList(all,colorBackground));
        priorityButton2.setBackgroundTintList(new ColorStateList(all,colorBackground));
        priorityButton3.setBackgroundTintList(new ColorStateList(all,colorBackground));
        priorityButton4.setBackgroundTintList(new ColorStateList(all,colorBackground));
        switch (priority){
            case 1:
                priorityButton1.setBackgroundTintList(new ColorStateList(all,colorAccent));
                break;
            case 2:
                priorityButton2.setBackgroundTintList(new ColorStateList(all,colorAccent));
                break;
            case 3:
                priorityButton3.setBackgroundTintList(new ColorStateList(all,colorAccent));
                break;
            case 4:
                priorityButton4.setBackgroundTintList(new ColorStateList(all,colorAccent));
                break;
        }
    }
}
