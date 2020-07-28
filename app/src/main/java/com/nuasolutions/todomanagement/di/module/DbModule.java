package com.nuasolutions.todomanagement.di.module;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.nuasolutions.todomanagement.data.local.AppDB;
import com.nuasolutions.todomanagement.data.local.dao.AccessTokenDAO;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @Singleton
    AppDB provideDatabase(@NonNull Application application) {
        return Room.databaseBuilder(application,
            AppDB.class, "nuasolutionsTODO.db")
                .allowMainThreadQueries().build();
    }

    @Provides
    @Singleton
    AccessTokenDAO provideAccessTokenDAO(@NonNull AppDB appDatabase) {
        return appDatabase.accessTokenDAO();
    }

}
