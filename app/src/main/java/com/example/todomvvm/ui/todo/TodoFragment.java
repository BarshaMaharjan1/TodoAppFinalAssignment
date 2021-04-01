package com.example.todomvvm.ui.todo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
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

import java.util.Calendar;
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
    private ImageView imgDate;
    private EditText date_edit;


//Adding menu and inflater
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.task_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mdelete :{
                repository.deleteAll();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, TodoFragment.newInstance())
                        .commitNow();

                return true;
            }

            case R.id.item_share: {

                String txt = "Todo share";
                String mimeType = "Share tasks";
                ShareCompat.IntentBuilder
                        .from(getActivity())
                        .setType(mimeType)
                        .setChooserTitle("Share you todo: ")
                        .setText(txt)
                        .startChooser();
            }
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

//      return inflater.inflate(R.layout.main_fragment, container, false);

        View view;
        view = inflater.inflate(R.layout.main_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        titleEditTExt = view.findViewById(R.id.title_entry);
        descEditText = view.findViewById(R.id.desc_label);
        imgDate=view.findViewById(R.id.imgDate);
        date_edit=view.findViewById(R.id.date_edit);




        this.adapter = new TodoListAdapter(this, new TodoListAdapter.TaskCallback() {


            @Override
            public void onItemDeleted(int id) {
                mTodoViewModel.deleteTodo(id);
            }


            @Override
            public void onUpdate(Todo todo) {
                // TODO , move to update activity / fragment from here with the current todo
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

        // deprecated, mTodoViewModel = of(this).get(TodoViewModel.class);
        repository = new TodoRepository(getActivity().getApplication());
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
