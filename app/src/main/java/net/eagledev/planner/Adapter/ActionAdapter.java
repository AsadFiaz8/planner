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

import net.eagledev.planner.Action;
import net.eagledev.planner.Formatter;
import net.eagledev.planner.Interface.ItemClickListener;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;

import java.util.List;


public class ActionAdapter extends RecyclerView.Adapter<ActionAdapter.ActionViewHolder>  {


    private Context context;
    private Formatter f = new Formatter();
    private List<Action> actionList;
    int row_index = -1;
    ItemClickListener onItemClickListener;
    Formatter fo = new Formatter();




    public ActionAdapter(Context context, List<Action> actionList) {
        this.context = context;
        this.actionList = actionList;
    }

    public ActionAdapter(Context context, List<Action> actionList, ItemClickListener clickListener){
        this.context = context;
        this.actionList = actionList;
        this.onItemClickListener = clickListener;

    }

    @NonNull
    @Override
    public ActionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.actions_list_layout, null);
        return new ActionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActionViewHolder actionViewHolder, int i) {
        Action action = actionList.get(i);
        actionViewHolder.textViewTittle.setText(action.getDesc());
        actionViewHolder.textViewDate.setText(f.Date(action.getStart()));
        actionViewHolder.textViewTime.setText(f.Time(action.getStart())+" - "+ f.Time(action.getStop()));
        int color = action.getColor();

        if(color < MainActivity.colors.length && color >= 0){
            color = action.getColor();
        } else {
            for(int l = 0; l<MainActivity.colors.length; l++){
                if(action.getColor() == MainActivity.colors[i]){
                    color = l;
                }
            }
            if(color >= MainActivity.colors.length || color < 0) color = 0;
        }
        color = MainActivity.colors[color];
        actionViewHolder.drawable.setTint(color);
        actionViewHolder.imageView.setImageDrawable(context.getDrawable(fo.newIcon(action.getIcon())));
        int[] ints = {0};
        int[][] all = {ints};
        int[] colors = {action.getColor()};


        //actionViewHolder.colorImageView.setImageDrawable(actionViewHolder.drawable);
    }

    @Override
    public int getItemCount() {
        return actionList.size();
    }

    class ActionViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView, colorImageView;
        TextView textViewTittle, textViewDate, textViewTime;
        Drawable drawable;
        public ActionViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTag(this);
            imageView = itemView.findViewById(R.id.actions_image_view);
            colorImageView = itemView.findViewById(R.id.actions_color_image_view);
            textViewTittle = itemView.findViewById(R.id.action_name);
            textViewDate = itemView.findViewById(R.id.actions_date);
            textViewTime = itemView.findViewById(R.id.action_time);
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






