package net.eagledev.planner;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryRecord;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;


import java.util.ArrayList;
import java.util.List;

public class BillingHolder implements  PurchasesUpdatedListener {

    public static final String TAG ="BillingHolder";
    public String licence_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgiu1qd45rq20wP3/5taqJ/LTpJFp7S+tllkgtJU3nkM1HJgGU0EHQRXw0JUgSAv8mM2vcCuiKopf5MiASCW3AVeD7FUeRPpKqjOJEmABEJPhFrmtpb8eyHd75DhCP9bgscVHeRe7ljrSqepnDDhq5Xh76tcXqXhzcITVZYQH/l/Vy+3zZ8zvTl28JtZ2Lcg3l3+I9k/uI7kLelWW63o5nRBX5dg68KiqN71kj83RyYxgcXMzkt7aCAAyo+aCMqsRPwOeTEjCH2NjUTdBaVrNPEp9B4OasOpx/FErBTiCaAhfmTd1DX6/knT3Q4N9+Al5lOCOjww59J4Zre921PEc8QIDAQAB";

    private BillingClient billingClient;
    public boolean isBillingAvailable;
    boolean premium = false;
    List<String> skuList;
    AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener;

    BillingHolder() {

        billingClient = BillingClient.newBuilder(MainActivity.context).setListener(this).enablePendingPurchases().build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    Toast.makeText(MainActivity.context, "Billing connect", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.context, "Billing cant't connect", Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });


        billingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.SUBS, new PurchaseHistoryResponseListener() {
            @Override
            public void onPurchaseHistoryResponse(BillingResult billingResult, List<PurchaseHistoryRecord> purchaseHistoryRecordList) {
                Log.e(TAG, "onPurchaseHistoryResponse");
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                        && purchaseHistoryRecordList != null) {

                    for (PurchaseHistoryRecord purchase : purchaseHistoryRecordList) {
                        // Process the result.
                        if(purchase.getSku().equals("premium_month")){
                            premium = true;
                            Log.e(TAG, "Premium month true");
                        }
                        if(purchase.getSku().equals("premium_year")){
                            premium = true;
                            Log.e(TAG, "Premium year true");
                        }
                    }
                }
            }
        });

        acknowledgePurchaseResponseListener = new AcknowledgePurchaseResponseListener() {
            @Override
            public void onAcknowledgePurchaseResponse(BillingResult billingResult) {

            }
        };

        skuList = new ArrayList<>();
        skuList.add("premium_month");
        skuList.add("premium_year");
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(BillingResult billingResult,
                                                     List<SkuDetails> skuDetailsList) {
                        // Process the result.
                    }
                });
    }

    public boolean isPremium() {
        //Log.e("BillingHolder","Billing Holder is Premium check");
        if (premium){
            return true;
        }


        return false;
    }

    public boolean isBillingAvailable(){
        return isBillingAvailable;
    }




    public void test(){
        billingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP,
                new PurchaseHistoryResponseListener() {
                    @Override
                    public void onPurchaseHistoryResponse(BillingResult billingResult, List<PurchaseHistoryRecord> purchaseHistoryRecordList) {

                    }
                });
    }





    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {

        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                && purchases != null) {
            for (Purchase purchase : purchases) {
                handlePurchase(purchase);
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            // Handle any other error codes.
        }
    }

    void handlePurchase(Purchase purchase) {
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            // Grant entitlement to the user.

            if(purchase.getSku().equals("premium_month")){
                premium = true;
            }
            if(purchase.getSku().equals("premium_year")){
                premium = true;
            }


            // Acknowledge the purchase if it hasn't already been acknowledged.
            if (!purchase.isAcknowledged()) {
                AcknowledgePurchaseParams acknowledgePurchaseParams =
                        AcknowledgePurchaseParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .build();
                billingClient.acknowledgePurchase(acknowledgePurchaseParams, acknowledgePurchaseResponseListener);
            }
        }
    }


}
