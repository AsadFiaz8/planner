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


public class ActionAdapter extends RecyclerView.Adapter<ActionAdapter.ActionViewHolder>  {


    private Context context;
    private Formatter f = new Formatter();
    private List<Action> actionList;
    int row_index = -1;
    ItemClickListener onItemClickListener;



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
        actionViewHolder.drawable.setTint(action.getColor());
        actionViewHolder.imageView.setImageDrawable(context.getDrawable(action.getIcon()));

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






