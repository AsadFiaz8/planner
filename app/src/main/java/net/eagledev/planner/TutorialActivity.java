package net.eagledev.planner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class TutorialActivity extends AppCompatActivity {

    ImageView image;
    TextView textView;
    ImageButton buttonSkip;
    ImageButton buttonNext;
    int part = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        image = findViewById(R.id.tut_image);
        textView = findViewById(R.id.tut_text);
        buttonSkip = findViewById(R.id.tut_btn_skip);
        buttonNext = findViewById(R.id.tut_btn_next);
        textView.setText(getResources().getString(R.string.tut1));
        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.valueHolder.setTut(true);
                finish();
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                part++;
                switch (part){
                    case 2:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.tut_img2));
                        textView.setText(getResources().getString(R.string.tut2));
                        break;
                    case 3:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.tut_img3));
                        textView.setText(getResources().getString(R.string.tut3));
                        break;
                    case 4:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.tut_img4));
                        textView.setText(getResources().getString(R.string.tut4));
                        break;
                    case 5:
                        MainActivity.valueHolder.setTut(true);
                        Log.d("Tutorial", String.valueOf(MainActivity.valueHolder.isTut()));
                        finish();
                        break;
                }
            }
        });





    }
}
