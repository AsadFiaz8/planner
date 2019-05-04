package net.eagledev.planner.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import net.eagledev.planner.Formatter;
import net.eagledev.planner.Interface.ItemClickListener;
import net.eagledev.planner.R;
import net.eagledev.planner.Reminder;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

    private Context context;
    private Formatter f = new Formatter();
    private List<Reminder> reminderList;
    ItemClickListener onItemClickListener;

    public ReminderAdapter(Context context, List<Reminder> reminderList) {
        this.context = context;
        this.reminderList = reminderList;
    }
    public ReminderAdapter(Context context, List<Reminder> reminderList, ItemClickListener clickListener){
        this.context = context;
        this.reminderList = reminderList;
        this.onItemClickListener = clickListener;

    }


    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reminders_list_layout, null);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder reminderViewHolder, int i) {

        Reminder reminder = reminderList.get(i);
        reminderViewHolder.reminderName.setText(reminder.getName());
        reminderViewHolder.reminderDate.setText(f.DateText(reminder.getDate()));
        reminderViewHolder.reminderTime.setText(f.Time(reminder.getDate()));
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    class ReminderViewHolder extends RecyclerView.ViewHolder {
        TextView reminderName, reminderDate, reminderTime;
        public ReminderViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setTag(this);
            reminderName = itemView.findViewById(R.id.reminder_name);
            reminderDate = itemView.findViewById(R.id.reminder_date);
            reminderTime = itemView.findViewById(R.id.reminader_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(v, getAdapterPosition());
                }
            });
        }
    }
}
