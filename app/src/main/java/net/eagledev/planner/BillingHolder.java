package net.eagledev.planner;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;


import java.util.ArrayList;
import java.util.List;

public class BillingHolder implements PurchaseHelper.PurchaseHelperListener {

    public static final String TAG ="BillingHolder";
    public String licence_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgiu1qd45rq20wP3/5taqJ/LTpJFp7S+tllkgtJU3nkM1HJgGU0EHQRXw0JUgSAv8mM2vcCuiKopf5MiASCW3AVeD7FUeRPpKqjOJEmABEJPhFrmtpb8eyHd75DhCP9bgscVHeRe7ljrSqepnDDhq5Xh76tcXqXhzcITVZYQH/l/Vy+3zZ8zvTl28JtZ2Lcg3l3+I9k/uI7kLelWW63o5nRBX5dg68KiqN71kj83RyYxgcXMzkt7aCAAyo+aCMqsRPwOeTEjCH2NjUTdBaVrNPEp9B4OasOpx/FErBTiCaAhfmTd1DX6/knT3Q4N9+Al5lOCOjww59J4Zre921PEc8QIDAQAB";

    private BillingClient billingClient;
    public boolean isBillingAvailable;
    boolean premium = false;
    List<String> skuList;
    PurchaseHelper purchaseHelper;
    List<Purchase> purchaseHistory;
    List<SkuDetails> skuDetails;


    BillingHolder() {

        purchaseHelper = new PurchaseHelper(MainActivity.context, this);
        purchaseHelper.getPurchasedItems(BillingClient.SkuType.SUBS);
        setSkuDetails();




    }

    public boolean isPremium() {
        Log.e("BillingHolder","Billing Holder is Premium check");

        purchaseHelper.getPurchasedItems(BillingClient.SkuType.SUBS);

        if(purchaseHistory != null){
            for(Purchase purchase: purchaseHistory){
                Log.e(TAG, "purchaseHistory" + purchaseHistory.size());
                if (purchase.getSku().equals("premium_month")) return true;
                if (purchase.getSku().equals("premium_year")) return true;
            }
        }
        if (premium){
            return true;
        }


        return false;
    }

    public boolean isBillingAvailable(){
        return isBillingAvailable;
    }




    void setSkuDetails(){
        List<String> skuNames = new ArrayList<>();
        skuNames.add("premium_month");
        skuNames.add("premium_year");
        purchaseHelper.getSkuDetails(skuNames, BillingClient.SkuType.SUBS);
    }


    @Override
    public void onServiceConnected(int resultCode) {

        Log.d(TAG, "onServiceConnected: " + resultCode);
    }

    @Override
    public void onSkuQueryResponse(List<SkuDetails> skuDetails) {

        this.skuDetails = skuDetails;
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
