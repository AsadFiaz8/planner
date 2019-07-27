package net.eagledev.planner;

import android.content.Intent;
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
    ImageView image2;
    ImageView image3;
    ImageView image4;
    ImageView image5;
    TextView textView;
    PlannerButton buttonSkip;
    PlannerButton buttonNext;
    int part = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        image = findViewById(R.id.tut_image);
        image2 = findViewById(R.id.tut_image2);
        image3 = findViewById(R.id.tut_image3);
        image4 = findViewById(R.id.tut_image4);
        image5 = findViewById(R.id.tut_image5);
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
                        image.setVisibility(View.INVISIBLE);
                        image2.setVisibility(View.VISIBLE);
                        image3.setVisibility(View.INVISIBLE);
                        image4.setVisibility(View.INVISIBLE);
                        image5.setVisibility(View.INVISIBLE);
                        textView.setText(getResources().getString(R.string.tut2));
                        break;
                    case 3:
                        image.setVisibility(View.INVISIBLE);
                        image2.setVisibility(View.INVISIBLE);
                        image3.setVisibility(View.VISIBLE);
                        image4.setVisibility(View.INVISIBLE);
                        image5.setVisibility(View.INVISIBLE);
                        textView.setText(getResources().getString(R.string.tut3));
                        break;
                    case 4:
                        image.setVisibility(View.INVISIBLE);
                        image2.setVisibility(View.INVISIBLE);
                        image3.setVisibility(View.INVISIBLE);
                        image4.setVisibility(View.VISIBLE);
                        image5.setVisibility(View.INVISIBLE);
                        textView.setText(getResources().getString(R.string.tut4));
                        break;
                    case 5:
                        image.setVisibility(View.INVISIBLE);
                        image2.setVisibility(View.INVISIBLE);
                        image3.setVisibility(View.INVISIBLE);
                        image4.setVisibility(View.INVISIBLE);
                        image5.setVisibility(View.VISIBLE);
                        textView.setText(getResources().getString(R.string.tut5));
                        break;
                    case 6:
                        MainActivity.valueHolder.setTut(true);
                        Log.d("Tutorial", String.valueOf(MainActivity.valueHolder.isTut()));
                        Intent returnIntent = new Intent();
                        setResult(MainActivity.RESULT_CODE_TUT,returnIntent);
                        finish();


                        break;
                }
            }
        });





    }
}
