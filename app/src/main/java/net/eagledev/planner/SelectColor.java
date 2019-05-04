package net.eagledev.planner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelectColor extends AppCompatActivity implements View.OnClickListener {

    GridLayout gridLayout;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_color);

        gridLayout = findViewById(R.id.grid_color_select);
        for (int i = 0; i < 4; i++){
            Button btn = new Button(this);
            btn.setLayoutParams(new LinearLayout.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT));
            btn.setId(i);
            btn.setBackgroundColor(MainActivity.colors[i]);

        }
        textView=findViewById(R.id.dialog_tittle);
        textView.setText("234253245342545");

    }

    @Override
    public void onClick(View view) {

    }
}
