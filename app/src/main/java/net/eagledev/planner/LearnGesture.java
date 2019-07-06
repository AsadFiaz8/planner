package net.eagledev.planner;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class LearnGesture extends GestureDetector.SimpleOnGestureListener {

    public LearnGesture(MainActivity m){
        this.m = m;
    }
    MainActivity m;
    public boolean left = false;
    public boolean right = false;

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e2.getX() < e1.getX()){

            right = true;
            m.dayRight();
        }
        if(e1.getX() < e2.getX()){

            m.dayLeft();
            left = true;
        }
        return super.onFling(e1, e2, velocityX, velocityY);
    }
}
