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
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Calendar;
import java.util.List;

public class BuyPremiumActivity extends AppCompatActivity implements View.OnClickListener, BillingProcessor.IBillingHandler, RewardedVideoAdListener {

    int messageID;
    TextView featuresTextView, pointsTextView;
    ImageView toolbarConfirm;
    Button btnYear, btnAd;
    PlannerButton btnMonth;
    Context context;
    private RewardedVideoAd mRewardedVideoAd;
    BillingProcessor bp;
    ValueHolder valueHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_premium);

        valueHolder = new ValueHolder();
        context = getBaseContext();
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        toolbarConfirm = findViewById(R.id.toolbar_confirm);
        toolbarConfirm.setVisibility(View.INVISIBLE);
        featuresTextView=findViewById(R.id.text_premium_features);
        btnMonth = findViewById(R.id.btn_premium_month);
        btnMonth.setOnClickListener(this);
        btnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "test", Toast.LENGTH_LONG).show();
            }
        });
        btnYear = findViewById(R.id.btn_premium_year);
        btnYear.setOnClickListener(this);
        btnAd = findViewById(R.id.btn_premium_watch_ad);
        btnAd.setOnClickListener(this);
        findViewById(R.id.toolbar_cancel).setOnClickListener(this);
        pointsTextView = findViewById(R.id.text_premium_points);
        pointsTextView.setText(getResources().getString(R.string.premium_points_amount)+" "+MainActivity.valueHolder.premiumPoints());
        Intent intent = getIntent();
        Bundle bundle = null;
        if(intent != null){
            bundle = intent.getExtras();
        }

        if(valueHolder.isPremiumUser()){
            featuresTextView.setText(getResources().getString(R.string.you_have_premium));
        }
        if(bundle!=null) {
            messageID = (int) bundle.get("messageID");
        } else {
            messageID = 0;
        }

        bp = new BillingProcessor(this, valueHolder.licence_key, this);
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
                if (bp != null) {
                    bp.release();
                }

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
                    pointsTextView.setText(getResources().getString(R.string.premium_points_amount)+" "+MainActivity.valueHolder.premiumPoints());
                } else {
                    Toast.makeText(context, getString(R.string.sorry_no_ads), Toast.LENGTH_LONG).show();
                    loadRewardedVideoAd();
                }


                break;
        }
    }


    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, productId);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "sub");
        MainActivity.mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        if(productId.equals("premium_month")){
            valueHolder.setPremiumUser(true);
            finish();
        }
        if(productId.equals("premium_year")){
            valueHolder.setPremiumUser(true);
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
        MainActivity.valueHolder.changePremiumPoints(3);
        pointsTextView.setText(getResources().getString(R.string.premium_points_amount)+" "+MainActivity.valueHolder.premiumPoints());
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

    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-6069706356094406/5168435855",
                new AdRequest.Builder().build());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (bp != null) {
            bp.release();
        }
        bp = null;
        finish();
    }
}
