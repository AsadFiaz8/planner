package net.eagledev.planner.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.analytics.FirebaseAnalytics;

import net.eagledev.planner.MainActivity;
import net.eagledev.planner.PlannerButton;
import net.eagledev.planner.PurchaseHelper;
import net.eagledev.planner.R;
import net.eagledev.planner.ValueHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BuyPremiumActivity extends AppCompatActivity implements View.OnClickListener, RewardedVideoAdListener, PurchaseHelper.PurchaseHelperListener {

    private static final String TAG = "BuyPremiumActivity";
    private static final int REQUEST_INVITE = 0;
    int messageID;
    TextView featuresTextView, pointsTextView;
    ImageView toolbarConfirm;
    PlannerButton btnMonth, btnYear, btnAd;
    Context context;
    private RewardedVideoAd mRewardedVideoAd;
    ValueHolder valueHolder;
    private BillingClient billingClient;
    SkuDetailsParams.Builder params;
    SkuDetails monthDetails;
    SkuDetails yearDetails;
    PurchaseHelper purchaseHelper;
    List<Purchase> purchaseHistory;

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
        btnYear = findViewById(R.id.btn_premium_year);
        btnYear.setOnClickListener(this);
        btnAd = findViewById(R.id.btn_premium_watch_ad);
        btnAd.setOnClickListener(this);
        findViewById(R.id.premium_invite_button).setOnClickListener(this);
        findViewById(R.id.toolbar_cancel).setOnClickListener(this);
        pointsTextView = findViewById(R.id.text_premium_points);
        pointsTextView.setText(getResources().getString(R.string.premium_points_amount)+" "+ MainActivity.valueHolder.premiumPoints());
        purchaseHelper = new PurchaseHelper(this, this);
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

                purchaseHelper.launchBillingFLow(BillingClient.SkuType.SUBS, "premium_month");
                //bp.purchase(BuyPremiumActivity.this,"android.test.purchased");
                break;
            case R.id.btn_premium_year:
                purchaseHelper.launchBillingFLow(BillingClient.SkuType.SUBS, "premium_year");
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
            case R.id.premium_invite_button:
                Intent intent = new AppInviteInvitation.IntentBuilder("Tytuł")
                        .setMessage("Wiadomość")
                        .setDeepLink(Uri.parse("http//degeapps.net/invited"))
                        .setCallToActionText("Nwm co to")
                        .build();
                startActivityForResult(intent, REQUEST_INVITE);

                break;

        }
    }




    @Override
    public void onDestroy() {


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
        if(MainActivity.currentUser.getEmail().equals("serdowas@gmail.com") ||MainActivity.currentUser.getEmail().equals("ddamian102@gmail.com")||MainActivity.currentUser.getEmail().equals("emilia.szornak@gmail.com")||MainActivity.currentUser.getEmail().equals("glembink97@gmail.com")) {
            MainActivity.valueHolder.changePremiumPoints(500);
        }
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

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Log.d(TAG, "onActivityResult: sent invitation " + id);
                }
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
            }
        }
    }


    @Override
    public void onServiceConnected(int resultCode) {

        Log.d(TAG, "onServiceConnected: " + resultCode);
    }

    @Override
    public void onSkuQueryResponse(List<SkuDetails> skuDetails) {

    }

    @Override
    public void onPurchasehistoryResponse(List<Purchase> purchasedItems) {

        purchaseHistory = purchasedItems;
    }

    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
        Log.d(TAG, "onPurchasesUpdated: " + responseCode);
    }
}
