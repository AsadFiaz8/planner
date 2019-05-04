package net.eagledev.planner.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;

import net.eagledev.planner.Activity.AddActionAtivity;
import net.eagledev.planner.R;

import java.util.ArrayList;
import java.util.List;

public class SelectIconActivity extends Activity implements View.OnClickListener{

    GridLayout gridLayout;
    Button imageButton;
    List<ImageButton> iconButtns = new ArrayList<>();
    AddActionAtivity addActionAtivity;
    int iconID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_icon);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.9),(int)(height*.8));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
        setButtons();

        Button b1 = new Button(getApplicationContext());
        b1.setText("Testowy button");

    }

    private  void setButtons() {
        ImageButton imageButton1 = findViewById(R.id.image_button1);
        imageButton1.setOnClickListener(this);
        ImageButton imageButton2 = findViewById(R.id.image_button2);
        imageButton2.setOnClickListener(this);
        ImageButton imageButton3 = findViewById(R.id.image_button3);
        imageButton3.setOnClickListener(this);
        ImageButton imageButton4 = findViewById(R.id.image_button4);
        imageButton4.setOnClickListener(this);
        ImageButton imageButton5 = findViewById(R.id.image_button5);
        imageButton5.setOnClickListener(this);
        ImageButton imageButton6 = findViewById(R.id.image_button6);
        imageButton6.setOnClickListener(this);
        ImageButton imageButton7 = findViewById(R.id.image_button7);
        imageButton7.setOnClickListener(this);
        ImageButton imageButton8 = findViewById(R.id.image_button8);
        imageButton8.setOnClickListener(this);
        ImageButton imageButton9 = findViewById(R.id.image_button9);
        imageButton9.setOnClickListener(this);
        ImageButton imageButton10 = findViewById(R.id.image_button10);
        imageButton10.setOnClickListener(this);
        ImageButton imageButton11 = findViewById(R.id.image_button11);
        imageButton11.setOnClickListener(this);
        ImageButton imageButton12 = findViewById(R.id.image_button12);
        imageButton12.setOnClickListener(this);
        ImageButton imageButton13 = findViewById(R.id.image_button13);
        imageButton13.setOnClickListener(this);
        ImageButton imageButton14 = findViewById(R.id.image_button14);
        imageButton14.setOnClickListener(this);
        ImageButton imageButton15 = findViewById(R.id.image_button15);
        imageButton15.setOnClickListener(this);
        ImageButton imageButton16 = findViewById(R.id.image_button16);
        imageButton16.setOnClickListener(this);
        ImageButton imageButton17 = findViewById(R.id.image_button17);
        imageButton17.setOnClickListener(this);
        ImageButton imageButton18 = findViewById(R.id.image_button18);
        imageButton18.setOnClickListener(this);
        ImageButton imageButton19 = findViewById(R.id.image_button19);
        imageButton19.setOnClickListener(this);
        ImageButton imageButton20 = findViewById(R.id.image_button20);
        imageButton20.setOnClickListener(this);
        //ImageButton imageButton21 = findViewById(R.id.image_button21);
        //imageButton21.setOnClickListener(this);
        /*
        ImageButton imageButton22 = findViewById(R.id.image_button22);
        imageButton22.setOnClickListener(this);
        ImageButton imageButton23 = findViewById(R.id.image_button23);
        imageButton23.setOnClickListener(this);
        ImageButton imageButton24 = findViewById(R.id.image_button24);
        imageButton24.setOnClickListener(this);
        ImageButton imageButton25 = findViewById(R.id.image_button25);
        imageButton25.setOnClickListener(this);
        ImageButton imageButton26 = findViewById(R.id.image_button26);
        imageButton26.setOnClickListener(this);
        ImageButton imageButton27 = findViewById(R.id.image_button27);
        imageButton27.setOnClickListener(this);
        ImageButton imageButton28 = findViewById(R.id.image_button28);
        imageButton28.setOnClickListener(this);
        ImageButton imageButton29 = findViewById(R.id.image_button29);
        imageButton29.setOnClickListener(this);
        ImageButton imageButton30 = findViewById(R.id.image_button30);
        imageButton30.setOnClickListener(this);
        */
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_button1:
                iconID = 1;
                CloseActivity();
                break;
            case R.id.image_button2:
                System.out.println("Button 2 clicked");
                iconID = 2;
                CloseActivity();
                break;
            case R.id.image_button3:
                System.out.println("Button 3 clicked");
                iconID = 3;
                CloseActivity();
                break;
            case R.id.image_button4:
                System.out.println("Button 4 clicked");
                iconID = 4;
                CloseActivity();
                break;
            case R.id.image_button5:
                System.out.println("Button 5 clicked");
                iconID = 5;
                CloseActivity();
                break;
            case R.id.image_button6:
                System.out.println("Button 6 clicked");
                iconID = 6;
                CloseActivity();
                break;
            case R.id.image_button7:
                System.out.println("Button 7 clicked");
                iconID = 7;
                CloseActivity();
                break;
            case R.id.image_button8:
                System.out.println("Button 8 clicked");
                iconID = 8;
                CloseActivity();
                break;
            case R.id.image_button9:
                System.out.println("Button 9 clicked");
                iconID = 9;
                CloseActivity();
                break;
            case R.id.image_button10:
                System.out.println("Button 10 clicked");
                iconID = 10;
                CloseActivity();
                break;
            case R.id.image_button11:
                System.out.println("Button 11 clicked");
                iconID = 11;
                CloseActivity();
                break;
            case R.id.image_button12:
                System.out.println("Button 12 clicked");
                iconID = 12;
                CloseActivity();
                break;
            case R.id.image_button13:
                System.out.println("Button 13 clicked");
                iconID = 13;
                CloseActivity();
                break;
            case R.id.image_button14:
                System.out.println("Button 14 clicked");
                iconID = 14;
                CloseActivity();
                break;
            case R.id.image_button15:
                System.out.println("Button 15 clicked");
                iconID = 15;
                CloseActivity();
                break;
            case R.id.image_button16:
                System.out.println("Button 16 clicked");
                iconID = 16;
                CloseActivity();
                break;
            case R.id.image_button17:
                System.out.println("Button 17 clicked");
                iconID = 17;
                CloseActivity();
                break;
            case R.id.image_button18:
                System.out.println("Button 18 clicked");
                iconID = 18;
                CloseActivity();
                break;
            case R.id.image_button19:
                System.out.println("Button 19 clicked");
                iconID = 19;
                CloseActivity();
                break;
            case R.id.image_button20:
                System.out.println("Button 20 clicked");
                iconID = 20;
                CloseActivity();
                break;
            //case R.id.image_button21:
              //  System.out.println("Button 21 clicked");
                //iconID = 21;
                //CloseActivity();
                //break;
                /*
            case R.id.image_button22:
                System.out.println("Button 22 clicked");
                iconID = 22;
                CloseActivity();
                break;
            case R.id.image_button23:
                System.out.println("Button 23 clicked");
                iconID = 23;
                CloseActivity();
                break;
            case R.id.image_button24:
                System.out.println("Button 24 clicked");
                iconID = 24;
                CloseActivity();
                break;
            case R.id.image_button25:
                System.out.println("Button 25 clicked");
                iconID = 25;
                CloseActivity();
                break;
            case R.id.image_button26:
                System.out.println("Button 26 clicked");
                iconID = 26;
                CloseActivity();
                break;
            case R.id.image_button27:
                System.out.println("Button 27 clicked");
                iconID = 27;
                CloseActivity();
                break;
            case R.id.image_button28:
                System.out.println("Button 28 clicked");
                iconID = 28;
                CloseActivity();
                break;
            case R.id.image_button29:
                System.out.println("Button 29 clicked");
                iconID = 29;
                CloseActivity();
                break;
            case R.id.image_button30:
                System.out.println("Button 30 clicked");
                iconID = 30;
                CloseActivity();
                break;
                */
        }
    }

    private void CloseActivity() {
        Intent intent = new Intent();
        String s = String.valueOf(iconID);
        intent.putExtra("message_return", s);
        setResult(RESULT_OK, intent);
        System.out.println("Button SELECT clicked");
        finish();
    }
}
