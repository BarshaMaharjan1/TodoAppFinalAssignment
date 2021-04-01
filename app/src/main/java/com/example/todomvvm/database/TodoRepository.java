package com.example.todomvvm.database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.todomvvm.database.Todo;
import com.example.todomvvm.database.TodoDao;
import com.example.todomvvm.database.TodoRoomDatabase;

import java.util.List;

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

public class TodoRepository {
    static TodoRepository INSTANCE;
    TodoRoomDatabase mdb;
    TodoDao mdao;



    public TodoRepository(Application application) {
        mdb=TodoRoomDatabase.getDatabase(application);
        mdao=mdb.todoDao();
    }


    public static TodoRepository getTodoRepository(Application application)
    {
        if (INSTANCE == null){
            INSTANCE = new TodoRepository(application);
        }
        return INSTANCE;
    }
    public LiveData<List<Todo>> getAllTask()
    {
        return mdao.getAllTasks();
    }

    public void deleteAll(){
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mdao.deleteAll();
            }
        });
    }

    public void update(final Todo todo){
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mdao.update(todo);
            }
        });
    }

    public void insert(final Todo todo){
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mdao.insert(todo);
            }
        });
    }

    public void deleteTodo(final int id) {
        TodoRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mdao.deleteTodo(id);
            }
        });
    }
}
