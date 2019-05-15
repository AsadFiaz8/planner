package net.eagledev.planner;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

public class BillingHolder implements BillingProcessor.IBillingHandler {

    public String licence_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgiu1qd45rq20wP3/5taqJ/LTpJFp7S+tllkgtJU3nkM1HJgGU0EHQRXw0JUgSAv8mM2vcCuiKopf5MiASCW3AVeD7FUeRPpKqjOJEmABEJPhFrmtpb8eyHd75DhCP9bgscVHeRe7ljrSqepnDDhq5Xh76tcXqXhzcITVZYQH/l/Vy+3zZ8zvTl28JtZ2Lcg3l3+I9k/uI7kLelWW63o5nRBX5dg68KiqN71kj83RyYxgcXMzkt7aCAAyo+aCMqsRPwOeTEjCH2NjUTdBaVrNPEp9B4OasOpx/FErBTiCaAhfmTd1DX6/knT3Q4N9+Al5lOCOjww59J4Zre921PEc8QIDAQAB";

    BillingProcessor bp;
    public boolean isBillingAvailable;

    BillingHolder() {


    }

    public boolean isPremium() {
        bp = new BillingProcessor(MainActivity.context, licence_key, this);
        bp.initialize();
        boolean purchaseResult = bp.loadOwnedPurchasesFromGoogle();
        if(purchaseResult){
            boolean isAvailable = BillingProcessor.isIabServiceAvailable(MainActivity.context);
            if(!isAvailable) {
                isBillingAvailable = false;
                bp.release();
                return false;
            }
            TransactionDetails subscriptionTransactionDetails = bp.getSubscriptionTransactionDetails("premium_month");
            if(subscriptionTransactionDetails!=null) {


                //User is still subscribed
                bp.release();
                return true;
            } else {
                //Not subscribed


            }
            subscriptionTransactionDetails = bp.getSubscriptionTransactionDetails("premium_year");
            if(subscriptionTransactionDetails!=null) {

                //User is still subscribed
                bp.release();
                return true;
            } else {
                //Not subscribed


            }
        }
        bp.release();
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
