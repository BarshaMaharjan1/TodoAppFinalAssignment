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

/**
 * Room uses this DAO where you map a Java method call to an SQL query.
 * <p>
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */

@Dao
public interface TodoDao {

    @Query("SELECT * FROM todo_table order by priority")
    LiveData<List<Todo>> getAllTasks();

    // conflict resolution strategy - IGNORE allows insert of same Todo multiple times
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Todo todo);

    @Query("delete from todo_table")
    void deleteAll();

    @Query("delete from todo_table where id = :id")
    void deleteTodo(int id);

    @Update()
    void update(Todo todo);
}

