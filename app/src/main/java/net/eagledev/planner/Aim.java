package net.eagledev.planner;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "aims")
public class Aim {




    @PrimaryKey
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "type")
    private int type; //                0 - day;  1 - month; 2 - year; 4 - custom;
    @ColumnInfo(name = "day")
    private int day;
    @ColumnInfo(name = "month")
    private int month;
    @ColumnInfo(name = "year")
    private int year;
    @ColumnInfo(name = "completed")
    boolean completed;
    @ColumnInfo(name = "start_day")
    int startDay;
    @ColumnInfo(name = "start_month")
    int startMonth;
    @ColumnInfo(name = "start_year")
    int startYear;
    public Aim() {

    }

    public Aim(String name, int type,Calendar start, Calendar date) {
         id = MainActivity.appDatabase.appDao().getMaxAimsID()+1;
         this.name = name;
         this.type = type;
         this.day = date.get(Calendar.DAY_OF_MONTH);
         this.month = date.get(Calendar.MONTH);
         this.year = date.get(Calendar.YEAR);
         startDay = start.get(Calendar.DAY_OF_MONTH);
        startMonth = start.get(Calendar.MONTH);
        startYear = start.get(Calendar.YEAR);
    }

    public Aim(int id, String name, int type, Calendar startDate, Calendar date) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.day = date.get(Calendar.DAY_OF_MONTH);
        this.month = date.get(Calendar.MONTH);
        this.year = date.get(Calendar.YEAR);
        startDay = startDate.get(Calendar.DAY_OF_MONTH);
        startMonth = startDate.get(Calendar.MONTH);
        startYear = startDate.get(Calendar.YEAR);
    }
    public Aim(String name, int type,Calendar start, Calendar date, boolean completed) {
        id = MainActivity.appDatabase.appDao().getMaxAimsID()+1;
        this.name = name;
        this.type = type;
        this.day = date.get(Calendar.DAY_OF_MONTH);
        this.month = date.get(Calendar.MONTH);
        this.year = date.get(Calendar.YEAR);
        startDay = start.get(Calendar.DAY_OF_MONTH);
        startMonth = start.get(Calendar.MONTH);
        startYear = start.get(Calendar.YEAR);
        this.completed = completed;
    }

    public Aim(int id, String name, int type, Calendar startDate, Calendar date, boolean completed) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.day = date.get(Calendar.DAY_OF_MONTH);
        this.month = date.get(Calendar.MONTH);
        this.year = date.get(Calendar.YEAR);
        startDay = startDate.get(Calendar.DAY_OF_MONTH);
        startMonth = startDate.get(Calendar.MONTH);
        startYear = startDate.get(Calendar.YEAR);
        this.completed = completed;
    }

    public Calendar getStart() {
         Calendar c = Calendar.getInstance();
         c.set(startYear, startMonth, startDay);
         return c;
    }

    public void setStart(Calendar c) {
         startDay = c.get(Calendar.DAY_OF_MONTH);
         startMonth = c.get(Calendar.MONTH);
         startYear = c.get(Calendar.YEAR);
    }

    public Calendar getStop() {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        return c;
    }

    public void setStop(Calendar c) {
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }
}
