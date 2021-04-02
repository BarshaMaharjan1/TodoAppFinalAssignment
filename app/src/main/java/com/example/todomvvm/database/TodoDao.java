package com.example.todomvvm.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todomvvm.database.Todo;

import java.util.List;
@Dao
public interface TodoDao {

    @Query("SELECT * FROM todo_table order by priority")
    LiveData<List<Todo>> getAllTasks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Todo todo);

    @Query("delete from todo_table")
    void deleteAll();

    @Query("delete from todo_table where id = :id")
    void deleteTodo(int id);

    @Update()
    void update(Todo todo);
}

