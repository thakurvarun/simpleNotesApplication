package com.plaps.androidcleancode.database;

import android.arch.lifecycle.LiveData;

import com.plaps.androidcleancode.database.data.Notes;

import java.util.List;

public interface NotesRepository {

    LiveData<Notes> findById(int id);

    LiveData<List<Notes>> findAll();

    List<Notes> getAllNotes();

    long insert(Notes product);

    int delete(Notes product);

}
