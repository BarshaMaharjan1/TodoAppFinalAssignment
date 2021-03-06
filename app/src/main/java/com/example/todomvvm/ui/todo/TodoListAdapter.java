package com.example.todomvvm.ui.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todomvvm.R;
import com.example.todomvvm.TodoActivity;
import com.example.todomvvm.database.Todo;
import com.example.todomvvm.database.TodoRepository;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder> {

    private final TaskCallback callback;

    class TodoViewHolder extends RecyclerView.ViewHolder {
        private TextView todoItemView, desc, datelist;
        private ImageView delete, update;


        private TodoViewHolder(View itemView) {
            super(itemView);
            todoItemView = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            delete = itemView.findViewById(R.id.delete);
            update = itemView.findViewById(R.id.update);
            datelist = itemView.findViewById(R.id.datelist);

            //For deleting one by one items from list using callback
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    callback.onItemDeleted(mTodos.get(pos).getId());
//                    Toast toast=Toast.makeText("deleted",Toast.LENGTH_SHORT):
//                            toast.show();
                }
            });

            //For updating one by one items from list using callback
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    callback.onUpdate(mTodos.get(pos));
                }
            });


        }
    }

    private final LayoutInflater mInflater;
    private List<Todo> mTodos;

    TodoListAdapter(TodoFragment context, TaskCallback callback) {
        mInflater = LayoutInflater.from(context.getContext());
        this.callback = callback;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TodoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {

        if (mTodos != null) {


            Todo current = mTodos.get(position);
            holder.todoItemView.setText(current.getTitle());
            holder.desc.setText(current.getDetail());
            holder.datelist.setText(current.getDate());


        } else {

            holder.todoItemView.setText(R.string.no_todo);
        }
    }


    void setTodos(List<Todo> todos) {
        mTodos = todos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTodos != null)
            return mTodos.size();
        else return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    //interface for callback task og delete items and update items
    public interface TaskCallback {
        void onItemDeleted(int id);

        void onUpdate(Todo todo);
    }
}