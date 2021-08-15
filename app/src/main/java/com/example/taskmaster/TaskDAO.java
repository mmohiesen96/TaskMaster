package com.example.taskmaster;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDAO{
    @Insert
    void insertOne(Task task);

    @Query("SELECT * FROM tasks WHERE id LIKE :id")
    Task findById(long id);

    @Query("SELECT * FROM tasks")
    List<Task> findAll();

    @Delete
    void delete(Task task);

}
