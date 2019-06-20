package net.eagledev.planner;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.concurrent.TimeUnit;

public class NeedPremiumDialog  implements RewardedVideoAdListener {

    Context context;
    int reason;
    Dialog dialog;
    RewardedVideoAd VideoAd;
    NeedPremiumDialogListener listener;
    TextView textView;
    boolean waintingForAd = false;
    int code;



    NeedPremiumDialog(Context context){
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

    private void loadRewardedVideoAd() {
        VideoAd.loadAd("ca-app-pub-6069706356094406/5168435855",
                new AdRequest.Builder().build());
    }

    public void ShowDialog(int reason){

        dialog = new Dialog(context);
        dialog.setTitle("Need premium");
        dialog.setContentView(R.layout.dialog_need_premium);
        dialog.show();
        code = reason;
        textView = dialog.findViewById(R.id.need_premium_reason);

        textView.setText(Reason(reason));
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
        MainActivity.valueHolder.changePremiumPoints(1);

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
        void getPremiumDialogResultCode(int resultColde);
    }

    private String Reason(int reason){
        switch (reason){
            case 0:
                return context.getString(R.string.reason_create_new_labels);
            case 1:
                return context.getString(R.string.reason_select_labels);
            case 2:
                return context.getString(R.string.reason_add_comment_to_task);



                default:
                    return context.getString(R.string.only_premium_users_can_use_this_function);

        }



    }


}
