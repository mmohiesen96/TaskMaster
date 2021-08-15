package com.example.taskmaster;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 3)
public abstract class TaskDB extends RoomDatabase {
    public abstract TaskDAO taskDAO();
}
