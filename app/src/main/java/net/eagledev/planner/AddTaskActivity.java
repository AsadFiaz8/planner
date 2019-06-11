package net.eagledev.planner;

import android.app.DatePickerDialog;
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

import java.util.Calendar;

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
    ImageView dayButton1;
    ImageView dayButton2;
    ImageView dayButton3;
    ImageView dayButton4;
    ImageView dayButton5;
    ImageView dayButton6;
    ImageView dayButton7;
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
        final String[] testLabels = {"main", "other", "cos"};
        TaskLabelAdapter labelAdapter = new TaskLabelAdapter(this,testLabels);
        labelSpinner.setAdapter(labelAdapter);
        labelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                label = testLabels[position];
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

        }


    }

    private void createTask() {
        name = String.valueOf(nameText.getText());
        comment = String.valueOf(commentText.getText());
        gap = Integer.parseInt(String.valueOf(gapText.getText()));


    }

    private void setRepeatLayout(int layout){
        if(layout == 0){
            daysLayout.setVisibility(View.VISIBLE);
            intervalsLayout.setVisibility(View.INVISIBLE);
        } else {
            daysLayout.setVisibility(View.INVISIBLE);
            intervalsLayout.setVisibility(View.VISIBLE);
        }
    }
}
