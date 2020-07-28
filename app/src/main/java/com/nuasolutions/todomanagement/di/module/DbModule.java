package com.nuasolutions.todomanagement.di.module;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.nuasolutions.todomanagement.data.local.AppDatabase;
import com.nuasolutions.todomanagement.data.local.dao.AccessTokenDAO;
import com.nuasolutions.todomanagement.data.local.dao.TodoDAO;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @Singleton
    AppDatabase provideDatabase(@NonNull Application application) {
        return Room.databaseBuilder(application,
            AppDatabase.class, "nuasolutionsTODO.db")
                .allowMainThreadQueries().build();
    }

    @Provides
    @Singleton
    AccessTokenDAO provideAccessTokenDAO(@NonNull AppDatabase appDatabase) {
        return appDatabase.accessTokenDAO();
    }

    @Provides
    @Singleton
    TodoDAO provideTodoDAO(@NonNull AppDatabase appDatabase) {
        return appDatabase.todoDAO();
    }
}
