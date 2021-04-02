package com.example.todomvvm;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.todomvvm.database.Todo;
import com.example.todomvvm.database.TodoRepository;
import com.example.todomvvm.ui.todo.TodoFragment;

import java.util.Calendar;
import java.util.Date;

public class AddTaskFragment extends Fragment {

    private EditText titleEditTExt;
    private EditText descEditText;
    private EditText setDate;
    RadioGroup mRadioGroup;
    private Button submitInsert;
    private TodoRepository repository;
    private ImageView calendar_date;
    private EditText date_edit;
    DatePickerDialog picker;


    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;


    public static AddTaskFragment newInstance() {
        return new AddTaskFragment();
    }

    public AddTaskFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for the fragment
        final View view;
        view = inflater.inflate(R.layout.fragment_add_task, container, false);
        titleEditTExt = (EditText) view.findViewById(R.id.title_entry);
        descEditText = (EditText) view.findViewById(R.id.desc_entry);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        submitInsert = (Button) view.findViewById(R.id.submit_btn);
        calendar_date = (ImageView) view.findViewById(R.id.imgDate);
        date_edit = (EditText) view.findViewById(R.id.date_edit);


        repository = new TodoRepository(getActivity().getApplication());

        //Inserting the tasks in list
        submitInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditTExt.getText().toString();
                String desc = descEditText.getText().toString();
                int priority = getPriorityFromViews();
                String date = date_edit.getText().toString();

                Todo todo = new Todo(title.trim(), desc, priority, date);
                repository.insert(todo);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, TodoFragment.newInstance())
                        .commitNow();
                // Toast.makeText(requireContext(),"Successfully insert data",Toast.LENGTH_SHORT).show();

            }

            // Setting the priority for High,Medium and low
            private int getPriorityFromViews() {
                int priority = 1;
                int checkedId = ((RadioGroup) view.findViewById(R.id.radioGroup)).getCheckedRadioButtonId(); //Made view final

                switch (checkedId) {
                    case R.id.radButton1:
                        priority = PRIORITY_HIGH;
                        break;
                    case R.id.radButton2:
                        priority = PRIORITY_MEDIUM;
                        break;
                    case R.id.radButton3:
                        priority = PRIORITY_LOW;
                        break;
                }
                return priority;
            }

            private void setPriorityInViews(int priority) {
                switch (priority) {
                    case PRIORITY_HIGH:
                        ((RadioGroup) view.findViewById(R.id.radioGroup)).check(R.id.radButton1);
                        break;
                    case PRIORITY_MEDIUM:
                        ((RadioGroup) view.findViewById(R.id.radioGroup)).check(R.id.radButton2);
                        break;
                    case PRIORITY_LOW:
                        ((RadioGroup) view.findViewById(R.id.radioGroup)).check(R.id.radButton3);
                }

            }
        });


        //For date picker as calendar
        calendar_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                picker = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date_edit.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        return view;


    }


}