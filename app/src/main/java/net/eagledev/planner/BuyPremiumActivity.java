package net.eagledev.planner;

import android.annotation.SuppressLint;
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

import java.util.Calendar;

public class BuyPremiumActivity extends AppCompatActivity implements View.OnClickListener, BillingProcessor.IBillingHandler {

    int messageID;
    TextView reasonTextView, featuresTextView;
    ImageView toolbarConfirm;
    Button btnMonth, btnYear, btnAd;

    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_premium);

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
                //Test
                //bp.purchase(BuyPremiumActivity.this,"android.test.purchased");

                break;
            case R.id.btn_premium_year:

                bp.subscribe(BuyPremiumActivity.this, "premium_year");
                break;
            case R.id.btn_premium_watch_ad:
                MainActivity.valueHolder.setPremiumAdTime(Calendar.getInstance());
                MainActivity.valueHolder.setAdsPremium(true);
                finish();
                Toast.makeText(this, getResources().getString(R.string.premium_updated_by_one_day), Toast.LENGTH_LONG).show();
                break;
        }
    }


    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {

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
}
