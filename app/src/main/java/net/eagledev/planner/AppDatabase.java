package net.eagledev.planner;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Action.class, Reminder.class, Routine.class, Aim.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase{
    public abstract AppDao appDao();
}
