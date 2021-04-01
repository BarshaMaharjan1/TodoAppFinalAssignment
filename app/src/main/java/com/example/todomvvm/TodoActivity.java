package com.example.todomvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.todomvvm.database.Todo;
import com.example.todomvvm.ui.todo.TodoFragment;

public class TodoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, TodoFragment.newInstance())
                    .commitNow();
        }
    }

    public void moveToUpdate(Todo todo) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("todo",todo);
        Fragment fragment= new UpdateFragment().newInstance();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }
}
