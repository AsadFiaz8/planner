package net.eagledev.planner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.eagledev.planner.Interface.ItemClickListener;

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
        routineViewHolder.drawable.setTint(routine.getColor());

        if (routine.isMonday()) {
            pn = "PN  ";
        }else pn ="";
        if (routine.isTuesday()) {
            wt = "WT  ";
        }else wt = "";
        if(routine.isWednesday()){
            sr = "SR  ";
        }else sr="";
        if (routine.isThursday()){
            cz = "CZ  ";
        }else cz="";
        if (routine.isFriday()) {
            pt = "PT  ";
        }else pt="";
        if(routine.isSaturday()){
            so = "SO  ";
        }else  so="";
        if (routine.isSunday()){
            nd = "ND";
        }else nd="";
        routineViewHolder.textViewDays.setText(pn+wt+sr+cz+pt+so+nd);
        if(routine.isMonday() && routine.isTuesday() && routine.isWednesday() && routine.isThursday() && routine.isFriday() && !routine.isSaturday() && !routine.isSunday()){
            routineViewHolder.textViewDays.setText("Dni robocze");
        }
        if(!routine.isMonday() && !routine.isTuesday() && !routine.isWednesday() && !routine.isThursday() && !routine.isFriday() && routine.isSaturday() && routine.isSunday()){
            routineViewHolder.textViewDays.setText("Weekendy");
        }
        if(routine.isMonday() && routine.isTuesday() && routine.isWednesday() && routine.isThursday() && routine.isFriday() && routine.isSaturday() && routine.isSunday()){
            routineViewHolder.textViewDays.setText("Codzinnie");
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

        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTag(this);
            imageView = itemView.findViewById(R.id.routines_image_view);
            textViewTittle = itemView.findViewById(R.id.routine_name);
            textViewHours = itemView.findViewById(R.id.routine_time);
            textViewDays = itemView.findViewById(R.id.routine_loops);
            colorImageView = itemView.findViewById(R.id.routines_color_image_view);
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








