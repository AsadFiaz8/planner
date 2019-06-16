package net.eagledev.planner;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "AddTaskActivity";
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

    boolean repeat = false;
    Checker checker = new Checker();
    int repeatType = 0;
    int priority = 0;
    String days = "0000000";
    Context context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        setup();
        setValues();

    }

    private void setValues() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!= null){
            if (bundle.getBoolean("edit")){
                Task task = MainActivity.appDatabase.appDao().idTask(bundle.getInt("ID"));
                //TODO fchuj rzeczy tu
            }
        }
    }

    private void setup() {
        context = this;
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
                if(position > 0){
                    repeat = true;
                } else repeat = false;
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
        labelList.add("Dodaj");
        ArrayAdapter<String> labelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, labelList);
        labelSpinner.setAdapter(labelAdapter);
        labelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == labelList.size()-1){
                    addLabel();
                } else {
                    label = labelList.get(position);
                }

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
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        dateButton.setText(f.Date(calendar));
        setRepeatLayout(0);
    }

    private void addLabel() {
        Toast.makeText(this, "Adding new label", Toast.LENGTH_LONG).show();
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
                        dateButton.setText(f.Date(calendar));
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
            case R.id.task_mo:
                setDay(1, dayButton1);
                break;
            case R.id.task_tu:
                setDay(2, dayButton2);
                break;
            case R.id.task_we:
                setDay(3, dayButton3);
                break;
            case R.id.task_th:
                setDay(4, dayButton4);
                break;
            case R.id.task_fr:
                setDay(5, dayButton5);
                break;
            case R.id.task_sa:
                setDay(6, dayButton6);
                break;
            case R.id.task_su:
                setDay(7, dayButton7);
                break;

        }


    }

    private void setDay(int i, TextView textView) {
        int index = i-1;
        int[] ints = {0};
        int[][] all = {ints};
        int[] colorBackground = {getColor(R.color.background)};
        int[] colorAccent = {getColor(R.color.colorAccent)};
        if(days.charAt(index)=='0'){
            textView.setBackgroundTintList(new ColorStateList(all, colorAccent));
            if(index!=7){
                days = days.substring(0, index)+"1"+days.substring(index+1);
            } else days = days.substring(0, index)+"1";

        } else {
            textView.setBackgroundTintList(new ColorStateList(all, colorBackground));
            if(i!=7){
                days = days.substring(0, index)+"0"+days.substring(index+1);
            } else days = days.substring(0, index)+"0";
        }
    }


    private void createTask() {
        name = String.valueOf(nameText.getText());
        comment = String.valueOf(commentText.getText());
        gap = Integer.parseInt(String.valueOf(gapText.getText()));

        if(name.length() <= 3){
            Toast.makeText(this, "Nazwa musi składać się z minimum 3 znaków", Toast.LENGTH_LONG).show();
        } else if(!checker.DateTimeInFuture(calendar)){
            Toast.makeText(this, "Data nie może być z przeszłości", Toast.LENGTH_LONG).show();
        } else {

            try {
                int id = MainActivity.appDatabase.appDao().getMaxTasksID()+1;
                Task task = new Task(id, name, priority, comment, calendar.getTimeInMillis(), repeat, false, repeatType, gap, timeType, days, label);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(task.getTime());
                Log.e(TAG, "\nId: " + task.getId()+
                        "\nName: "+task.getName()+
                        "\nPriority: "+task.getPriority()+
                        "\nComment: "+task.getComment()+
                        "\nTime:" + f.dateWithTime(cal)+
                        "\nRepeat: "+task.isRepeat()+
                        "\nReminder: "+task.isReminder()+
                        "\nRepeat Type: "+task.getRepeat_type()+
                        "\nRepeat Gap: "+task.getRepeat_gap()+
                        "\nTime Type: "+task.getTime_type()+
                        "\nDays: " +task.getDays());
                MainActivity.appDatabase.appDao().addTask(task);
                MainActivity.needRefresh = true;
                finish();

            } catch (Exception e){
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                Log.e(TAG, e.getMessage());
                finish();
            }
        }



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
