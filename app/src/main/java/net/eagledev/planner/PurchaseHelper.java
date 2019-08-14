package net.eagledev.planner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.billingclient.api.BillingClient;

import com.android.billingclient.api.BillingClient.SkuType;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.List;

/**
 * Created by Jaison.
 */
public class PurchaseHelper {

    private String TAG = "PurchaseHelper";

    private Context context;

    private BillingClient mBillingClient;

    private PurchaseHelperListener purchaseHelperListener;

    private boolean mIsServiceConnected;

    private int billingSetupResponseCode;



    /**
     * To instantiate the object
     *
     * @param context It will be used to get an application context to bind to the in-app billing
     *     service.
     * @param purchaseHelperListener Your listener to get the response for your query.
     */
    public PurchaseHelper(Context context, PurchaseHelperListener purchaseHelperListener) {
        this.context = context;
        mBillingClient = BillingClient.newBuilder(context).enablePendingPurchases().setListener(getPurchaseUpdatedListener()).build();
        this.purchaseHelperListener = purchaseHelperListener;
        startConnection(getServiceConnectionRequest());
    }


    /**
     * To establish the connection with play library
     *
     * @param onSuccessRequest It will be used to notify that setup is complete and the billing
     *     client is ready. You can query whatever you want.
     */
    private void startConnection(final Runnable onSuccessRequest) {
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                int billingResponseCode = billingResult.getResponseCode();
                Log.d(TAG, "onBillingSetupFinished: " + billingResponseCode);

                if (billingResponseCode == BillingClient.BillingResponseCode.OK) {
                    mIsServiceConnected = true;

                    billingSetupResponseCode = billingResponseCode;

                    if (onSuccessRequest != null) {
                        onSuccessRequest.run();
                    }
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                mIsServiceConnected = false;
                Log.d(TAG, "onBillingServiceDisconnected: ");
            }
        });
    }


    public boolean isServiceConnected() {
        return mIsServiceConnected;
    }

    /**
     * Call this method once you are done with this BillingClient reference.
     */
    public void endConnection() {
        if (mBillingClient != null && mBillingClient.isReady()) {
            mBillingClient.endConnection();
            mBillingClient = null;
        }
    }


    /**
     * To notify that setup is complete and the billing client is ready.
     * @return
     */
    private Runnable getServiceConnectionRequest() {
        return new Runnable() {
            @Override
            public void run() {
                if (purchaseHelperListener != null)
                    purchaseHelperListener.onServiceConnected(billingSetupResponseCode);
            }
        };
    }

    /**
     * Check if the subscription feature is supported by the Play Store.
     * @return
     */
    private boolean isSubscriptionSupported() {
        int responseCode = mBillingClient.isFeatureSupported(BillingClient.FeatureType.SUBSCRIPTIONS).getResponseCode();
        if (responseCode != BillingClient.BillingResponseCode.OK)
            Log.w(TAG, "isSubscriptionSupported() got an error response: " + responseCode);
        return responseCode == BillingClient.BillingResponseCode.OK;
    }

    /**
     * Check if the manage products feature is supported by the Play Store.
     * @return
     */
    private boolean isInAppSupported() {
        int responseCode = mBillingClient.isFeatureSupported(BillingClient.FeatureType.SUBSCRIPTIONS_UPDATE).getResponseCode();
        if (responseCode != BillingClient.BillingResponseCode.OK)
            Log.w(TAG, "isInAppSupported() got an error response: " + responseCode);
        return responseCode == BillingClient.BillingResponseCode.OK;
    }


    /**
     * Get purchases details for all the items bought within your app.
     *
     * @param skuType The type of SKU, either "inapp" or "subs"
     */
    public void getPurchasedItems(@SkuType final String skuType) {

        Runnable purchaseHistoryRequest = new Runnable() {
            @Override
            public void run() {
                Purchase.PurchasesResult purchasesResult = mBillingClient.queryPurchases(skuType);

                if (purchaseHelperListener != null)
                    purchaseHelperListener.onPurchasehistoryResponse(purchasesResult.getPurchasesList());
            }
        };

        executeServiceRequest(purchaseHistoryRequest);
    }


    /**
     * Perform a network query to get SKU details and return the result asynchronously.
     *
     * @param skuList Specify the SKUs that are queried for as published in the Google Developer console.
     * @param skuType The type of SKU, either "inapp" or "subs"
     */
    public void getSkuDetails(final List<String> skuList, @SkuType final String skuType) {

        Runnable skuDetailsRequest = new Runnable() {
            @Override
            public void run() {

                SkuDetailsParams skuParams;

                skuParams = SkuDetailsParams.newBuilder().setType(skuType).setSkusList(skuList).build();

                mBillingClient.querySkuDetailsAsync(skuParams, new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                        int responseCode = billingResult.getResponseCode();


                        if (responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                            Log.e(TAG, "getSkuDetails: " + responseCode + "   "+ BillingClient.BillingResponseCode.OK);
                            Log.e(TAG, "skuDetailsListSize: " + skuDetailsList.size());

                            if (purchaseHelperListener != null)
                                Log.e(TAG, "purchaseHelperListener: " + purchaseHelperListener);
                                purchaseHelperListener.onSkuQueryResponse(skuDetailsList);

                        }
                    }
                });
            }
        };

        executeServiceRequest(skuDetailsRequest);

    }

    /**
     * Initiate the billing flow for an in-app purchase or subscription.
     *
     *  skuType The type of SKU, either "inapp" or "subs"
     *  productId Specify the SKU that is being purchased to as published in the Google
     * Developer console.
     */
    public void launchBillingFLow(@SkuType final SkuDetails skuDetails) {

        Runnable launchBillingRequest = new Runnable() {
            @Override
            public void run() {

                BillingFlowParams mBillingFlowParams;

                mBillingFlowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(skuDetails)
                        .build();

                mBillingClient.launchBillingFlow((Activity) context, mBillingFlowParams);

            }
        };

        executeServiceRequest(launchBillingRequest);

    }

    /**
     * Redirects the user to the “Manage subscription” page for your app.
     */
    public void gotoManageSubscription() {
        String PACKAGE_NAME = context.getPackageName();

        Log.d(TAG, "gotoManageSubscription: " + PACKAGE_NAME);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/account/subscriptions?package="+PACKAGE_NAME));
        context.startActivity(browserIntent);
    }

    /**
     * Your listener to get the response for purchase updates which happen when, the user buys
     * something within the app or by initiating a purchase from Google Play Store.
     */
    private PurchasesUpdatedListener getPurchaseUpdatedListener() {
        return new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {
                if (purchaseHelperListener != null)
                    purchaseHelperListener.onPurchasesUpdated(billingResult.getResponseCode(), purchases);
            }
        };
    }

    /**
     * To execute all the requests based on the connection status
     *
     * If billing service was connected, then execute the request.
     *
     * If billing service was disconnected, we try to reconnect 1 time.
     *
     * @param runnable It will be used to execute the request
     */
    private void executeServiceRequest(Runnable runnable) {
        if (mIsServiceConnected) {
            runnable.run();
        } else {
            startConnection(runnable);
        }
    }


    /**
     * Listener interface for handling the various responses of the Purchase helper util
     */
    public interface PurchaseHelperListener {
        void onServiceConnected(@BillingClient.BillingResponseCode int resultCode);

        void onSkuQueryResponse(List<SkuDetails> skuDetails);

        void onPurchasehistoryResponse(List<Purchase> purchasedItems);

        void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases);
    }
}
