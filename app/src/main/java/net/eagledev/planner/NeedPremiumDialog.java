package net.eagledev.planner;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.concurrent.TimeUnit;

public class NeedPremiumDialog implements RewardedVideoAdListener {

    Context context;
    int reason;
    Dialog dialog;
    RewardedVideoAd VideoAd;



    NeedPremiumDialog(Context context){
        this.context = context;
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
                    Toast.makeText(context, context.getString(R.string.sorry_no_ads), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onRewardedVideoAdLoaded() {

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
}
