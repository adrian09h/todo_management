package com.nuasolutions.todomanagement.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.nuasolutions.todomanagement.data.local.dao.AccessTokenDAO;
import com.nuasolutions.todomanagement.data.local.dao.TodoDAO;
import com.nuasolutions.todomanagement.data.local.entity.AccessTokenEntity;

@Database(entities = {AccessTokenEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AccessTokenDAO accessTokenDAO();
    public abstract TodoDAO todoDAO();

}
