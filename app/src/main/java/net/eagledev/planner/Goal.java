package net.eagledev.planner;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "goals")
public class Goal {

    @PrimaryKey
    private  int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "priority")
    private int priority;

    @ColumnInfo(name = "comment")
    private String comment;

    @ColumnInfo(name = "time")
    private int time;

    @ColumnInfo(name = "completed")
    boolean completed;

    @ColumnInfo(name = "reminder")
    boolean reminder;

    @ColumnInfo(name = "repeat")
    boolean repeat;

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

    @ColumnInfo(name = "repeat_type")
    int repeat_type;

    @ColumnInfo(name = "repeat_gap")
    int repeat_gap;

    @ColumnInfo(name = "repeat_time")
    int repeat_time;

    @ColumnInfo(name = "days")
    String days = "0000000";

    @ColumnInfo (name = "label")
    String label;


    public Goal(){

    }

    public Goal(int id, String name, int priority, String comment, int time, boolean repeat, boolean reminder){
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.comment = comment;
        this.time = time;
        this.repeat = repeat;
        this.reminder = false;
        //Na razie bez powiadomień
        Calendar  calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_WEEK);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        completed = false;
    }



    public Goal(int id, String name, int priority, String comment, int time, String label, boolean repeat, boolean reminder, int repeat_type, int repeat_gap, int repeat_time, String days){
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.comment = comment;
        this.time = time;
        this.repeat = repeat;
        this.reminder = false;
        this.repeat_type = repeat_type;
        this.repeat_gap = repeat_gap;
        this.days = days;
        this.label = label;
        this.repeat_time = repeat_time;
        //Na razie bez powiadomień
        Calendar  calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_WEEK);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        completed = false;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
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

    public int getRepeat_type() {
        return repeat_type;
    }

    public void setRepeat_type(int repeat_type) {
        this.repeat_type = repeat_type;
    }

    public int getRepeat_gap() {
        return repeat_gap;
    }

    public void setRepeat_gap(int repeat_gap) {
        this.repeat_gap = repeat_gap;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public int getRepeat_time() {
        return repeat_time;
    }

    public void setRepeat_time(int repeat_time) {
        this.repeat_time = repeat_time;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
