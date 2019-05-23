package net.eagledev.planner;

import java.util.Calendar;

public class Checker {

    Calendar now;

    public Checker() {

    }

    public boolean DateInFuture(Calendar cal1) {

        Calendar cal2 = Calendar.getInstance();
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA)) return false;
        if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA)) return true;
        if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) return false;
        if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) return true;
        return cal1.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR);
    }

    public boolean DateTimeInFuture(Calendar cal1) {
        Calendar cal2 = Calendar.getInstance();
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA)) return false;
        if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA)) return true;
        if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) return false;
        if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) return true;
        if (cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR)) return false;
        if (cal1.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR)) return true;
        return cal1.get(Calendar.HOUR_OF_DAY)*60+cal1.get(Calendar.MINUTE) > cal2.get(Calendar.HOUR_OF_DAY)*60+cal2.get(Calendar.MINUTE);
    }

    public boolean TimeInFuture(Calendar cal1) {
        Calendar cal2 = Calendar.getInstance();
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return cal1.get(Calendar.HOUR_OF_DAY)*60+cal1.get(Calendar.MINUTE) > cal2.get(Calendar.HOUR_OF_DAY)*60+cal2.get(Calendar.MINUTE);
    }


    public boolean Before(Calendar cal2, Calendar cal1) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        if (cal1.get(Calendar.ERA) < cal2.get(Calendar.ERA)) return false;
        if (cal1.get(Calendar.ERA) > cal2.get(Calendar.ERA)) return true;
        if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) return false;
        if (cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) return true;
        if (cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR)) return false;
        if (cal1.get(Calendar.DAY_OF_YEAR) > cal2.get(Calendar.DAY_OF_YEAR)) return true;
        return cal1.get(Calendar.HOUR_OF_DAY)*60+cal1.get(Calendar.MINUTE) > cal2.get(Calendar.HOUR_OF_DAY)*60+cal2.get(Calendar.MINUTE);
    }

    public boolean TimeCollision(Calendar cal1, Calendar cal2, Calendar cal3, Calendar cal4){
        int a1 = cal1.get(Calendar.HOUR_OF_DAY)*60+cal1.get(Calendar.MINUTE);
        int a2 = cal2.get(Calendar.HOUR_OF_DAY)*60+cal2.get(Calendar.MINUTE);
        int b1 = cal3.get(Calendar.HOUR_OF_DAY)*60+cal3.get(Calendar.MINUTE);
        int b2 = cal4.get(Calendar.HOUR_OF_DAY)*60+cal4.get(Calendar.MINUTE);
        if(a1<b1 && a2<=b1 || a1>=b2 && a2>b2){
            return false;
        } else  return true;
    }

    public boolean DateEquals(Calendar cal1, Calendar cal2){
        if (cal1.get(Calendar.ERA) != cal2.get(Calendar.ERA)) return false;
        if (cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR)) return false;
        if (cal1.get(Calendar.DAY_OF_YEAR) != cal2.get(Calendar.DAY_OF_YEAR)) return false;
        return true;
    }

    public boolean TimeEquals(Calendar cal1, Calendar cal2){
        if(cal1.get(Calendar.HOUR) != cal2.get(Calendar.HOUR)) return false;
        if(cal1.get(Calendar.MINUTE) != cal2.get(Calendar.MINUTE)) return false;
        return true;
    }


}
