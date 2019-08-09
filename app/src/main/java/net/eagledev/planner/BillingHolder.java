package net.eagledev.planner;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

public class BillingHolder implements BillingProcessor.IBillingHandler {

    public static final String TAG ="BillingHolder";
    public String licence_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgiu1qd45rq20wP3/5taqJ/LTpJFp7S+tllkgtJU3nkM1HJgGU0EHQRXw0JUgSAv8mM2vcCuiKopf5MiASCW3AVeD7FUeRPpKqjOJEmABEJPhFrmtpb8eyHd75DhCP9bgscVHeRe7ljrSqepnDDhq5Xh76tcXqXhzcITVZYQH/l/Vy+3zZ8zvTl28JtZ2Lcg3l3+I9k/uI7kLelWW63o5nRBX5dg68KiqN71kj83RyYxgcXMzkt7aCAAyo+aCMqsRPwOeTEjCH2NjUTdBaVrNPEp9B4OasOpx/FErBTiCaAhfmTd1DX6/knT3Q4N9+Al5lOCOjww59J4Zre921PEc8QIDAQAB";

    BillingProcessor bp;
    public boolean isBillingAvailable;

    BillingHolder() {


        isBillingAvailable = BillingProcessor.isIabServiceAvailable(MainActivity.context);

    }

    public boolean isPremium() {
        //Log.e("BillingHolder","Billing Holder is Premium check");
        bp = new BillingProcessor(MainActivity.context, licence_key, this);
        bp.initialize();
        boolean purchaseResult = bp.loadOwnedPurchasesFromGoogle();
        Log.e(TAG, "purchaseResult  " + String.valueOf(purchaseResult));
        Log.e(TAG, "isAvailable  " + String.valueOf(BillingProcessor.isIabServiceAvailable(MainActivity.context)));
        if(purchaseResult){
            boolean isAvailable = BillingProcessor.isIabServiceAvailable(MainActivity.context);
            Log.e(TAG, "isAvailable  " + String.valueOf(isAvailable));
            if(!isAvailable) {
                isBillingAvailable = false;
                bp.release();
                //Log.e("BillingHolder","Is not Avanaible");
                return false;
            }
            TransactionDetails subscriptionTransactionDetails = bp.getSubscriptionTransactionDetails("premium_month");
            if(subscriptionTransactionDetails!=null) {


                //User is still subscribed
                //Log.e("BillingHolder","Subscribed month");
                bp.release();
                return true;
            } else {
                //Not subscribed
                //Log.e("BillingHolder","Month not Subscribed");


            }
            subscriptionTransactionDetails = bp.getSubscriptionTransactionDetails("premium_year");
            if(subscriptionTransactionDetails!=null) {

                //User is still subscribed
                bp.release();
                //Log.e("BillingHolder","Subscribed year");
                return true;
            } else {
                //Not subscribed
                //Log.e("BillingHolder","Year not Subscribed");

            }
        }
        bp.release();
        //Log.e("BillingHolder","Not Subscribed");
        return false;
    }

    public boolean isBillingAvailable(){
        return isBillingAvailable;
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
