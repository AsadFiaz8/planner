package net.eagledev.planner;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.selection.ItemDetailsLookup;

public final class MyDetailsLookup {

    private final RecyclerView mRecyclerView;

    MyDetailsLookup(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    /*public ItemDetailsLookup.ItemDetails getItemDetails(MotionEvent e) {
        View view = mRecView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            RecyclerView.ViewHolder holder = mRecView.getChildViewHolder(view);
            if (holder instanceof ActionViewHolder) {
                return ((MyHolder) holder).getItemDetails();
            }
        }
        return null;
    }*/
}
