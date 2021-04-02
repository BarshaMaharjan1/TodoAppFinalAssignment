package com.example.todomvvm.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Todo.class}, version = 1, exportSchema = false)

public abstract class TodoRoomDatabase extends RoomDatabase {

    public abstract TodoDao todoDao();

    private static TodoRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TodoRoomDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized ((TodoRoomDatabase.class)) {
                if (INSTANCE == null) {
                    // create DB
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoRoomDatabase.class, "it is a todo database")
                            .addCallback(RoomDbCallBack) // insert test data
                            .build();
                }
            }

        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback RoomDbCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            TodoDao dao = INSTANCE.todoDao();

        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}