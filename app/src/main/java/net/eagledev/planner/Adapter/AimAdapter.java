package net.eagledev.planner.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import net.eagledev.planner.Aim;
import net.eagledev.planner.Formatter;
import net.eagledev.planner.Interface.ItemClickListener;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;

import java.util.List;

public class AimAdapter extends RecyclerView.Adapter<AimAdapter.AimViewHolder> {

    private Context context;
    private Formatter f = new Formatter();
    private List<Aim> aimsList;
    ItemClickListener itemClickListener;
    ItemClickListener mainListener;
    boolean onList;

    public AimAdapter(Context context, List<Aim> aimList, ItemClickListener itemClickListener){
        this.context = context;
        this.aimsList = aimList;
        this.itemClickListener = itemClickListener;
    }

    public AimAdapter(Context context, List<Aim> aimList, ItemClickListener itemClickListener, ItemClickListener mainListener){
        this.context = context;
        this.aimsList = aimList;
        this.itemClickListener = itemClickListener;
        this.mainListener = mainListener;

    }

    public AimAdapter(Context context, List<Aim> aimList, ItemClickListener itemClickListener, boolean onList){
        this.context = context;
        this.aimsList = aimList;
        this.itemClickListener = itemClickListener;
        this.onList = onList;
    }

    public AimAdapter(Context context, List<Aim> aimList, ItemClickListener itemClickListener, ItemClickListener mainListener, boolean onList){
        this.context = context;
        this.aimsList = aimList;
        this.itemClickListener = itemClickListener;
        this.mainListener = mainListener;
        this.onList = onList;
    }

    @NonNull
    @Override
    public AimViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.aims_list_layout, null);
        return new AimViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AimViewHolder aimViewHolder, int i) {

        Aim aim = aimsList.get(i);
        aimViewHolder.name.setText(aim.getName());
        if(aim.isCompleted()) {

            aimViewHolder.checker.setImageDrawable(MainActivity.drawables.get(0));
        } else aimViewHolder.checker.setImageDrawable(MainActivity.drawables.get(1));

        if(onList){
            switch (aim.getType()) {
                case 0:
                    aimViewHolder.time.setText(f.DateText(aim.getStop()));
                    break;

                case 1:
                    aimViewHolder.time.setText(f.Month(aim.getMonth()));
                    break;

                case 2:
                    aimViewHolder.time.setText("");
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return aimsList.size();
    }

    public class AimViewHolder extends RecyclerView.ViewHolder {
        TextView name, time;
        ImageButton checker;

        public AimViewHolder(@NonNull View itemView) {
            super(itemView);
            checker = itemView.findViewById(R.id.aim_list_button);
            name = itemView.findViewById(R.id.aim_list_text);
            time = itemView.findViewById(R.id.aim_list_time);
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
