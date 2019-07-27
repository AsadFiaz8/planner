package net.eagledev.planner.Dialog;

import android.app.Dialog;
import android.app.Presentation;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import net.eagledev.planner.Activity.BuyPremiumActivity;
import net.eagledev.planner.MainActivity;
import net.eagledev.planner.PlannerButton;
import net.eagledev.planner.R;

public class NeedPremiumDialog  implements RewardedVideoAdListener {

    Context context;
    int reason;
    Dialog dialog;
    RewardedVideoAd VideoAd;
    NeedPremiumDialogListener listener;
    TextView textView;
    boolean waintingForAd = false;
    int code;



    public NeedPremiumDialog(Context context){
        this.context = context;
        this.code = code;
        try {
            listener = (NeedPremiumDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement NeedPremiumDialogListener");
        }

        VideoAd = MobileAds.getRewardedVideoAdInstance(context);
        VideoAd.setRewardedVideoAdListener(this);
        MobileAds.initialize(context, "ca-app-pub-6069706356094406~2925415895");
        loadRewardedVideoAd();
    }

    public NeedPremiumDialog(Context context, int code){
        this.context = context;
        this.code = code;
        try {
            listener = (NeedPremiumDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement NeedPremiumDialogListener");
        }

        VideoAd = MobileAds.getRewardedVideoAdInstance(context);
        VideoAd.setRewardedVideoAdListener(this);
        //MobileAds.initialize(context, "ca-app-pub-6069706356094406~2925415895");
        MobileAds.initialize(context);
        loadRewardedVideoAd();
    }

    private void loadRewardedVideoAd() {
        VideoAd.loadAd("ca-app-pub-6069706356094406/5168435855", new AdRequest.Builder().build());
        //Reklama testowa
        //VideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());

    }



        public void ShowDialog(String reason){

            dialog = new Dialog(context);
            dialog.setTitle("Need premium");
            dialog.setContentView(R.layout.dialog_need_premium);
            dialog.show();
            textView = dialog.findViewById(R.id.need_premium_reason);

            textView.setText(reason);
            PlannerButton premiumButton = dialog.findViewById(R.id.need_premium_button);
            premiumButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pIntent = new Intent(context, BuyPremiumActivity.class);
                    context.startActivity(pIntent);
                    dialog.dismiss();
                }
            });

            PlannerButton adButton = dialog.findViewById(R.id.need_premium_ad_button);
            adButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.VideoAd.isLoaded()) {
                        MainActivity.VideoAd.show();

                    } else {
                        waintingForAd = true;
                        Toast.makeText(context, context.getString(R.string.sorry_no_ads), Toast.LENGTH_LONG).show();
                    }
                }
            });

    }

    @Override
    public void onRewardedVideoAdLoaded() {
        if(waintingForAd){
            waintingForAd = false;
            MainActivity.VideoAd.show();
        }
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        MainActivity.valueHolder.changePremiumPoints(3);

        listener.getPremiumDialogResultCode(code);
        dialog.dismiss();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    public interface NeedPremiumDialogListener{
        void getPremiumDialogResultCode(int resultCode);
    }

    private String Reason(int reason){
        switch (reason){
            case 0:
                return context.getString(R.string.reason_create_new_labels);
            case 1:
                return context.getString(R.string.reason_select_labels);
            case 2:
                return context.getString(R.string.reason_add_comment_to_task);
            case 3:
                return context.getString(R.string.premium_reason1);




                default:
                    return context.getString(R.string.only_premium_users_can_use_this_function);

        }



    }


}
