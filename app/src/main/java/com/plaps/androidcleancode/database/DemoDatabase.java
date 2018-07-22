package com.plaps.androidcleancode.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.plaps.androidcleancode.database.data.Notes;
import com.plaps.androidcleancode.database.data.NotesDao;

@Database(entities = {Notes.class}, version = DemoDatabase.VERSION)
public abstract class DemoDatabase extends RoomDatabase {

    static final int VERSION = 1;

    public abstract NotesDao getProductDao();

}
