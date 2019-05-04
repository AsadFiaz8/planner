package net.eagledev.planner;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    Context c;
    int[] images;


    public CustomSpinnerAdapter(@NonNull Context context, String[] names, int[] images) {

        super(context, R.layout.spinner_item, names);
        this.c = context;
        this.images = images;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {




            LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.spinner_item, null);
            ImageView i1 = (ImageView) row.findViewById(R.id.color_item);

            i1.setImageResource(images[position]);


        return row;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_item, null);
        ImageView i1 = (ImageView) row.findViewById(R.id.color_item);

        i1.setImageResource(images[position]);


        return row;
    }
}
