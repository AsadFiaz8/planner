package net.eagledev.planner.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



import net.eagledev.planner.R;

public class TaskLabelAdapter extends ArrayAdapter<String> {

    Context context;
    String[] names;

    TaskLabelAdapter(Context context, String[] names){
        super(context, R.layout.spinner_item);

        this.context = context;
        this.names = names;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = layoutInflater.inflate(R.layout.spinner_item, null);
        TextView t1 = (TextView) row.findViewById(R.id.spinner_text_item);
        t1.setText(names[position]);

        return row;

    }
}
