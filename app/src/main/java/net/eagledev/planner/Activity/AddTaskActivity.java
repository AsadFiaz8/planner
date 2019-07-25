package net.eagledev.planner.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.eagledev.planner.Checker;
import net.eagledev.planner.Formatter;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.Dialog.NeedPremiumDialog;
import net.eagledev.planner.R;
import net.eagledev.planner.Task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener, NeedPremiumDialog.NeedPremiumDialogListener {

    public static final String TAG = "AddTaskActivity";
    public static final int CODE_NEW_LABEL = 0;
    public static final int CODE_LABEL = 1;
    public static final int CODE_COMMENT = 2;

    int waitLabel;

    boolean canComment = false;
    ImageView toolbar_confirm;
    ImageView toolbar_cancel;
    ImageView toolbar_delete;
    TextView nameText;
    Button dateButton;
    Spinner repeatSpinner;
    EditText gapText;
    Spinner timeSpinner;
    Spinner labelSpinner;
    ImageView priorityButton1;
    ImageView priorityButton2;
    ImageView priorityButton3;
    ImageView priorityButton4;
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
    List<String> labelList = new ArrayList<>();

    Dialog dialog;
    boolean edit;
    int id;
    boolean repeat = false;
    Checker checker = new Checker();
    int repeatType = 0;
    int priority = 0;
    String days = "0000000";
    Context context;
    boolean completed;
    long completedTime;



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
                edit = true;
                toolbar_delete = findViewById(R.id.toolbar_delete);
                toolbar_delete.setVisibility(View.VISIBLE);
                toolbar_delete.setOnClickListener(this);
                Task task = MainActivity.appDatabase.appDao().idTask(bundle.getInt("ID"));
                id = bundle.getInt("ID");
                name = task.getName();
                nameText.setText(name);
                comment = task.getComment();
                commentText.setText(comment);
                calendar.setTimeInMillis(task.getTime());
                dateButton.setText(f.Date(calendar));
                setPriority(task.getPriority());
                label = task.getLabel();
                completed = task.isCompleted();
                for (int i = 0; i < labelList.size(); i++){
                    if(labelList.get(i).equals(label)){
                        labelSpinner.setSelection(i);
                    }
                }

                repeatSpinner.setSelection(task.getRepeat_type());
                repeatType = task.getRepeat_type();
                setRepeatLayout(repeatType);
                if(repeatType > 0){
                    repeat = true;
                } else repeat = false;
                if(repeatType == 1){
                    if(task.getDays().charAt(0)=='1'){
                        setDay(1, dayButton1);
                    }
                    if(task.getDays().charAt(1)=='1'){
                        setDay(2, dayButton2);
                    }
                    if(task.getDays().charAt(2)=='1'){
                        setDay(3, dayButton3);
                    }
                    if(task.getDays().charAt(3)=='1'){
                        setDay(4, dayButton4);
                    }
                    if(task.getDays().charAt(4)=='1'){
                        setDay(5, dayButton5);
                    }
                    if(task.getDays().charAt(5)=='1'){
                        setDay(6, dayButton6);
                    }
                    if(task.getDays().charAt(6)=='1'){
                        setDay(7, dayButton7);
                    }
                }
                if (repeatType == 2){
                    gap = task.getRepeat_gap();
                    timeType = task.getTime_type();
                    timeSpinner.setSelection(timeType);
                    gapText = findViewById(R.id.task_amout_edittext);
                    gapText.setText(String.valueOf(gap));
                }

            }
        }
    }

    private void setup() {
        context = this;
        daysLayout = findViewById(R.id.task_days_layout);
        intervalsLayout = findViewById(R.id.task_other_layout);
        nameText = findViewById(R.id.task_name);
        dateButton = findViewById(R.id.task_date);
        dateButton.setOnClickListener(this);
        repeatSpinner = findViewById(R.id.task_repeat_spinner);
        ArrayAdapter<CharSequence> repeatAdapter = ArrayAdapter.createFromResource(this,
                R.array.task_repeat_array,
                R.layout.spinner_layout_regular);
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
                R.layout.spinner_layout_regular);
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
        loadLabelList();
        final ArrayAdapter<String> labelAdapter = new ArrayAdapter<>(this, R.layout.spinner_layout_regular, labelList);
        labelSpinner.setAdapter(labelAdapter);
        labelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == labelList.size()-1){
                    labelSpinner.setSelection(0);
                    if(MainActivity.valueHolder.canUsePremium()){
                        addLabel();
                    } else {
                        NeedPremiumDialog needPremiumDialog = new NeedPremiumDialog(context, CODE_NEW_LABEL);
                        needPremiumDialog.ShowDialog(getString(R.string.reason_create_new_labels));
                    }
                } else {
                    if(position>0){
                        if (MainActivity.valueHolder.canUsePremium()){
                                label = labelList.get(position);
                        } else {
                            labelSpinner.setSelection(0);
                            waitLabel = position;
                            NeedPremiumDialog needPremiumDialog = new NeedPremiumDialog(context, CODE_LABEL);
                            needPremiumDialog.ShowDialog(getString(R.string.reason_select_labels));
                        }
                    }
                    else label = labelList.get(0);
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
        commentText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!canComment){
                    if(MainActivity.valueHolder.canUsePremium()){
                        canComment = true;
                    } else {
                        NeedPremiumDialog needPremiumDialog = new NeedPremiumDialog(context, CODE_COMMENT);
                        needPremiumDialog.ShowDialog(getString(R.string.reason_add_comment_to_task));
                    }
                }

            }
        });
        commentText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!canComment){
                    if(MainActivity.valueHolder.canUsePremium()){
                        canComment = true;
                    } else {
                        NeedPremiumDialog needPremiumDialog = new NeedPremiumDialog(context, CODE_COMMENT);
                        needPremiumDialog.ShowDialog(getString(R.string.reason_add_comment_to_task));
                    }
                }

            }
        });
        commentText.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                if (!canComment){
                    if(MainActivity.valueHolder.canUsePremium()){
                        canComment = true;
                    } else {
                        NeedPremiumDialog needPremiumDialog = new NeedPremiumDialog(context, CODE_COMMENT);
                        needPremiumDialog.ShowDialog(getString(R.string.reason_add_comment_to_task));
                    }
                }
                return false;
            }
        });
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

    private void loadLabelList() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        List<String> firstLabel = new ArrayList<>();
        firstLabel.add(getString(R.string.main));
        String firstJson = gson.toJson(firstLabel);
        String json = sharedPreferences.getString("label_list",firstJson);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        labelList= gson.fromJson(json, type);
        if (labelList == null){
            labelList = new ArrayList<>();
            labelList.add(getString(R.string.main));
        }
        labelList.add(getString(R.string.add));

    }

    private void addLabel() {


        dialog = new Dialog(context);
        dialog.setTitle("New label");
        dialog.setContentView(R.layout.dialog_new_label);
        dialog.show();
        final EditText labelName = dialog.findViewById(R.id.newlabel_name);
        Button labelButton = dialog.findViewById(R.id.newlabel_button);
        labelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labelList.set(labelList.size()-1, String.valueOf(labelName.getText()));
                SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(labelList);
                editor.putString("label_list",json);
                editor.apply();
                labelList.add(getString(R.string.add));
                labelSpinner.setSelection(labelList.size()-2);
                dialog.dismiss();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.toolbar_confirm:
                if(edit){
                    updateTask();
                } else {
                    createTask();

                }
                break;
            case R.id.toolbar_cancel:
                finish();
                break;
            case R.id.toolbar_delete:
                MainActivity.appDatabase.appDao().deleteTask(id);
                setResult(MainActivity.CODE_CREATED);
                finish();
                break;
            case R.id.task_date:
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
                try {
                    datePickerDialog.show();
                } catch (Exception e){
                    Log.e(TAG, e.getMessage());
                }
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

    private void updateTask() {
        name = String.valueOf(nameText.getText());
        comment = String.valueOf(commentText.getText());
        gap = Integer.parseInt(String.valueOf(gapText.getText()));

        if(name.length() <= 3){
            Toast.makeText(this, "Nazwa musi składać się z minimum 3 znaków", Toast.LENGTH_LONG).show();
        } else if(!checker.DateTimeInFuture(calendar)){
            Toast.makeText(this, "Data nie może być z przeszłości", Toast.LENGTH_LONG).show();
        } else {

            try {
                //Log.e(TAG, days);
                if (label.equals(getString(R.string.add))){
                    label = labelList.get(labelList.size()-2);
                }
                Task task = new Task(id, name, priority, comment, calendar.getTimeInMillis(), repeat, false, repeatType, gap, timeType, days, label, completed, completedTime);
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
                MainActivity.appDatabase.appDao().updateTask(task);
                setResult(MainActivity.CODE_CREATED);
                finish();
            } catch (Exception e){
                Log.e(TAG, "updateTask: "+e.getMessage());
            }



        }
    }

    private void setDay(int i, TextView textView) {
        int index = i-1;
        int[] ints = {0};
        int[][] all = {ints};
        int[] colorBackground = {getColor(R.color.background)};
        int[] colorWhite = {getColor(R.color.white)};
        int[] colorAccent = {getColor(R.color.colorAccent)};

        if(days.charAt(index)=='0'){
            textView.setBackground(getDrawable(R.drawable.task_day_selected));
            textView.setTextColor(new ColorStateList(all, colorWhite));
            if(index!=7){
                days = days.substring(0, index)+"1"+days.substring(index+1);
            } else days = days.substring(0, index)+"1";

        } else {
            textView.setBackground(getDrawable(R.drawable.task_day_unselected));
            textView.setTextColor(new ColorStateList(all, colorAccent));
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
                if (label.equals(getString(R.string.add))){
                    label = labelList.get(labelList.size()-2);
                }
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
                setResult(MainActivity.CODE_CREATED);
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
        //priorityButton1.setBackgroundTintList(new ColorStateList(all,colorBackground));
        //priorityButton2.setBackgroundTintList(new ColorStateList(all,colorBackground));
        //priorityButton3.setBackgroundTintList(new ColorStateList(all,colorBackground));
        //priorityButton4.setBackgroundTintList(new ColorStateList(all,colorBackground));
        priorityButton1.setImageDrawable(getDrawable(R.drawable.icon_priority_1_off));
        priorityButton2.setImageDrawable(getDrawable(R.drawable.icon_priority_2_off));
        priorityButton3.setImageDrawable(getDrawable(R.drawable.icon_priority_3_off));
        priorityButton4.setImageDrawable(getDrawable(R.drawable.icon_priority_4_off));
        switch (priority){
            case 1:
                priorityButton1.setImageDrawable(getDrawable(R.drawable.icon_priority_1_on));
                break;
            case 2:
                priorityButton2.setImageDrawable(getDrawable(R.drawable.icon_priority_2_on));
                break;
            case 3:
                priorityButton3.setImageDrawable(getDrawable(R.drawable.icon_priority_3_on));
                break;
            case 4:
                priorityButton4.setImageDrawable(getDrawable(R.drawable.icon_priority_4_on));
                break;
        }
    }

    @Override
    public void getPremiumDialogResultCode(int resultCode) {

        switch (resultCode){
            case CODE_NEW_LABEL:
                addLabel();
                break;
            case CODE_LABEL:
                labelSpinner.setSelection(waitLabel);
                break;
            case CODE_COMMENT:
                canComment = true;
                break;
        }

    }
}
