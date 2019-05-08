package net.eagledev.planner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.Calendar;

public class WatchPremiumAdActivity extends AppCompatActivity implements View.OnClickListener, BillingProcessor.IBillingHandler, RewardedVideoAdListener {

    ImageView toolbarConfirm;
    Context context;
    BillingProcessor bp;
    private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_premium_ad);
        context = getBaseContext();
        toolbarConfirm = findViewById(R.id.toolbar_confirm);
        toolbarConfirm.setVisibility(View.INVISIBLE);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        findViewById(R.id.toolbar_cancel).setOnClickListener(this);
        findViewById(R.id.btn_premium_watch_ad).setOnClickListener(this);
        findViewById(R.id.btn_premium_month).setOnClickListener(this);
        findViewById(R.id.btn_premium_year).setOnClickListener(this);
        bp = new BillingProcessor(this, MainActivity.valueHolder.licence_key, this);
        bp.initialize();
        MobileAds.initialize(this, "ca-app-pub-6069706356094406~2925415895");
        loadRewardedVideoAd();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.toolbar_cancel:
                MainActivity.valueHolder.setAdsPremium(false);
                finish();
                break;
            case R.id.btn_premium_watch_ad:
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                    finish();
                } else {
                    Toast.makeText(context, getString(R.string.sorry_no_ads), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_premium_month:
                bp.subscribe(WatchPremiumAdActivity.this, "premium_month");
                break;
            case R.id.btn_premium_year:
                bp.subscribe(WatchPremiumAdActivity.this, "premium_year");
                break;
        }
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {

        if(productId.equals("premium_month")){
            MainActivity.valueHolder.setAdsPremium(false);
            MainActivity.valueHolder.setPremiumUser(true);
            finish();
        }
        if(productId.equals("premium_year")){
            MainActivity.valueHolder.setAdsPremium(false);
            MainActivity.valueHolder.setPremiumUser(true);
            finish();
        }
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
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
        MainActivity.valueHolder.setAdsPremium(true);
        MainActivity.valueHolder.setPremiumAdTime(Calendar.getInstance());
        Toast.makeText(this, getResources().getString(R.string.premium_was_erxtended_by_one_day), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {
        MainActivity.valueHolder.setAdsPremium(true);
        MainActivity.valueHolder.setPremiumAdTime(Calendar.getInstance());
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-6069706356094406/5168435855",
                new AdRequest.Builder().build());
    }
}
