package net.eagledev.planner;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

@Database(entities = {Action.class, Reminder.class, Routine.class, Aim.class, Task.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase{
    public abstract AppDao appDao();

    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

        }
    };

}
