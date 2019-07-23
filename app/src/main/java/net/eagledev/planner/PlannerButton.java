package net.eagledev.planner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class PlannerButton extends RelativeLayout implements View.OnClickListener {

    private String text;
    private int size;
    ImageButton image;
    TextView textView;


    public PlannerButton(Context context) {
        super(context);
    }

    public PlannerButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.planner_button, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PlannerButton);

        text = typedArray.getString(R.styleable.PlannerButton_text);
        size = typedArray.getInt(R.styleable.PlannerButton_size,0);
        typedArray.recycle();

        initComponents();
        setText(text);
        if(size == 1){
            image.setBackground(getResources().getDrawable(R.drawable.btn_s1));
        }
        if(size == 2){
            image.setBackground(getResources().getDrawable(R.drawable.btn_s2));
        }

    }

    private void initComponents() {
        image = findViewById(R.id.planner_button);
        textView = findViewById(R.id.planner_button_text);
    }


    public void setText(String text) {
        textView.setText(text);
    }

    @Override
    public void onClick(View v) {

    }
}
