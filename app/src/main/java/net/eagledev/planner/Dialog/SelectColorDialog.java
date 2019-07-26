package net.eagledev.planner.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Switch;

import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;

public class SelectColorDialog implements View.OnClickListener {

    Context context;
    Dialog dialog;
    int requestCode;
    int colorID, premiumColor;
    SelectColorDialogListener listener;

    public SelectColorDialog(Context context)
    {
        this.context = context;
        try {
            listener = (SelectColorDialogListener) context;
        } catch (Exception e){
            throw new ClassCastException(context.toString() + "must implements SelectColorDialogListener");
        }
    }

    public void ShowDialog(int requestCode){
        this.requestCode = requestCode;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_select_color);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        setListeners();




    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.color_button0:
                colorID = 0;
                setColor();
                dialog.dismiss();
                break;
            case R.id.color_button1:
                colorID = 1;
                setColor();
                dialog.dismiss();
                break;
            case R.id.color_button2:
                colorID = 2;
                setColor();
                dialog.dismiss();
                break;
            case R.id.color_button3:
                colorID = 3;
                setColor();
                dialog.dismiss();
                break;
            case R.id.color_button4:
                colorID = 4;
                setColor();
                dialog.dismiss();
                break;
            case R.id.color_button5:
                colorID = 5;
                setColor();
                dialog.dismiss();
                break;

            case R.id.color_button_green1:
                premiumColor = 6;
                setPremiumColor();
                break;
            case R.id.color_button_green2:
                premiumColor = 7;
                setPremiumColor();
                break;
            case R.id.color_button_green3:
                premiumColor = 8;
                setPremiumColor();
                break;
            case R.id.color_button_green4:
                premiumColor = 9;
                setPremiumColor();
                break;
            case R.id.color_button_green5:
                premiumColor = 10;
                setPremiumColor();
                break;
            case R.id.color_button_green6:
                premiumColor = 11;
                setPremiumColor();
                break;

            case R.id.color_button_yellow1:
                premiumColor = 12;
                setPremiumColor();
                break;
            case R.id.color_button_yellow2:
                premiumColor = 13;
                setPremiumColor();
                break;
            case R.id.color_button_yellow3:
                premiumColor = 14;
                setPremiumColor();
                break;
            case R.id.color_button_yellow4:
                premiumColor = 15;
                setPremiumColor();
                break;
            case R.id.color_button_yellow5:
                premiumColor = 16;
                setPremiumColor();
                break;
            case R.id.color_button_yellow6:
                premiumColor = 17;
                setPremiumColor();
                break;

            case R.id.color_button_orange1:
                premiumColor = 18;
                setPremiumColor();
                break;
            case R.id.color_button_orange2:
                premiumColor = 19;
                setPremiumColor();
                break;
            case R.id.color_button_orange3:
                premiumColor = 20;
                setPremiumColor();
                break;
            case R.id.color_button_orange4:
                premiumColor = 21;
                setPremiumColor();
                break;
            case R.id.color_button_orange5:
                premiumColor = 22;
                setPremiumColor();
                break;
            case R.id.color_button_orange6:
                premiumColor = 23;
                setPremiumColor();
                break;

            case R.id.color_button_red1:
                premiumColor = 24;
                setPremiumColor();
                break;
            case R.id.color_button_red2:
                premiumColor = 25;
                setPremiumColor();
                break;
            case R.id.color_button_red3:
                premiumColor = 26;
                setPremiumColor();
                break;
            case R.id.color_button_red4:
                premiumColor = 27;
                setPremiumColor();
                break;
            case R.id.color_button_red5:
                premiumColor = 28;
                setPremiumColor();
                break;
            case R.id.color_button_red6:
                premiumColor = 29;
                setPremiumColor();
                break;

            case R.id.color_button_violet1:
                premiumColor = 36;
                setPremiumColor();
                break;
            case R.id.color_button_violet2:
                premiumColor = 37;
                setPremiumColor();
                break;
            case R.id.color_button_violet3:
                premiumColor = 38;
                setPremiumColor();
                break;
            case R.id.color_button_violet4:
                premiumColor = 39;
                setPremiumColor();
                break;
            case R.id.color_button_violet5:
                premiumColor = 40;
                setPremiumColor();
                break;
            case R.id.color_button_violet6:
                premiumColor = 41;
                setPremiumColor();
                break;

            case R.id.color_button_blue1:
                premiumColor = 42;
                setPremiumColor();
                break;
            case R.id.color_button_blue2:
                premiumColor = 43;
                setPremiumColor();
                break;
            case R.id.color_button_blue3:
                premiumColor = 44;
                setPremiumColor();
                break;
            case R.id.color_button_blue4:
                premiumColor = 45;
                setPremiumColor();
                break;
            case R.id.color_button_blue5:
                premiumColor = 46;
                setPremiumColor();
                break;
            case R.id.color_button_blue6:
                premiumColor = 47;
                setPremiumColor();
                break;

            case R.id.color_button_navy1:
                premiumColor = 48;
                setPremiumColor();
                break;
            case R.id.color_button_navy2:
                premiumColor = 49;
                setPremiumColor();
                break;
            case R.id.color_button_navy3:
                premiumColor = 50;
                setPremiumColor();
                break;
            case R.id.color_button_navy4:
                premiumColor = 51;
                setPremiumColor();
                break;
            case R.id.color_button_navy5:
                premiumColor = 52;
                setPremiumColor();
                break;
            case R.id.color_button_navy6:
                premiumColor = 53;
                setPremiumColor();
                break;
        }

    }

    private void setColor() {
        listener.getColorPickerDialogColor(requestCode, colorID);
    }

    private void setPremiumColor(){
        if(MainActivity.valueHolder.canUsePremium()){
            colorID = premiumColor;
            listener.getColorPickerDialogColor(requestCode, colorID);
            dialog.dismiss();
        } else {
            NeedPremiumDialog pd = new NeedPremiumDialog(context, 2);
            pd.ShowDialog(context.getString(R.string.premium_reason3));
        }


    }

    private void setListeners() {
        dialog.findViewById(R.id.color_button0).setOnClickListener(this);
        dialog.findViewById(R.id.color_button1).setOnClickListener(this);
        dialog.findViewById(R.id.color_button2).setOnClickListener(this);
        dialog.findViewById(R.id.color_button3).setOnClickListener(this);
        dialog.findViewById(R.id.color_button4).setOnClickListener(this);
        dialog.findViewById(R.id.color_button5).setOnClickListener(this);

        dialog.findViewById(R.id.color_button_green1).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_green2).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_green3).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_green4).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_green5).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_green6).setOnClickListener(this);

        dialog.findViewById(R.id.color_button_yellow1).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_yellow2).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_yellow3).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_yellow4).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_yellow5).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_yellow6).setOnClickListener(this);

        dialog.findViewById(R.id.color_button_orange1).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_orange2).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_orange3).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_orange4).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_orange5).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_orange6).setOnClickListener(this);

        dialog.findViewById(R.id.color_button_red1).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_red2).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_red3).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_red4).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_red5).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_red6).setOnClickListener(this);

        dialog.findViewById(R.id.color_button_violet1).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_violet2).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_violet3).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_violet4).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_violet5).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_violet6).setOnClickListener(this);

        dialog.findViewById(R.id.color_button_blue1).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_blue2).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_blue3).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_blue4).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_blue5).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_blue6).setOnClickListener(this);

        dialog.findViewById(R.id.color_button_navy1).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_navy2).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_navy3).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_navy4).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_navy5).setOnClickListener(this);
        dialog.findViewById(R.id.color_button_navy6).setOnClickListener(this);
    }

    public interface SelectColorDialogListener{
        void getColorPickerDialogColor(int requestCode, int color);
    }

}


