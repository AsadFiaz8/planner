package net.eagledev.planner.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import net.eagledev.planner.MainActivity;
import net.eagledev.planner.R;

public class SelectIconDialog implements View.OnClickListener {
    
    Context context;
    Dialog dialog;
    int requestCode;
    int iconID, premiumIcon;
    SelectIconDialogListener listener;
    
    public SelectIconDialog(Context context){
        this.context = context;
        
        try {
            listener = (SelectIconDialogListener)context;
            
        } catch (Exception e){
            throw new ClassCastException(context.toString()  + "must implements SelectIconDialogListener");
        }
    }
    
    public void ShowDialog(int requestCode){
        this.requestCode = requestCode;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_select_icon);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        setListeners();
    }

    private void setListeners() {
        dialog.findViewById(R.id.image_button1).setOnClickListener(this);
        dialog.findViewById(R.id.image_button2).setOnClickListener(this);
        dialog.findViewById(R.id.image_button3).setOnClickListener(this);
        dialog.findViewById(R.id.image_button4).setOnClickListener(this);
        dialog.findViewById(R.id.image_button5).setOnClickListener(this);
        dialog.findViewById(R.id.image_button6).setOnClickListener(this);
        dialog.findViewById(R.id.image_button7).setOnClickListener(this);
        dialog.findViewById(R.id.image_button8).setOnClickListener(this);
        dialog.findViewById(R.id.image_button9).setOnClickListener(this);
        dialog.findViewById(R.id.image_button10).setOnClickListener(this);
        dialog.findViewById(R.id.image_button11).setOnClickListener(this);
        dialog.findViewById(R.id.image_button12).setOnClickListener(this);
        dialog.findViewById(R.id.image_button13).setOnClickListener(this);
        dialog.findViewById(R.id.image_button14).setOnClickListener(this);
        dialog.findViewById(R.id.image_button15).setOnClickListener(this);
        dialog.findViewById(R.id.image_button16).setOnClickListener(this);
        dialog.findViewById(R.id.image_button17).setOnClickListener(this);
        dialog.findViewById(R.id.image_button18).setOnClickListener(this);
        dialog.findViewById(R.id.image_button19).setOnClickListener(this);
        dialog.findViewById(R.id.image_button20).setOnClickListener(this);

        //Premium icons
        dialog.findViewById(R.id.image_button21).setOnClickListener(this);
        dialog.findViewById(R.id.image_button22).setOnClickListener(this);
        dialog.findViewById(R.id.image_button23).setOnClickListener(this);
        dialog.findViewById(R.id.image_button24).setOnClickListener(this);
        dialog.findViewById(R.id.image_button25).setOnClickListener(this);
        dialog.findViewById(R.id.image_button26).setOnClickListener(this);
        dialog.findViewById(R.id.image_button27).setOnClickListener(this);
        dialog.findViewById(R.id.image_button28).setOnClickListener(this);
        dialog.findViewById(R.id.image_button29).setOnClickListener(this);
        dialog.findViewById(R.id.image_button30).setOnClickListener(this);
        dialog.findViewById(R.id.image_button31).setOnClickListener(this);
        dialog.findViewById(R.id.image_button32).setOnClickListener(this);
        dialog.findViewById(R.id.image_button33).setOnClickListener(this);
        dialog.findViewById(R.id.image_button34).setOnClickListener(this);
        dialog.findViewById(R.id.image_button35).setOnClickListener(this);
        dialog.findViewById(R.id.image_button36).setOnClickListener(this);
        dialog.findViewById(R.id.image_button37).setOnClickListener(this);
        dialog.findViewById(R.id.image_button38).setOnClickListener(this);
        dialog.findViewById(R.id.image_button40).setOnClickListener(this);
        dialog.findViewById(R.id.image_button41).setOnClickListener(this);
        dialog.findViewById(R.id.image_button42).setOnClickListener(this);
        dialog.findViewById(R.id.image_button43).setOnClickListener(this);
        dialog.findViewById(R.id.image_button44).setOnClickListener(this);
        dialog.findViewById(R.id.image_button45).setOnClickListener(this);
        dialog.findViewById(R.id.image_button46).setOnClickListener(this);
        dialog.findViewById(R.id.image_button47).setOnClickListener(this);
        dialog.findViewById(R.id.image_button48).setOnClickListener(this);
        dialog.findViewById(R.id.image_button49).setOnClickListener(this);
        dialog.findViewById(R.id.image_button50).setOnClickListener(this);
        dialog.findViewById(R.id.image_button51).setOnClickListener(this);
        dialog.findViewById(R.id.image_button52).setOnClickListener(this);
        dialog.findViewById(R.id.image_button53).setOnClickListener(this);
        dialog.findViewById(R.id.image_button54).setOnClickListener(this);
        dialog.findViewById(R.id.image_button55).setOnClickListener(this);
        dialog.findViewById(R.id.image_button56).setOnClickListener(this);
        dialog.findViewById(R.id.image_button57).setOnClickListener(this);
        dialog.findViewById(R.id.image_button58).setOnClickListener(this);
        dialog.findViewById(R.id.image_button59).setOnClickListener(this);
        dialog.findViewById(R.id.image_button60).setOnClickListener(this);
        dialog.findViewById(R.id.image_button51).setOnClickListener(this);
        dialog.findViewById(R.id.image_button62).setOnClickListener(this);
        dialog.findViewById(R.id.image_button63).setOnClickListener(this);
        dialog.findViewById(R.id.image_button64).setOnClickListener(this);
        dialog.findViewById(R.id.image_button65).setOnClickListener(this);
        dialog.findViewById(R.id.image_button66).setOnClickListener(this);
        dialog.findViewById(R.id.image_button67).setOnClickListener(this);
        dialog.findViewById(R.id.image_button68).setOnClickListener(this);
        dialog.findViewById(R.id.image_button69).setOnClickListener(this);
        dialog.findViewById(R.id.image_button70).setOnClickListener(this);
        dialog.findViewById(R.id.image_button71).setOnClickListener(this);
        dialog.findViewById(R.id.image_button72).setOnClickListener(this);
        dialog.findViewById(R.id.image_button73).setOnClickListener(this);
        dialog.findViewById(R.id.image_button74).setOnClickListener(this);
        dialog.findViewById(R.id.image_button75).setOnClickListener(this);
        dialog.findViewById(R.id.image_button76).setOnClickListener(this);
        dialog.findViewById(R.id.image_button77).setOnClickListener(this);
        dialog.findViewById(R.id.image_button78).setOnClickListener(this);
        dialog.findViewById(R.id.image_button79).setOnClickListener(this);
        dialog.findViewById(R.id.image_button80).setOnClickListener(this);
        dialog.findViewById(R.id.image_button81).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.image_button1:

                iconID = 0;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;
            case R.id.image_button2:

                iconID = 1;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;
            case R.id.image_button3:

                iconID = 2;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;
            case R.id.image_button4:

                iconID = 3;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;
            case R.id.image_button5:

                iconID = 4;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;
            case R.id.image_button6:

                iconID = 5;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;
            case R.id.image_button7:

                iconID = 6;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;
            case R.id.image_button8:

                iconID = 7;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;
            case R.id.image_button9:

                iconID = 8;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;
            case R.id.image_button10:

                iconID = 9;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;
            case R.id.image_button11:

                iconID = 10;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;
            case R.id.image_button12:

                iconID = 11;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;
            case R.id.image_button13:

                iconID = 12;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;
            case R.id.image_button14:

                iconID = 13;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;
            case R.id.image_button15:

                iconID = 14;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;
            case R.id.image_button16:

                iconID = 15;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;
            case R.id.image_button17:

                iconID = 16;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;
            case R.id.image_button18:

                iconID = 17;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;
            case R.id.image_button19:

                iconID = 18;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;
            case R.id.image_button20:

                iconID = 19;
                listener.getIconPickedIcon(0, iconID);
                dialog.dismiss();
                break;

            //Premium icons
            case R.id.image_button21:
                premiumIcon = 20;
                selectPremiumIcon();
                break;
            case R.id.image_button22:
                premiumIcon = 21;
                selectPremiumIcon();
                break;
            case R.id.image_button23:
                premiumIcon = 22;
                selectPremiumIcon();
                break;
            case R.id.image_button24:
                premiumIcon = 23;
                selectPremiumIcon();
                break;
            case R.id.image_button25:
                premiumIcon = 24;
                selectPremiumIcon();
                break;
            case R.id.image_button26:
                premiumIcon = 25;
                selectPremiumIcon();
                break;
            case R.id.image_button27:
                premiumIcon = 26;
                selectPremiumIcon();
                break;
            case R.id.image_button28:
                premiumIcon = 27;
                selectPremiumIcon();
                break;
            case R.id.image_button29:
                premiumIcon = 28;
                selectPremiumIcon();
                break;
            case R.id.image_button30:
                premiumIcon = 29;
                selectPremiumIcon();
                break;
            case R.id.image_button31:
                premiumIcon = 30;
                selectPremiumIcon();
                break;
            case R.id.image_button32:
                premiumIcon = 31;
                selectPremiumIcon();
                break;
            case R.id.image_button33:
                premiumIcon = 32;
                selectPremiumIcon();
                break;
            case R.id.image_button34:
                premiumIcon = 33;
                selectPremiumIcon();
                break;
            case R.id.image_button35:
                premiumIcon = 34;
                selectPremiumIcon();
                break;
            case R.id.image_button36:
                premiumIcon = 35;
                selectPremiumIcon();
                break;
            case R.id.image_button37:
                premiumIcon = 36;
                selectPremiumIcon();
                break;
            case R.id.image_button38:
                premiumIcon = 37;
                selectPremiumIcon();
                break;
            case R.id.image_button40:
                premiumIcon = 38;
                selectPremiumIcon();
                break;
            case R.id.image_button41:
                premiumIcon = 39;
                selectPremiumIcon();
                break;
            case R.id.image_button42:
                premiumIcon = 40;
                selectPremiumIcon();
                break;
            case R.id.image_button43:
                premiumIcon = 41;
                selectPremiumIcon();
                break;
            case R.id.image_button44:
                premiumIcon = 42;
                selectPremiumIcon();
                break;
            case R.id.image_button45:
                premiumIcon = 43;
                selectPremiumIcon();
                break;
            case R.id.image_button46:
                premiumIcon = 44;
                selectPremiumIcon();
                break;
            case R.id.image_button47:
                premiumIcon = 45;
                selectPremiumIcon();
                break;
            case R.id.image_button48:
                premiumIcon = 46;
                selectPremiumIcon();
                break;
            case R.id.image_button49:
                premiumIcon = 47;
                selectPremiumIcon();
                break;
            case R.id.image_button50:
                premiumIcon = 48;
                selectPremiumIcon();
                break;
            case R.id.image_button51:
                premiumIcon = 49;
                selectPremiumIcon();
                break;
            case R.id.image_button52:
                premiumIcon = 50;
                selectPremiumIcon();
                break;
            case R.id.image_button53:
                premiumIcon = 51;
                selectPremiumIcon();
                break;
            case R.id.image_button54:
                premiumIcon = 52;
                selectPremiumIcon();
                break;
            case R.id.image_button55:
                premiumIcon = 53;
                selectPremiumIcon();
                break;
            case R.id.image_button56:
                premiumIcon = 54;
                selectPremiumIcon();
                break;
            case R.id.image_button57:
                premiumIcon = 55;
                selectPremiumIcon();
                break;
            case R.id.image_button58:
                premiumIcon = 56;
                selectPremiumIcon();
                break;
            case R.id.image_button59:
                premiumIcon = 57;
                selectPremiumIcon();
                break;
            case R.id.image_button60:
                premiumIcon = 58;
                selectPremiumIcon();
                break;
            case R.id.image_button61:
                premiumIcon = 59;
                selectPremiumIcon();
                break;
            case R.id.image_button62:
                premiumIcon = 60;
                selectPremiumIcon();
                break;
            case R.id.image_button63:
                premiumIcon = 61;
                selectPremiumIcon();
                break;
            case R.id.image_button64:
                premiumIcon = 62;
                selectPremiumIcon();
                break;
            case R.id.image_button65:
                premiumIcon = 63;
                selectPremiumIcon();
                break;
            case R.id.image_button66:
                premiumIcon = 64;
                selectPremiumIcon();
                break;
            case R.id.image_button67:
                premiumIcon = 65;
                selectPremiumIcon();
                break;
            case R.id.image_button68:
                premiumIcon = 66;
                selectPremiumIcon();
                break;
            case R.id.image_button69:
                premiumIcon = 67;
                selectPremiumIcon();
                break;
            case R.id.image_button70:
                premiumIcon = 68;
                selectPremiumIcon();
                break;
            case R.id.image_button71:
                premiumIcon = 69;
                selectPremiumIcon();
                break;
            case R.id.image_button72:
                premiumIcon = 70;
                selectPremiumIcon();
                break;
            case R.id.image_button73:
                premiumIcon = 71;
                selectPremiumIcon();
                break;
            case R.id.image_button74:
                premiumIcon = 72;
                selectPremiumIcon();
                break;
            case R.id.image_button75:
                premiumIcon = 73;
                selectPremiumIcon();
                break;
            case R.id.image_button76:
                premiumIcon = 74;
                selectPremiumIcon();
                break;
            case R.id.image_button77:
                premiumIcon = 75;
                selectPremiumIcon();
                break;
            case R.id.image_button78:
                premiumIcon = 76;
                selectPremiumIcon();
                break;
            case R.id.image_button79:
                premiumIcon = 77;
                selectPremiumIcon();
                break;
            case R.id.image_button80:
                premiumIcon = 78;
                selectPremiumIcon();
                break;
            case R.id.image_button81:
                premiumIcon = 79;
                selectPremiumIcon();
                break;
        }
    }

    private void selectPremiumIcon() {
        if(MainActivity.valueHolder.canUsePremium()){
            iconID = premiumIcon;
            listener.getIconPickedIcon(requestCode, iconID);
            dialog.dismiss();
        } else {
            NeedPremiumDialog pd = new NeedPremiumDialog(context, 1);
            pd.ShowDialog(context.getString(R.string.premium_reason3));
        }
    }


    public interface SelectIconDialogListener{
        void getIconPickedIcon(int requestCode, int icon);
    }
}
