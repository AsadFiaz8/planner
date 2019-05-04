package net.eagledev.planner;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "reminders")
public class Reminder {


    @PrimaryKey
    private int id;

    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo(name = "year")
    int year;
    @ColumnInfo(name = "month")
    int month;
    @ColumnInfo(name = "day")
    int day;
    @ColumnInfo(name = "hour")
    int hour;
    @ColumnInfo(name = "minute")
    int minute;
    @ColumnInfo(name = "done")
    int done;

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public void setDate(Calendar c) {
        minute = c.get(Calendar.MINUTE);
        hour = c.get(Calendar.HOUR_OF_DAY);
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
    }

    public Calendar getDate() {
        Calendar cal = new Calendar() {
            @Override
            protected void computeTime() {

            }

            @Override
            protected void computeFields() {

            }

            @Override
            public void add(int i, int i1) {

            }

            @Override
            public void roll(int i, boolean b) {

            }

            @Override
            public int getMinimum(int i) {
                return 0;
            }

            @Override
            public int getMaximum(int i) {
                return 0;
            }

            @Override
            public int getGreatestMinimum(int i) {
                return 0;
            }

            @Override
            public int getLeastMaximum(int i) {
                return 0;
            }
        };
        cal.set(year, month , day , hour , minute);
        return cal;
    }



    public Reminder() {

    }

    public Reminder(int id, String name, Calendar date) {
        this.id = id;
        this.name = name;
        year = date.get(Calendar.YEAR);
        month = date.get(Calendar.MONTH);
        day = date.get(Calendar.DAY_OF_MONTH);
        hour = date.get(Calendar.HOUR_OF_DAY);
        minute = date.get(Calendar.MINUTE);
        done = 0;
    }

    public Reminder(int id, String name, Calendar date, boolean done) {
        this.id = id;
        this.name = name;
        year = date.get(Calendar.YEAR);
        month = date.get(Calendar.MONTH);
        day = date.get(Calendar.DAY_OF_MONTH);
        hour = date.get(Calendar.HOUR_OF_DAY);
        minute = date.get(Calendar.MINUTE);
        if(done){
            this.done = 1;
        } else this.done = 0;
    }

}
