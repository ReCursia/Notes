package com.example.notes.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {
    private static final String DB_NAME = "notes2.db";
    private static NotesDatabase instance;

    public static NotesDatabase getInstance(Context context) {
        NotesDatabase localInstance = instance;
        if (localInstance == null) {
            synchronized (NotesDatabase.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = (localInstance = Room.databaseBuilder(context, NotesDatabase.class, DB_NAME).build());
                }
            }
        }
        return localInstance;
    }

    public abstract NotesDao notesDao();
}
