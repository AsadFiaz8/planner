package net.eagledev.planner;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

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


    public Goal(){

    }

    public Goal(int id, String name, int priority, String comment, int time){
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.comment = comment;
        this.time = time;
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
}
