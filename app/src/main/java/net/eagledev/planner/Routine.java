package net.eagledev.planner;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.util.Log;

import java.util.Calendar;

@Entity(tableName = "routines")
public class Routine {

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "icon")
    private int icon;
    @ColumnInfo(name = "color")
    private int color;
    @ColumnInfo(name = "date_year")
    private int date_year;
    @ColumnInfo(name = "date_month")
    private int date_month;
    @ColumnInfo(name = "date_day")
    private int date_day;


    @ColumnInfo(name = "start_hour")
    private int start_hour;
    @ColumnInfo(name = "start_moinute")
    private int start_minute;
    @ColumnInfo(name = "stop_hour")
    private int stop_hour;
    @ColumnInfo(name = "stop_minute")
    private int stop_minute;

    @ColumnInfo(name = "monday")
    private boolean monday;
    @ColumnInfo(name = "tuesday")
    private boolean tuesday;
    @ColumnInfo(name = "wednesday")
    private boolean wednesday;
    @ColumnInfo(name = "thursday")
    private boolean thursday;
    @ColumnInfo(name = "friday")
    private boolean friday;
    @ColumnInfo(name = "saturday")
    private  boolean saturday;
    @ColumnInfo(name = "sunday")
    private boolean sunday;


    public Routine() {

    }

    public Routine(int id, String name, int icon, int color, Calendar start, Calendar stop, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.color = color;
        date_year = start.get(Calendar.YEAR);
        date_month = start.get(Calendar.MONTH);
        date_day = start.get(Calendar.DAY_OF_MONTH);
        start_hour = start.get(Calendar.HOUR_OF_DAY);
        start_minute = start.get(Calendar.MINUTE);
        stop_hour = stop.get(Calendar.HOUR_OF_DAY);
        stop_minute = stop.get(Calendar.MINUTE);
        this.monday = monday;
        this. tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        Log.e("ROUTINE","                         start "+start_hour+"   stop "+stop_hour);
    }

    public Calendar start() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, start_hour);
        cal.set(Calendar.MINUTE, start_minute);
        return  cal;
    }

    public Calendar stop() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, stop_hour);
        cal.set(Calendar.MINUTE, stop_minute);
        return  cal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getDate_year() {
        return date_year;
    }

    public void setDate_year(int date_year) {
        this.date_year = date_year;
    }

    public int getDate_month() {
        return date_month;
    }

    public void setDate_month(int date_month) {
        this.date_month = date_month;
    }

    public int getDate_day() {
        return date_day;
    }

    public void setDate_day(int date_day) {
        this.date_day = date_day;
    }

    public int getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(int start_hour) {
        this.start_hour = start_hour;
    }

    public int getStart_minute() {
        return start_minute;
    }

    public void setStart_minute(int start_minute) {
        this.start_minute = start_minute;
    }

    public int getStop_hour() {
        return stop_hour;
    }

    public void setStop_hour(int stop_hour) {
        this.stop_hour = stop_hour;
    }

    public int getStop_minute() {
        return stop_minute;
    }

    public void setStop_minute(int stop_minute) {
        this.stop_minute = stop_minute;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }


}
