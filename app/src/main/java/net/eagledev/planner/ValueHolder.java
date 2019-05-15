package net.eagledev.planner;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

import java.util.Calendar;

public class ValueHolder implements BillingProcessor.IBillingHandler {


    SharedPreferences.Editor editor;

    boolean premiumUser;
    boolean adsPremium;
    Calendar premiumAdTime = Calendar.getInstance();
    boolean mainNotification = true;
    boolean  datePickerButton = true;
    Checker checker = new Checker();
    public String licence_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgiu1qd45rq20wP3/5taqJ/LTpJFp7S+tllkgtJU3nkM1HJgGU0EHQRXw0JUgSAv8mM2vcCuiKopf5MiASCW3AVeD7FUeRPpKqjOJEmABEJPhFrmtpb8eyHd75DhCP9bgscVHeRe7ljrSqepnDDhq5Xh76tcXqXhzcITVZYQH/l/Vy+3zZ8zvTl28JtZ2Lcg3l3+I9k/uI7kLelWW63o5nRBX5dg68KiqN71kj83RyYxgcXMzkt7aCAAyo+aCMqsRPwOeTEjCH2NjUTdBaVrNPEp9B4OasOpx/FErBTiCaAhfmTd1DX6/knT3Q4N9+Al5lOCOjww59J4Zre921PEc8QIDAQAB";

    BillingHolder billingHolder;
    //BillingProcessor bp;

    ValueHolder() {
        editor = MainActivity.pref.edit();

        //billingHolder = new BillingHolder();
        //bp = new BillingProcessor(MainActivity.context, licence_key, this);
        //bp.initialize();



    }



    public boolean isPremiumUser() {
        billingHolder = new BillingHolder();
        if(billingHolder.isPremium()){
            //User is still subscribed
            editor.putBoolean("premium_user", true);
            editor.commit();
            billingHolder = null;
            return true;
        } else {
            if (!billingHolder.isBillingAvailable()){
                billingHolder = null;
                return MainActivity.pref.getBoolean("premium_user", false);
            } else {
                editor.putBoolean("premium_user", false);
                editor.commit();
                billingHolder = null;
                return false;
            }
        }

    }

    public void setPremiumUser(boolean premiumUser) {
        this.premiumUser = premiumUser;
        editor.putBoolean("premium_user", premiumUser);
        editor.commit();
    }

    public void setPremiumAdTime(Calendar cal) {
        editor.putInt("premium_ad_day", cal.get(Calendar.DAY_OF_MONTH));
        editor.putInt("premium_ad_month", cal.get(Calendar.MONTH));
        editor.putInt("premium_ad_year", cal.get(Calendar.YEAR));
        editor.commit();
        premiumAdTime = cal;
    }

    public  boolean getAdsPremiumActive() {
        Calendar today = Calendar.getInstance();
        premiumAdTime.set(Calendar.DAY_OF_MONTH, MainActivity.pref.getInt("premium_ad_day", 1));
        premiumAdTime.set(Calendar.MONTH, MainActivity.pref.getInt("premium_ad_month", Calendar.JANUARY));
        premiumAdTime.set(Calendar.YEAR, MainActivity.pref.getInt("premium_ad_year", 0));
        return checker.DateEquals(today, premiumAdTime);
    }

    public void setAdsPremium(boolean premium) {
        adsPremium = premium;
        editor.putBoolean("ads_premium", premium);
        editor.commit();
    }

    public boolean getAdsPremium() {
        if(isPremiumUser()){
            return false;
        }
        adsPremium = MainActivity.pref.getBoolean("ads_premium", false);
        return adsPremium;
    }




    public boolean isDatePickerButton() {
        datePickerButton = MainActivity.pref.getBoolean("piker_button", true);
        return datePickerButton;
    }

    public void setDatePickerButton(boolean datePickerButton) {
        this.datePickerButton = datePickerButton;
        editor = MainActivity.pref.edit();
        editor.putBoolean("piker_button", datePickerButton);
        editor.commit();
    }



    public boolean isMainNotification() {
        mainNotification = MainActivity.pref.getBoolean("main_notification", true);
        return mainNotification;
    }

    public void setMainNotification(boolean mainNotification) {
        this.mainNotification = mainNotification;
        editor = MainActivity.pref.edit();
        editor.putBoolean("main_notification", mainNotification);
        editor.commit();
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
