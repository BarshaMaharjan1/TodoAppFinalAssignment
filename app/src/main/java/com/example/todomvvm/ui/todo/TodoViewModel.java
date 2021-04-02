package com.example.todomvvm.ui.todo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todomvvm.database.Todo;
import com.example.todomvvm.database.TodoRepository;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private TodoRepository mRepository;

    private LiveData<List<Todo>> mTodos;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TodoRepository(application);
        mTodos = mRepository.getAllTask();
    }

    LiveData<List<Todo>> getTodos() {
        return mTodos;
    }

    public void insert(Todo todo) {
        mRepository.insert(todo);
    }


    public void deleteTodo(int id) {
        mRepository.deleteTodo(id);
    }

}
