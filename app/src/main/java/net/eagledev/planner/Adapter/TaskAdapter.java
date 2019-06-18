package net.eagledev.planner.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import net.eagledev.planner.Aim;
import net.eagledev.planner.Checker;
import net.eagledev.planner.Formatter;
import net.eagledev.planner.Interface.ItemClickListener;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;
import net.eagledev.planner.Task;

import java.util.Calendar;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private Context context;
    private Formatter f = new Formatter();
    private List<Task> taskList;
    ItemClickListener itemClickListener;
    ItemClickListener mainListener;
    boolean onList;

    public TaskAdapter(Context context, List<Task> taskList, ItemClickListener itemClickListener){
        this.context = context;
        this.taskList = taskList;
        this.itemClickListener = itemClickListener;
    }

    public TaskAdapter(Context context, List<Task> taskList, ItemClickListener itemClickListener, ItemClickListener mainListener){
        this.context = context;
        this.taskList = taskList;
        this.itemClickListener = itemClickListener;
        this.mainListener = mainListener;

    }

    public TaskAdapter(Context context, List<Task> taskList, ItemClickListener itemClickListener, boolean onList){
        this.context = context;
        this.taskList = taskList;
        this.itemClickListener = itemClickListener;
        this.onList = onList;
    }

    public TaskAdapter(Context context, List<Task> taskList, ItemClickListener itemClickListener, ItemClickListener mainListener, boolean onList){
        this.context = context;
        this.taskList = taskList;
        this.itemClickListener = itemClickListener;
        this.mainListener = mainListener;
        this.onList = onList;
    }

    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task_list_layout, null);
        return new TaskAdapter.TaskViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder taskViewHolder, int i) {

        Task task = taskList.get(i);
        taskViewHolder.name.setText(task.getName());
        Checker checker = new Checker();
        if(task.getRepeat_type()>0){
            Calendar now = Calendar.getInstance();
            now = task.usingDate();
            Calendar cal = task.CompletedTime();
            if(checker.BeforeDay(cal, now)){
                taskViewHolder.checker.setImageDrawable(MainActivity.drawables.get(1));
            } else {
                taskViewHolder.checker.setImageDrawable(MainActivity.drawables.get(0));
            }
            taskViewHolder.repeatIcon.setVisibility(View.VISIBLE);

        } else {
            taskViewHolder.repeatIcon.setVisibility(View.INVISIBLE);
            if(task.isCompleted()) {
                taskViewHolder.checker.setImageDrawable(MainActivity.drawables.get(0));
            } else taskViewHolder.checker.setImageDrawable(MainActivity.drawables.get(1));
        }

        switch (task.getPriority()){
            case 1:
                taskViewHolder.priorityIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.priority1));
                break;
            case 2:
                taskViewHolder.priorityIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.priority2));
                break;
            case 3:
                taskViewHolder.priorityIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.priority3));
                break;
            case 4:
                taskViewHolder.priorityIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.priority4));
                break;

                default:
                    taskViewHolder.priorityIcon.setVisibility(View.INVISIBLE);
                    break;
        }




    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView name, time;
        ImageView checker, repeatIcon, priorityIcon;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checker = itemView.findViewById(R.id.task_list_button);
            name = itemView.findViewById(R.id.task_list_text);
            time = itemView.findViewById(R.id.task_list_time);
            repeatIcon = itemView.findViewById(R.id.task_repeat_icon);
            priorityIcon = itemView.findViewById(R.id.task_priority_icon);

            if(onList){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mainListener.onClick(view, getAdapterPosition());
                    }
                });

            } else {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        mainListener.onClick(view, getAdapterPosition());
                        return false;
                    }
                });
            }

            checker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    itemClickListener.onClick(view, getAdapterPosition());
                }
            });

        }
    }
}
