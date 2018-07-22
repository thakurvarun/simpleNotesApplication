package com.plaps.androidcleancode.database.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM Notes WHERE id=:id")
    LiveData<Notes> findById(int id);

    @Query("SELECT * FROM Notes")
    LiveData<List<Notes>> findAll();

    @Query("SELECT * FROM Notes")
    List<Notes> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Notes product);

    @Delete
    int delete(Notes product);

}
