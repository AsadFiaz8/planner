package net.eagledev.planner;

import android.graphics.drawable.Drawable;

import java.util.Calendar;

public final class Formatter {


    public String dateWithTime(Calendar cal){
        return z(cal.get(Calendar.HOUR_OF_DAY)) + ":" + z(cal.get(Calendar.MINUTE)) + " " + z(cal.get(Calendar.DAY_OF_MONTH)) + "/" + z(cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.YEAR);
    }

    public String Date(Calendar cal) {

        Calendar now = Calendar.getInstance();
        if(now.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
            if (now.get(Calendar.DAY_OF_YEAR)==cal.get(Calendar.DAY_OF_YEAR)) {
                return s(R.string.today);
            } else if(now.get(Calendar.DAY_OF_YEAR)==cal.get(Calendar.DAY_OF_YEAR)-1) {
                return s(R.string.tomorrow);
            } else if (now.get(Calendar.DAY_OF_YEAR)==cal.get(Calendar.DAY_OF_YEAR)+1) {
                return s(R.string.yesterday);
            }
        }
        String month = month(cal.get(Calendar.MONTH));
        return cal.get(Calendar.DAY_OF_MONTH) + " " + month + " " + cal.get(Calendar.YEAR);

    }

    public String DateForClock(Calendar cal) {
        String month = month(cal.get(Calendar.MONTH));
        return cal.get(Calendar.DAY_OF_MONTH) + "\n" + month + "\n" + cal.get(Calendar.YEAR);
    }

    public String DateText(Calendar cal) {
        Calendar now = Calendar.getInstance();
        if (now.get(Calendar.YEAR)==cal.get(Calendar.YEAR)){
            if (now.get(Calendar.DAY_OF_YEAR)==cal.get(Calendar.DAY_OF_YEAR)) {
                return s(R.string.today);
            } else if(now.get(Calendar.DAY_OF_YEAR)==cal.get(Calendar.DAY_OF_YEAR)-1) {
                return s(R.string.tomorrow);
            } else if (now.get(Calendar.DAY_OF_YEAR)==cal.get(Calendar.DAY_OF_YEAR)+1) {
                return s(R.string.yesterday);
            }
        }
        return z(cal.get(Calendar.DAY_OF_MONTH)) + "/" + z(cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.YEAR);

    }

    public String Time(Calendar cal) {
        return z(cal.get(Calendar.HOUR_OF_DAY)) + ":" + z(cal.get(Calendar.MINUTE));
    }

    public String z(int i){
        if(i>=0 && i <=9) {
            return 0+String.valueOf(i);
        } else return String.valueOf(i);
    }

    public String Month(int i) {
        String s = "";
        switch (i)
        {
            case 0:
                s = s(R.string.january);
                break;
            case 1:
                s = s(R.string.february);
            break;

            case 2:
                s = s(R.string.march);
            break;

            case 3:
                s = s(R.string.april);
            break;

            case 4:
                s = s(R.string.may);
            break;

            case 5:
                s = s(R.string.june);
            break;

            case 6:
                s = s(R.string.july);
            break;

            case 7:
                s = s(R.string.august);
            break;

            case 8:
                s = s(R.string.september);
            break;

            case 9:
                s = s(R.string.october);
            break;

            case 10:
                s = s(R.string.november);
            break;

            case 11:
                s = s(R.string.december);
            break;
        }
        return s;
    }

    private String month(int m) {
        String month = "";

        switch (m) {
            case 0:
                month = s(R.string.january2);
                break;
            case 1:
                month = s(R.string.february2);
                break;

            case 2:
                month = s(R.string.march2);
                break;

            case 3:
                month = s(R.string.april2);
                break;

            case 4:
                month = s(R.string.may2);
                break;

            case 5:
                month = s(R.string.june2);
                break;

            case 6:
                month = s(R.string.july2);
                break;

            case 7:
                month = s(R.string.august2);
                break;

            case 8:
                month = s(R.string.september2);
                break;

            case 9:
                month = s(R.string.october2);
                break;

            case 10:
                month = s(R.string.november2);
                break;

            case 11:
                month = s(R.string.december2);
                break;
        }
        return month;
    }

    public Drawable scaleDrawable(Drawable drawable, int scale){
        return scaleDrawable(drawable, scale);

    }


    private String s (int i) {
        return MainActivity.context.getResources().getString(i);
    }
}
