package com.example.todomvvm.ui.todo;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todomvvm.AddTaskFragment;
import com.example.todomvvm.R;
import com.example.todomvvm.TodoActivity;
import com.example.todomvvm.database.Todo;
import com.example.todomvvm.database.TodoRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class TodoFragment extends Fragment {

    private TodoViewModel mTodoViewModel;

    public static TodoFragment newInstance() {
        return new TodoFragment();
    }

    public TodoFragment() {

    }

    private FloatingActionButton floatingButton;
    private TodoListAdapter adapter;
    private TodoRepository repository;


    //Adding menu bar
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.task_menu, menu);
    }

    //Menu items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // delete all the tasks from the list
            case R.id.mdelete: {
                repository.deleteAll();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, TodoFragment.newInstance())
                        .commitNow();

                return true;
            }

            case R.id.item_share: {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"barshamhrzan@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Todo Test");
                i.putExtra(Intent.EXTRA_TEXT, "Welcome to todo app list.");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {

                }
            }
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for the fragment

        View view;
        view = inflater.inflate(R.layout.main_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);


        this.adapter = new TodoListAdapter(this, new TodoListAdapter.TaskCallback() {


            @Override
            public void onItemDeleted(int id) {
                mTodoViewModel.deleteTodo(id);
            }


            @Override
            public void onUpdate(Todo todo) {
                ((TodoActivity) getActivity()).moveToUpdate(todo);
            }
        });

        setHasOptionsMenu(true);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        floatingButton = view.findViewById(R.id.float_btn);


        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        repository = new TodoRepository(getActivity().getApplication());
        mTodoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);


        mTodoViewModel.getTodos().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable final List<Todo> todos) {

                adapter.setTodos(todos);
            }
        });

        //switching the fragment through floating button
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, AddTaskFragment.newInstance()).addToBackStack(null)
                        .commit();
                // Toast.makeText(requireContext(),"add",Toast.LENGTH_SHORT).show();

            }
        });


    }


}
