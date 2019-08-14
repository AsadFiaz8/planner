package net.eagledev.planner;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;



import java.util.Calendar;

public class ValueHolder  {


    SharedPreferences.Editor editor;

    boolean premiumUser;
    boolean adsPremium;

    public static final String TAG = "ValueHolder";


    boolean tut;
    Calendar premiumAdTime = Calendar.getInstance();
    boolean mainNotification = true;
    boolean  datePickerButton = true;
    Checker checker = new Checker();
    public static final String p_month="premium_month";
    public static final String p_year = "premium_year";
    public String licence_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgiu1qd45rq20wP3/5taqJ/LTpJFp7S+tllkgtJU3nkM1HJgGU0EHQRXw0JUgSAv8mM2vcCuiKopf5MiASCW3AVeD7FUeRPpKqjOJEmABEJPhFrmtpb8eyHd75DhCP9bgscVHeRe7ljrSqepnDDhq5Xh76tcXqXhzcITVZYQH/l/Vy+3zZ8zvTl28JtZ2Lcg3l3+I9k/uI7kLelWW63o5nRBX5dg68KiqN71kj83RyYxgcXMzkt7aCAAyo+aCMqsRPwOeTEjCH2NjUTdBaVrNPEp9B4OasOpx/FErBTiCaAhfmTd1DX6/knT3Q4N9+Al5lOCOjww59J4Zre921PEc8QIDAQAB";

    BillingHolder billingHolder;
    FirestoreDatabase firestoreDatabase;

    public static final int CODE_REMINDERS = 999;
    //BillingProcessor bp;

    public ValueHolder() {
        try {
            firestoreDatabase = MainActivity.fDatabase;
            editor = MainActivity.pref.edit();

        } catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        //billingHolder = new BillingHolder();
        //bp = new BillingProcessor(MainActivity.context, licence_key, this);
        //bp.initialize();



    }



    public boolean isPremiumUser() {
        billingHolder = new BillingHolder();
        if(billingHolder.isPremium()){
            //User is still subscribed

            Log.e(TAG, "Is premium");
            editor.putBoolean("premium_user", true);
            editor.commit();
            billingHolder = null;
            return true;
        } else {
            if (!billingHolder.isBillingAvailable()){
                billingHolder = null;
                Log.e(TAG, "Billing is not available");
                if(MainActivity.pref.getBoolean("premium_user", false)) return true;
                Calendar calendar = Calendar.getInstance();
                if(premiumTime() > calendar.getTimeInMillis()) return true;
            } else {
                editor.putBoolean("premium_user", false);
                editor.commit();
                Log.e(TAG, "Billing is available");
                billingHolder = null;


            }
        }
        if(firestoreDatabase.getPremiumTime()>Calendar.getInstance().getTimeInMillis()){
            return true;
        }
        /*
        firestoreDatabase.setPremium(true);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 14);
        firestoreDatabase.setPremiumTime(calendar.getTimeInMillis());
        */

        return false;

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

    public boolean isTut() {
        tut = MainActivity.pref.getBoolean("tutorial", false);
        return tut;
    }

    public void setTut(boolean tut) {
        this.tut = tut;
        editor = MainActivity.pref.edit();
        editor.putBoolean("tutorial", tut);
        editor.commit();
    }

    public void setFirstBackup(boolean firstBackup){
        editor=MainActivity.pref.edit();
        editor.putBoolean("first_backup", firstBackup);
        editor.commit();
    }

    public int premiumPoints(){
        return MainActivity.pref.getInt("premium_points", 0);
    }

    public void changePremiumPoints(int amount){
        int points = MainActivity.pref.getInt("premium_points", 0);
        points += amount;
        if(points<0) points =0;
        editor = MainActivity.pref.edit();
        editor.putInt("premium_points", points);
        editor.commit();

    }

    public long premiumTime() {
        return MainActivity.pref.getLong("premium_time", 0);
    }

    public void setPremiumTime(long time){
        editor = MainActivity.pref.edit();
        editor.putLong("premium_time", time);
        editor.commit();

    }

    public boolean canUsePremium(){
        if (premiumPoints()>0 || isPremiumUser()){
            if(!isPremiumUser()) changePremiumPoints(-1);
            return true;
        } else return false;
    }

    public boolean canGiveOpinion(){
        boolean gaveOpinion = MainActivity.pref.getBoolean("gave_opinion", false);
        if(gaveOpinion){
            return false;
        } else {
            long time = MainActivity.pref.getLong("start_day",-1);
            if(time == -1){
                editor = MainActivity.pref.edit();
                editor.putLong("start_day",Calendar.getInstance().getTimeInMillis());
                editor.commit();
                return false;
            } else {
                if(Calendar.getInstance().getTimeInMillis() - time >= 259200000){
                    return true;
                } else return false;
            }
        }

    }

    public void setGaveOpinion(boolean opinion){
        editor=MainActivity.pref.edit();
        editor.putBoolean("gave_opinion", opinion);
        editor.commit();
    }







    public boolean getFirstBackup(){
        return MainActivity.pref.getBoolean("first_backup", false);
    }




}
