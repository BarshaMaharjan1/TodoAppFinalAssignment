package com.example.todomvvm.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "todo_table")
public class Todo implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String title;
    private String detail;
    private int priority;
    private String date;

    public Todo(@NonNull String title, String detail, int priority, String date ) {
        this.title = title;
        this.detail = detail;
        this.priority = priority;
        this.date = date;
    }

    @Ignore
    public Todo(int id, String title, String detail, int priority) {
        this.id = id;
        this.title = title;
        this.priority = priority;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String description) {
        this.detail = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

