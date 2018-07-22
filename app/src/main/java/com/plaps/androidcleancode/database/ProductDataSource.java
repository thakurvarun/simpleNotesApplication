package com.plaps.androidcleancode.database;

import android.arch.lifecycle.LiveData;

import com.plaps.androidcleancode.database.data.Notes;
import com.plaps.androidcleancode.database.data.NotesDao;

import java.util.List;

import javax.inject.Inject;

public class ProductDataSource implements NotesRepository {

    private NotesDao productDao;

    @Inject
    public ProductDataSource(NotesDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public LiveData<Notes> findById(int id) {
        return productDao.findById(id);
    }

    @Override
    public LiveData<List<Notes>> findAll() {
        return productDao.findAll();
    }

    @Override
    public List<Notes> getAllNotes() {
        return productDao.getAllNotes();
    }

    @Override
    public long insert(Notes product) {
        return productDao.insert(product);
    }

    @Override
    public int delete(Notes product) {
        return productDao.delete(product);
    }
}
