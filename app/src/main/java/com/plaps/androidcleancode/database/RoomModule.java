package com.plaps.androidcleancode.database;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.plaps.androidcleancode.database.data.NotesDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private DemoDatabase demoDatabase;

    public RoomModule(Application mApplication) {
        demoDatabase = Room.databaseBuilder(mApplication, DemoDatabase.class, "demo-db").allowMainThreadQueries().build();
    }

    @Singleton
    @Provides
    DemoDatabase providesRoomDatabase() {
        return demoDatabase;
    }

    @Singleton
    @Provides
    NotesDao providesProductDao(DemoDatabase demoDatabase) {
        return demoDatabase.getProductDao();
    }

    @Singleton
    @Provides
    NotesRepository productRepository(NotesDao productDao) {
        return new ProductDataSource(productDao);
    }

}
