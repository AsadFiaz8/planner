package net.eagledev.planner;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

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
        Button premiumButton = dialog.findViewById(R.id.need_premium_button);
        premiumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pIntent = new Intent(context, BuyPremiumActivity.class);
                context.startActivity(pIntent);
                dialog.dismiss();
            }
        });

        Button adButton = dialog.findViewById(R.id.need_premium_ad_button);
        adButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
