package net.eagledev.planner.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import net.eagledev.planner.Adapter.TaskAdapter;
import net.eagledev.planner.Activity.AddTaskActivity;
import net.eagledev.planner.Checker;
import net.eagledev.planner.Formatter;
import net.eagledev.planner.Interface.ItemClickListener;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.PlannerButton;
import net.eagledev.planner.R;
import net.eagledev.planner.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TasksFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TasksFragment extends Fragment {

    Calendar now = Calendar.getInstance();
    RecyclerView recyclerView;
    TaskAdapter adapter;
    List<Task> taskList = new ArrayList<>();
    public static final String TAG = "TasksFragment";
    View view;
    Context context = getContext();
    ItemClickListener itemClickListener;
    ItemClickListener mainListener;
    Checker checker = new Checker();
    Dialog taskInfoDialog;

    Formatter f = new Formatter();
    List<List<Task>> taskLister = new ArrayList<>();
    List<RecyclerView> recyclerList = new ArrayList<>();
    List<ItemClickListener> itemListnerList = new ArrayList<>();
    List<ItemClickListener> longListeneerList = new ArrayList<>();
    List<TextView> dateNameList = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public TasksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TasksFragment.
     */
    public static TasksFragment newInstance(String param1, String param2) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context = getActivity();
        view = inflater.inflate(R.layout.fragment_tasks, container, false);
        ImageButton btnAdd = view.findViewById(R.id.task_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.context, AddTaskActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        SetupList();
        return view;
    }

    private void SetupList() {
        setupRecyclerList();
        setupDates();
        taskLister.clear();


        for (int t = 0; t< 7; t++){
            final Calendar date = Calendar.getInstance();
            date.setFirstDayOfWeek(Calendar.MONDAY);
            Log.e("t" , String.valueOf(t));
            date.add(Calendar.DATE, t);
            dateNameList.get(t).setText(f.Date(date));
            List<Task> dayTaskList = MainActivity.appDatabase.appDao().getTaskDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
            List<Task> repeatTaskList = MainActivity.appDatabase.appDao().getTasksRepeatType(1);
            List<Task> todayRepeatTypDay = new ArrayList<>();

            for (int i = 0; i< repeatTaskList.size(); i++){
                int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
                dayOfWeek -=1;
                if (dayOfWeek<1){
                    dayOfWeek = 7;
                }

                if(repeatTaskList.get(i).getDays().charAt(dayOfWeek-1) == '1'){
                    repeatTaskList.get(i).setUsingDate(date);
                    todayRepeatTypDay.add(repeatTaskList.get(i));
                }
            }
            Log.e(TAG, String.valueOf(todayRepeatTypDay.size()));
            List<Task> todayRepeatTypInterval = MainActivity.appDatabase.appDao().getTasksRepeatType(2);
            List<Task> allTaskLists = new ArrayList<>();
            for (int i = 0 ; i<dayTaskList.size(); i++){
                boolean isExist = false;
                for (int l = 0; l<allTaskLists.size(); l++){
                    if(dayTaskList.get(i).getId() == allTaskLists.get(l).getId()){
                        //Sparwdzanie czy zadanie o danym id już zostało dodane
                        isExist = true;
                    }
                }
                if (!isExist){
                    Log.e("Added" , "2");
                    dayTaskList.get(i).setUsingDate(date);
                    allTaskLists.add(dayTaskList.get(i));
                }
            }

                for (int i = 0 ; i<todayRepeatTypDay.size(); i++){
                    boolean isExist = false;
                    for (int l = 0; l<allTaskLists.size(); l++){
                        if(dayTaskList.size() > 0){
                            if(dayTaskList.get(i).getId() == allTaskLists.get(l).getId()){
                                //Sparwdzanie czy zadanie o danym id już zostało dodane
                                isExist = true;
                            }
                        }

                    }
                    if (!isExist){
                        todayRepeatTypDay.get(i).setUsingDate(date);
                        allTaskLists.add(todayRepeatTypDay.get(i));
                    }
                }


            for (int i = 0 ; i<todayRepeatTypInterval.size(); i++){
                Task task = todayRepeatTypInterval.get(i);
                Calendar tCal = Calendar.getInstance();
                tCal.setTimeInMillis(task.getTime());
                if(task.getTime_type() == 0){
                    //Dni
                    if(Math.abs((tCal.getTimeInMillis()-date.getTimeInMillis())/86400000) % task.getRepeat_gap() == 0){
                        boolean isExist = false;
                        for (int l = 0; l<allTaskLists.size(); l++){
                            if(task.getId() == allTaskLists.get(l).getId()){
                                //Sparwdzanie czy zadanie o danym id już zostało dodane
                                isExist = true;
                            }
                        }
                        if (!isExist){
                            task.setUsingDate(date);
                            allTaskLists.add(task);
                        }
                    }
                }
                if(task.getTime_type() == 1){
                    //Tygodnie
                    if(Math.abs((tCal.getTimeInMillis()-date.getTimeInMillis())/604800000) % task.getRepeat_gap() == 0){
                        boolean isExist = false;
                        for (int l = 0; l<allTaskLists.size(); l++){
                            if(task.getId() == allTaskLists.get(l).getId()){
                                //Sparwdzanie czy zadanie o danym id już zostało dodane
                                isExist = true;
                            }
                        }
                        if (!isExist){
                            task.setUsingDate(date);
                            allTaskLists.add(task);
                        }
                    }
                }
                if(task.getTime_type() == 2){
                    //Miesiące
                    if(((date.get(Calendar.YEAR)*12+date.get(Calendar.MONTH))-(tCal.get(Calendar.YEAR)*12+tCal.get(Calendar.MONTH))) % task.getRepeat_gap() == 0){
                        boolean isExist = false;
                        for (int l = 0; l<allTaskLists.size(); l++){
                            if(task.getId() == allTaskLists.get(l).getId()){
                                //Sparwdzanie czy zadanie o danym id już zostało dodane
                                isExist = true;
                            }
                        }
                        if (!isExist){
                            task.setUsingDate(date);
                            allTaskLists.add(task);
                        }
                    }
                }
            }
            List <Integer> startTimes = new ArrayList<>();
            for(int s = 0; s<allTaskLists.size(); s++){
                startTimes.add(allTaskLists.get(s).getPriority());
            }
            Collections.sort(startTimes);

            List<Task> tsk = new ArrayList<>();
            for (int s = 0;  s < allTaskLists.size(); s++){
                for(int l = 0; l<allTaskLists.size(); l++){
                    if(startTimes.get(s) == allTaskLists.get(l).getPriority()){
                        boolean e = false;
                        for (Task task: tsk){
                            if (task.getId() == allTaskLists.get(l).getId()) e = true;
                        }
                        if (!e) tsk.add(allTaskLists.get(l));
                    }
                }

            }

            taskLister.add(tsk);

            int taskDay = t;
            recyclerList.get(taskDay).setHasFixedSize(true);
            recyclerList.get(taskDay).setLayoutManager(new LinearLayoutManager(context));
            final List<Task> tList = taskLister.get(taskDay);


            longListeneerList.add(new ItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    final Task task = tList.get(position);
                    taskInfoDialog = new Dialog(context);
                    taskInfoDialog.setTitle("Task info");
                    taskInfoDialog.setContentView(R.layout.dialog_task_info);
                    taskInfoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    taskInfoDialog.show();
                    TextView name = taskInfoDialog.findViewById(R.id.dialog_task_info_name);
                    name.setText(task.getName());
                    TextView date = taskInfoDialog.findViewById(R.id.dialog_task_info_date);
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(task.getTime());
                    date.setText(f.Date(c));
                    TextView repeating = taskInfoDialog.findViewById(R.id.dialog_task_info_repeating);

                    switch (task.getRepeat_type()){
                        case 0:
                            repeating.setText(getResources().getString(R.string.no));
                            break;
                        case 2:
                            String timeType="";
                            if(task.getTime_type()==0) timeType = getResources().getString(R.string.days);
                            if(task.getTime_type()==1) timeType = getResources().getString(R.string.weeks);
                            if(task.getTime_type()==2) timeType = getResources().getString(R.string.months);
                            repeating.setText("Co "+task.getRepeat_gap()+" "+timeType);
                            break;
                        case 1:
                            String repeatDays = "";
                            if(task.getDays().charAt(0)=='1') repeatDays = repeatDays + " " +getString(R.string.mo);
                            if(task.getDays().charAt(1)=='1') repeatDays = repeatDays + " " +getString(R.string.tu);
                            if(task.getDays().charAt(2)=='1') repeatDays = repeatDays + " " +getString(R.string.we);
                            if(task.getDays().charAt(3)=='1') repeatDays = repeatDays + " " +getString(R.string.th);
                            if(task.getDays().charAt(4)=='1') repeatDays = repeatDays + " " +getString(R.string.fr);
                            if(task.getDays().charAt(5)=='1') repeatDays = repeatDays + " " +getString(R.string.sa);
                            if(task.getDays().charAt(6)=='1') repeatDays = repeatDays + " " +getString(R.string.su);


                            if (task.getDays().charAt(0)=='1' && task.getDays().charAt(1)=='1' && task.getDays().charAt(2)=='1' && task.getDays().charAt(3)=='1' && task.getDays().charAt(4)=='1'){
                                repeatDays = getString(R.string.work_days);
                            }
                            if (task.getDays().charAt(5)=='1' && task.getDays().charAt(6)=='1'){
                                repeatDays = getString(R.string.weekends);
                            }
                            if (task.getDays().charAt(0)=='1' && task.getDays().charAt(1)=='1' && task.getDays().charAt(2)=='1' && task.getDays().charAt(3)=='1' && task.getDays().charAt(4)=='1' && task.getDays().charAt(5)=='1' && task.getDays().charAt(6)=='1'){
                                repeatDays = getString(R.string.everyday);
                            }
                            repeating.setText(repeatDays);
                            break;
                    }

                    final TextView comment = taskInfoDialog.findViewById(R.id.dialog_task_info_comment);
                    comment.setText(task.getComment());
                    TextView label = taskInfoDialog.findViewById(R.id.dialog_task_info_label);
                    label.setText(task.getLabel());
                    TextView completed = taskInfoDialog.findViewById(R.id.dialog_task_info_completed);
                    if (task.getRepeat_type()>0){
                        if (task.isCompleted()){
                            completed.setText(getResources().getString(R.string.last_completed_from_day)+f.Date(task.CompletedTime()));
                        } else {
                            completed.setText(getResources().getString(R.string.no));
                        }
                    } else {
                        if (task.isCompleted()){
                            completed.setText(getResources().getString(R.string.yes));
                        } else {
                            completed.setText(getResources().getString(R.string.no));
                        }
                    }

                    PlannerButton button = taskInfoDialog.findViewById(R.id.dialog_task_info_button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent editTaskIntent = new Intent(context, AddTaskActivity.class);
                            editTaskIntent.putExtra("ID", task.getId());
                            editTaskIntent.putExtra("edit", true);
                            startActivityForResult(editTaskIntent,0);
                            taskInfoDialog.dismiss();
                        }
                    });
                }
            });

            itemListnerList.add(new ItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Task task = tList.get(position);
                    task.setCompleted(!task.isCompleted());
                    ImageView imageButton = view.findViewById(R.id.task_list_button);
                    if (task.getRepeat_type()>0){
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(task.getCompletedTime());
                        if (checker.Before(calendar, date)){
                            task.setCompletedTime(date.getTimeInMillis());
                            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.task_check_on));
                        } else {
                            task.setCompletedTime(date.getTimeInMillis()-86400000);
                            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.task_check_off));
                        }
                    } else {
                        if(task.isCompleted()) {
                            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.task_check_on));
                        }else {
                            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.task_check_off));
                        }
                    }

                    MainActivity.appDatabase.appDao().updateTask(task);
                    SetupList();
                }
            });


            adapter = new TaskAdapter(context, taskLister.get(taskDay) ,itemListnerList.get(taskDay), longListeneerList.get(taskDay));
            recyclerList.get(taskDay).setAdapter(adapter);


        }



}

    private void setupDates() {
        dateNameList.add((TextView) view.findViewById(R.id.task_name_day1));
        dateNameList.add((TextView) view.findViewById(R.id.task_name_day2));
        dateNameList.add((TextView) view.findViewById(R.id.task_name_day3));
        dateNameList.add((TextView) view.findViewById(R.id.task_name_day4));
        dateNameList.add((TextView) view.findViewById(R.id.task_name_day5));
        dateNameList.add((TextView) view.findViewById(R.id.task_name_day6));
        dateNameList.add((TextView) view.findViewById(R.id.task_name_day7));
    }

    private void setupRecyclerList() {
        recyclerList.add((RecyclerView) view.findViewById(R.id.task_reycler_view1));
        recyclerList.add((RecyclerView) view.findViewById(R.id.task_reycler_view2));
        recyclerList.add((RecyclerView) view.findViewById(R.id.task_reycler_view3));
        recyclerList.add((RecyclerView) view.findViewById(R.id.task_reycler_view4));
        recyclerList.add((RecyclerView) view.findViewById(R.id.task_reycler_view5));
        recyclerList.add((RecyclerView) view.findViewById(R.id.task_reycler_view6));
        recyclerList.add((RecyclerView) view.findViewById(R.id.task_reycler_view7));
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == MainActivity.CODE_CREATED){
            SetupList();
        }
    }
}
