package net.eagledev.planner;

//import android.arch.persistence.room.ColumnInfo;
//import android.arch.persistence.room.Entity;
//import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Color;
import android.util.Log;

import java.util.Calendar;

@Entity(tableName = "actions")
public class Action {


    @PrimaryKey
    private int id;
    @ColumnInfo(name = "desc")
    String desc;
    @ColumnInfo(name = "icon")
    private int icon;
    @ColumnInfo(name = "color")
    private int color;

    //Date start
    @ColumnInfo(name = "start_year")
    private int start_year;
    @ColumnInfo(name = "start_month")
    private int start_month;
    @ColumnInfo(name = "start_day")
    private int start_day;
    @ColumnInfo(name = "start_hour")
    private int start_hour;
    @ColumnInfo(name = "start_minute")
    private int start_minute;


    //Date stop
    @ColumnInfo(name = "stop_year")
    private int stop_year;
    @ColumnInfo(name = "stop_month")
    private int stop_month;
    @ColumnInfo(name = "stop_day")
    private int stop_day;
    @ColumnInfo(name = "stop_hour")
    private int stop_hour;
    @ColumnInfo(name = "stop_minute")
    private int stop_minute;




    public Action() {

    }

    public Action(int id, String desc, Calendar date_start, Calendar date_stop, int icon, int color) {

        this.id = id;
        this.desc = desc;
        this.icon = icon;
        this.color= color;
        if(desc.equals("Empty")) {
            this.color = Color.GRAY;
        }
        setStart(date_start);
        setStop(date_stop);
    }

    public void set(int id, String desc, Calendar date_start, Calendar date_stop, int icon, int color){
        this.id = id;
        this.desc = desc;
        this.icon = icon;
        this.color= color;
        if(desc.equals("Empty")) {
            this.color = Color.GRAY;
        }
        setStart(date_start);
        setStop(date_stop);
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Calendar getStart() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, start_year);
        cal.set(Calendar.MONTH, start_month);
        cal.set(Calendar.DAY_OF_MONTH, start_day);
        cal.set(Calendar.HOUR_OF_DAY, start_hour);
        cal.set(Calendar.MINUTE, start_minute);
        return cal;
    }

    public void setStart(Calendar date_start) {
        start_year=date_start.get(Calendar.YEAR);
        start_month=date_start.get(Calendar.MONTH);
        start_day=date_start.get(Calendar.DAY_OF_MONTH);
        start_hour=date_start.get(Calendar.HOUR_OF_DAY);
        start_minute=date_start.get(Calendar.MINUTE);
    }

    public Calendar getStop() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, stop_year);
        cal.set(Calendar.MONTH, stop_month);
        cal.set(Calendar.DAY_OF_MONTH, stop_day);
        cal.set(Calendar.HOUR_OF_DAY, stop_hour);
        cal.set(Calendar.MINUTE, stop_minute);
        return cal;
    }

    public void setStop(Calendar date_stop) {
        stop_year=date_stop.get(Calendar.YEAR);
        stop_month=date_stop.get(Calendar.MONTH);
        stop_day=date_stop.get(Calendar.DAY_OF_MONTH);
        stop_hour=date_stop.get(Calendar.HOUR_OF_DAY);
        Log.e("Actrion set minutes", String.valueOf(date_stop.get(Calendar.MINUTE)));
        stop_minute=date_stop.get(Calendar.MINUTE);
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDesc() {
        return desc;
    }


    public int getIcon() {
        return icon;
    }

    public int getColor() {
        return color;
    }

    public int getTime() {
        return (getStopMinutes()-getStartMinutes());
    }

    public int getStartMinutes() {
        return getStart().get(Calendar.HOUR_OF_DAY)*60+getStart().get(Calendar.MINUTE);
    }

    public int getStopMinutes() {
        return getStop().get(Calendar.HOUR_OF_DAY)*60+getStop().get(Calendar.MINUTE);
    }

    public int getStart_year() {
        return start_year;
    }

    public void setStart_year(int start_year) {
        this.start_year = start_year;
    }

    public int getStart_month() {
        return start_month;
    }

    public void setStart_month(int start_month) {
        this.start_month = start_month;
    }

    public int getStart_day() {
        return start_day;
    }

    public void setStart_day(int start_day) {
        this.start_day = start_day;
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

    public int getStop_year() {
        return stop_year;
    }

    public void setStop_year(int stop_year) {
        this.stop_year = stop_year;
    }

    public int getStop_month() {
        return stop_month;
    }

    public void setStop_month(int stop_month) {
        this.stop_month = stop_month;
    }

    public int getStop_day() {
        return stop_day;
    }

    public void setStop_day(int stop_day) {
        this.stop_day = stop_day;
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
}
