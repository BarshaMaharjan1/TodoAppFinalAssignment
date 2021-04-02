package com.example.todomvvm;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.todomvvm.database.Todo;
import com.example.todomvvm.database.TodoRepository;
import com.example.todomvvm.ui.todo.TodoFragment;

import java.util.Calendar;

public class UpdateFragment extends Fragment {



    private EditText titleEditTExt;
    private EditText descEditText;
    private EditText setDate;
    RadioGroup mRadioGroup;
    private Button update_btn;
    private TodoRepository repository;


    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;
    private Todo data;
    DatePickerDialog picker;
    private ImageView calendar_date;



    public UpdateFragment() {

    }


    public static UpdateFragment newInstance() {
        return new UpdateFragment();

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=this.getArguments();
        assert bundle != null;
        data= (Todo) bundle.getSerializable("todo");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for the fragment
        final View view;

        view = inflater.inflate(R.layout.fragment_update, container, false);

        titleEditTExt = view.findViewById(R.id.title_entry);
        descEditText = view.findViewById(R.id.desc_entry);
        mRadioGroup = view.findViewById(R.id.radioGroup);
        update_btn = view.findViewById(R.id.update_btn);
        setDate = view.findViewById(R.id.date_edit);
        calendar_date= view.findViewById(R.id.calendar_date);

        repository = new TodoRepository(getActivity().getApplication());

        titleEditTExt.setText(Editable.Factory.getInstance().newEditable(data.getTitle()));
        descEditText.setText(Editable.Factory.getInstance().newEditable(data.getDetail()));
        setDate.setText(Editable.Factory.getInstance().newEditable(data.getDate()));
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditTExt.getText().toString();
                String desc = descEditText.getText().toString();
                String date = setDate.getText().toString();
                int priority = getPriorityFromViews();
                data.setTitle(title.trim());
                data.setDetail(desc);
                data.setPriority(priority);
                data.setDate(date);
                repository.update(data);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, TodoFragment.newInstance())
                        .commitNow();


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

                picker=new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        setDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                },year,month,day);
                picker.show();
            }
        });

        return view;
    }
}