package com.example.todomvvm.ui.todo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

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

import static androidx.lifecycle.ViewModelProviders.of;

public class TodoFragment extends Fragment {

    private TodoViewModel mTodoViewModel;

    public static TodoFragment newInstance() {
        return new TodoFragment();
    }

    public TodoFragment() {
        // Required empty public constructor
    }

    private FloatingActionButton floatingButton;

    private TodoListAdapter adapter;

    private EditText titleEditTExt;
    private EditText descEditText;
    private EditText setDate;
    RadioGroup mRadioGroup;
    private Button submitButton;
    private TodoRepository repository;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//      return inflater.inflate(R.layout.main_fragment, container, false);

        View view;
        view = inflater.inflate(R.layout.main_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        titleEditTExt=view.findViewById(R.id.title_entry);
        descEditText=view.findViewById(R.id.desc_label);

        this.adapter = new TodoListAdapter(this, new TodoListAdapter.TaskCallback() {


            @Override
            public void onItemDeleted(int id) {
                mTodoViewModel.deleteTodo(id);
            }

            @Override
            public void onUpdate(Todo todo) {
                // TODO , move to update activity / fragment from here with the current todo
                ((TodoActivity)getActivity()).moveToUpdate(todo);
            }
        });
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        floatingButton = view.findViewById(R.id.float_btn);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // deprecated, mTodoViewModel = of(this).get(TodoViewModel.class);
        mTodoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);

        // TODO: Use the ViewModel

        // Add an observer on the LiveData returned by getTodos.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mTodoViewModel.getTodos().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable final List<Todo> todos) {
                // Update the cached copy of the todos in the adapter.
                adapter.setTodos(todos);
            }
        });

        //switching between fragments
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, AddTaskFragment.newInstance())
                        .commitNow();

            }
        });



    }

}
