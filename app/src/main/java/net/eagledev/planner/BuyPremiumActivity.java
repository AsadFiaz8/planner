package net.eagledev.planner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.Calendar;
import java.util.List;

public class BuyPremiumActivity extends AppCompatActivity implements View.OnClickListener, BillingProcessor.IBillingHandler, RewardedVideoAdListener {

    int messageID;
    TextView reasonTextView, featuresTextView;
    ImageView toolbarConfirm;
    Button btnMonth, btnYear, btnAd;
    Context context;
    private RewardedVideoAd mRewardedVideoAd;
    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_premium);

        context = getBaseContext();
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        toolbarConfirm = findViewById(R.id.toolbar_confirm);
        toolbarConfirm.setVisibility(View.INVISIBLE);
        featuresTextView=findViewById(R.id.text_premium_features);
        btnMonth = findViewById(R.id.btn_premium_month);
        btnMonth.setOnClickListener(this);
        btnYear = findViewById(R.id.btn_premium_year);
        btnYear.setOnClickListener(this);
        btnAd = findViewById(R.id.btn_premium_watch_ad);
        btnAd.setOnClickListener(this);
        findViewById(R.id.toolbar_cancel).setOnClickListener(this);
        reasonTextView = findViewById(R.id.text_premium_reason);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(MainActivity.valueHolder.isPremiumUser()){
            featuresTextView.setText(getResources().getString(R.string.you_have_premium));
        }
        if(MainActivity.valueHolder.getAdsPremium() && MainActivity.valueHolder.getAdsPremiumActive()) {
            featuresTextView.setText(getString(R.string.premium_activatetd_by_ad));
        }
        if(bundle!=null) {
            messageID = (int) bundle.get("messageID");
        } else {
            messageID = 0;
        }
        switch (messageID) {
            case 0:
                reasonTextView.setText("");
                break;
            case 1:
                reasonTextView.setText(getResources().getString(R.string.premium_reason1));
                break;
            case 2:
                reasonTextView.setText(getResources().getString(R.string.premium_reason2));
                break;
            case 3:
                reasonTextView.setText(getResources().getString(R.string.premium_reason3));
                break;
            case 4:
                reasonTextView.setText(getResources().getString(R.string.premium_reason4));
                break;
            case 5:
                reasonTextView.setText(getResources().getString(R.string.premium_reason5));
                break;
        }
        bp = new BillingProcessor(this, MainActivity.valueHolder.licence_key, this);
        //bp = new BillingProcessor(this, null, this);
        bp.initialize();
        MobileAds.initialize(this, "ca-app-pub-6069706356094406~2925415895");
        loadRewardedVideoAd();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.toolbar_cancel:
                finish();
                break;
            case R.id.btn_premium_month:
                bp.subscribe(BuyPremiumActivity.this, "premium_month");
                //bp.purchase(BuyPremiumActivity.this,"android.test.purchased");
                break;
            case R.id.btn_premium_year:
                bp.subscribe(BuyPremiumActivity.this, "premium_year");
                break;
            case R.id.btn_premium_watch_ad:
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                    finish();
                } else {
                    Toast.makeText(context, getString(R.string.sorry_no_ads), Toast.LENGTH_LONG).show();
                }


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
        //Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
        //Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
        //Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        //Toast.makeText(this, "onRewardedVideoClosd", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        MainActivity.valueHolder.setAdsPremium(true);
        MainActivity.valueHolder.setPremiumAdTime(Calendar.getInstance());
        Toast.makeText(this, getResources().getString(R.string.premium_was_erxtended_by_one_day), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        //Toast.makeText(this, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        //Toast.makeText(this, "onRewardedVideoAdFailedToLoad error: " + i, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoCompleted() {
        //Toast.makeText(this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
        MainActivity.valueHolder.setAdsPremium(true);
        MainActivity.valueHolder.setPremiumAdTime(Calendar.getInstance());
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-6069706356094406/5168435855",
                new AdRequest.Builder().build());
    }
}
