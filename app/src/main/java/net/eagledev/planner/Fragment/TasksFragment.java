package net.eagledev.planner.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.eagledev.planner.Action;
import net.eagledev.planner.Activity.EditActionActivity;
import net.eagledev.planner.Adapter.ActionAdapter;
import net.eagledev.planner.Adapter.TaskAdapter;
import net.eagledev.planner.AddTaskActivity;
import net.eagledev.planner.Formatter;
import net.eagledev.planner.Interface.ItemClickListener;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;
import net.eagledev.planner.Task;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
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
    View view;
    Context context = getContext();
    ItemClickListener itemClickListener;
    ItemClickListener mainListener;

    Formatter f = new Formatter();
    List<List<Task>> taskLister = new ArrayList<>();
    List<RecyclerView> recyclerList = new ArrayList<>();
    List<ItemClickListener> itemListnerList = new ArrayList<>();
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
    // TODO: Rename and change types and number of parameters
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
        SetupList();
        return view;
    }

    private void SetupList() {
        setupRecyclerList();
        setupDates();
        taskLister.clear();


        for (int t = 0; t< 7; t++){
            Calendar date = Calendar.getInstance();
            date.setFirstDayOfWeek(Calendar.MONDAY);
            Log.e("Date1" , f.Date(date));
            Log.e("t" , String.valueOf(t));
            date.add(Calendar.DATE, t);
            Log.e("Date2" , f.Date(date));
            dateNameList.get(t).setText(f.Date(date));
            List<Task> dayTaskList = MainActivity.appDatabase.appDao().getTaskDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
            List<Task> repeatTaskList = MainActivity.appDatabase.appDao().getTasksRepeatType(1);
            List<Task> todayRepeatTypDay = new ArrayList<>();

            for (int i = 0; i< repeatTaskList.size(); i++){
                //Log.e("days", repeatTaskList.get(i).getDays());
                int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
                dayOfWeek -=1;
                if (dayOfWeek<1){
                    dayOfWeek = 7;
                }
                Log.e("day", f.Date(date)+"   "+String.valueOf(date.get(Calendar.DAY_OF_WEEK)));

                if(repeatTaskList.get(i).getDays().charAt(dayOfWeek-1) == '1'){
                    todayRepeatTypDay.add(repeatTaskList.get(i));
                }
            }
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
                    allTaskLists.add(dayTaskList.get(i));
                }
            }
            for (int i = 0 ; i<todayRepeatTypDay.size(); i++){
                boolean isExist = false;
                for (int l = 0; l<allTaskLists.size(); l++){
                    if(dayTaskList.get(i).getId() == allTaskLists.get(l).getId()){
                        //Sparwdzanie czy zadanie o danym id już zostało dodane
                        isExist = true;
                    }
                }
                if (!isExist){
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
                            allTaskLists.add(task);
                        }
                    }
                }
            }
            taskLister.add(allTaskLists);

            int taskDay = t;
            recyclerList.get(taskDay).setHasFixedSize(true);
            recyclerList.get(taskDay).setLayoutManager(new LinearLayoutManager(context));
            final List<Task> tList = taskLister.get(taskDay);
            itemListnerList.add(new ItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    //TODO listenery do ogarnięcia
                    Task task = tList.get(position);
                    Intent intentEdit = new Intent(context, AddTaskActivity.class);
                    intentEdit.putExtra("ID", task.getId());
                    startActivityForResult(intentEdit, 1);
                }
            });
            adapter = new TaskAdapter(context, taskLister.get(taskDay) ,itemListnerList.get(taskDay));
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

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
