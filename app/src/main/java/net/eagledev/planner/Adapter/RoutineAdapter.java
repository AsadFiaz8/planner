package net.eagledev.planner.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.eagledev.planner.Formatter;
import net.eagledev.planner.Interface.ItemClickListener;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;
import net.eagledev.planner.Routine;

import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder> {



    private Context context;
    private Formatter f = new Formatter();
    private List<Routine> routineList;

    ItemClickListener onItemClickListener;

    public RoutineAdapter(Context context, List<Routine> routineList, ItemClickListener clickListener) {
        this.context = context;
        this.routineList = routineList;
        this.onItemClickListener = clickListener;

    }

    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.routines_list_layuot, null);
        return new RoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder routineViewHolder, int i) {
        Routine routine = routineList.get(i);
        String pn, wt, sr, cz, pt, so, nd;
        routineViewHolder.textViewTittle.setText(routine.getName());
        routineViewHolder.textViewHours.setText(f.Time(routine.getStart())+" - "+ f.Time(routine.getStop()));
        routineViewHolder.imageView.setImageDrawable(context.getDrawable(routine.getIcon()));



        int color = routine.getColor();

        if(color < MainActivity.colors.length && color >= 0){
            color = routine.getColor();
        } else {
            for(int l = 0; l<MainActivity.colors.length; l++){
                if(routine.getColor() == MainActivity.colors[i]){
                    color = l;
                }
            }
            if(color >= MainActivity.colors.length || color < 0) color = 0;
        }
        color = MainActivity.colors[color];

        routineViewHolder.drawable.setTint(color);
        int[] ints = {0};
        int[][] all = {ints};
        int[] colors = {routine.getColor()};
        routineViewHolder.relativeLayout.setBackgroundTintList(new ColorStateList(all,colors));

        if (routine.isMonday()) {
            pn = context.getString(R.string.pn);
            pn = pn+ "  ";
        }else pn ="";
        if (routine.isTuesday()) {
            wt = context.getString(R.string.wt);
            wt = wt+ "  ";
        }else wt = "";
        if(routine.isWednesday()){
            sr = context.getString(R.string.sr);
            sr = sr+ "  ";
        }else sr="";
        if (routine.isThursday()){
            cz = context.getString(R.string.cz);
            cz = cz+ "  ";
        }else cz="";
        if (routine.isFriday()) {
            pt = context.getString(R.string.pt);
            pt = pt+ "  ";
        }else pt="";
        if(routine.isSaturday()){
            so = context.getString(R.string.so);
            so = so+ "  ";
        }else  so="";
        if (routine.isSunday()){
            nd = context.getString(R.string.nd);
        }else nd="";
        routineViewHolder.textViewDays.setText(pn+wt+sr+cz+pt+so+nd);
        if(routine.isMonday() && routine.isTuesday() && routine.isWednesday() && routine.isThursday() && routine.isFriday() && !routine.isSaturday() && !routine.isSunday()){
            routineViewHolder.textViewDays.setText(context.getResources().getString(R.string.work_days));
        }
        if(!routine.isMonday() && !routine.isTuesday() && !routine.isWednesday() && !routine.isThursday() && !routine.isFriday() && routine.isSaturday() && routine.isSunday()){
            routineViewHolder.textViewDays.setText(context.getResources().getString(R.string.weekends));
        }
        if(routine.isMonday() && routine.isTuesday() && routine.isWednesday() && routine.isThursday() && routine.isFriday() && routine.isSaturday() && routine.isSunday()){
            routineViewHolder.textViewDays.setText(context.getResources().getString(R.string.everyday));
        }
    }

    @Override
    public int getItemCount() {
        return routineList.size();
    }

    class RoutineViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView, colorImageView;
        TextView textViewTittle, textViewHours, textViewDays;
        Drawable drawable;
        RelativeLayout relativeLayout;

        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTag(this);
            imageView = itemView.findViewById(R.id.routines_image_view);
            textViewTittle = itemView.findViewById(R.id.routine_name);
            textViewHours = itemView.findViewById(R.id.routine_time);
            textViewDays = itemView.findViewById(R.id.routine_loops);
            colorImageView = itemView.findViewById(R.id.routines_color_image_view);
            relativeLayout = itemView.findViewById(R.id.routines_layout);
            drawable = colorImageView.getDrawable();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(view, getAdapterPosition());
                }
            });
        }
    }
}








