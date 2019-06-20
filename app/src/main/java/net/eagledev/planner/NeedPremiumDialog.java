package net.eagledev.planner;

import android.app.Dialog;
import android.content.Context;

public class NeedPremiumDialog {

    Context context;
    int reason;
    Dialog dialog;

    NeedPremiumDialog(Context context){
        this.context = context;

    }

    public void ShowDialog(int reason){
        dialog = new Dialog(context);
        dialog.setTitle("Need premium");
        dialog.setContentView(R.layout.dialog_need_premium);
        dialog.show();


    }

}
