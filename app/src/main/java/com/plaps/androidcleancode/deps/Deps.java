package com.plaps.androidcleancode.deps;


import com.plaps.androidcleancode.database.DemoDatabase;
import com.plaps.androidcleancode.database.NotesRepository;
import com.plaps.androidcleancode.database.RoomModule;
import com.plaps.androidcleancode.database.data.NotesDao;
import com.plaps.androidcleancode.home.HomeActivity;
import com.plaps.androidcleancode.networking.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Varun on 7/17/18.
 */
@Singleton
@Component(dependencies = {},modules = {NetworkModule.class, RoomModule.class})
public interface Deps {
    void inject(HomeActivity homeActivity);

    NotesDao productDao();

    DemoDatabase demoDatabase();

    NotesRepository NotesRepository();

//    Application application();
}
