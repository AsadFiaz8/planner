package net.eagledev.planner;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.List;

@Dao
public interface AppDao {

    //                                                 <<< Action >>>

    @Insert
    public void addAction(Action action);

    @Query("select * from actions")
    public List<Action> getActions();

    @Query("DELETE FROM actions")
    public void nukeActionsTable();

    @Query("DELETE FROM actions WHERE id = :id")
    public void deleteAction(int id);

    @Query( "SELECT MAX(id) from actions")
    public int getMaxActionID();

    @Query("SELECT * FROM actions WHERE start_day = :day AND start_month = :month AND start_year = :year")
    public List<Action> getActionsFromDay(int day, int month, int year);

    @Query("SELECT * FROM actions WHERE start_month = :month AND start_year = :year")
    public List<Action> getActionsFromMonth(int month, int year);

    @Query("SELECT * FROM actions WHERE id = :id")
    public Action idAction(int id);

    @Update
    public void updateAction(Action action);

    //                                                 <<< Reminders >>>

    @Insert
    public void addReminder(Reminder reminder);

    @Query("SELECT * FROM reminders")
    public List<Reminder> getReminders();

    @Query("DELETE FROM reminders")
    public void nukeRemindersTable();

    @Query("DELETE FROM reminders WHERE id = :id")
    public void deleteReminder(int id);

    @Query("SELECT MAX(id) FROM reminders")
    public  int getMaxRemindersID();

    @Query("SELECT * FROM reminders WHERE day = :day AND month = :month AND year = :year")
    public List<Reminder> getRemindersFromDay(int day, int month, int year);

    @Query("SELECT * FROM reminders WHERE id = :id")
    public Reminder idReminder(int id);

    @Query("SELECT * FROM reminders where done = 0")
    public List<Reminder> getUndoneRminders();

    @Update
    public void updateReminder(Reminder reminder);

    //                                                 <<< Routines >>>

    @Insert
    public void addRoutine(Routine routine);

    @Query("DELETE FROM routines")
    public void nukeRoutinesTable();

    @Query("DELETE FROM routines WHERE id = :id")
    public void deleteRoutine(int id);

    @Query("SELECT * FROM routines")
    public List<Routine> getRoutines();

    @Query("SELECT MAX(id) from routines")
    public int getMaxRoutinesID();

    @Query("SELECT COUNT() FROM routines")
    public int getRoutinesCount();

    @Query("SELECT * FROM routines WHERE id = :id")
    public Routine idRoutine(int id);

    @Update
    public void updateRoutine(Routine routine);

    @Query("SELECT * FROM routines WHERE monday")
    public List<Routine> getMonday();

    @Query("SELECT * FROM routines WHERE tuesday")
    public List<Routine> getTuesday();

    @Query("SELECT * FROM routines WHERE wednesday")
    public List<Routine> getWednesday();

    @Query("SELECT * FROM routines WHERE thursday")
    public List<Routine> getThursday();

    @Query("SELECT * FROM routines WHERE friday")
    public List<Routine> getFriday();

    @Query("SELECT * FROM routines WHERE saturday")
    public List<Routine> getSaturday();

    @Query("SELECT * FROM routines WHERE sunday")
    public List<Routine> getSunday();

    //                                                 <<< Aims >>>

    @Insert
    public void addAim(Aim aim);

    @Query("DELETE FROM aims")
    public void nukeAimsTable();

    @Query("DELETE FROM aims WHERE id = :id")
    public void deleteAim(int id);

    @Query("SELECT * FROM aims")
    public List<Aim> getAims();

    @Query("SELECT MAX(id) FROM aims")
    public int getMaxAimsID();

    @Query("SELECT * FROM aims WHERE id=:id")
    public Aim idAim(int id);

    @Update
    public void updateAim(Aim aim);

    @Query("SELECT * FROM aims WHERE type = :type")
    public List<Aim> getAimsType(int type);

    @Query("SELECT * FROM aims WHERE year = :year AND month = :month AND day = :day")
    public List<Aim> getAimsDate(int year, int month, int day);

    @Query("SELECT * FROM aims WHERE year = :year AND month = :month AND day = :day AND type = :type")
    public List<Aim> getAimsDateType(int year, int month, int day, int type);

    @Query("SELECT * FROM aims WHERE completed = :completed")
    public List<Aim> getAimsCompleted(boolean completed);


    //Tasks

    @Insert
    public void addTask(Task task);

    @Query("DELETE FROM tasks")
    public void nukeTasksTable();

    @Query("DELETE FROM tasks WHERE id = :id")
    public void deleteTask(int id);

    @Query("SELECT * FROM tasks")
    public List<Task> getTasks();

    @Query("SELECT MAX(id) FROM tasks")
    public int getMaxTasksID();

    @Query("SELECT * FROM tasks WHERE id=:id")
    public Task idTask(int id);

    @Update
    public void updateTask(Task task);

    @Query("SELECT * FROM tasks WHERE repeat_type = :type")
    public List<Task> getTasksRepeatType(int type);

    @Query("SELECT * FROM tasks WHERE year = :year AND month = :month AND day = :day")
    public List<Task> getTaskDate(int year, int month, int day);

    @Query("SELECT * FROM tasks WHERE completed = :completed AND year = :year AND month = :month AND day = :day")
    public List<Task> getTasksCompleted(boolean completed, int year, int month, int day);





}
