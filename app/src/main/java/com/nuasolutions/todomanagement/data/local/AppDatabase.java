package com.nuasolutions.todomanagement.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.nuasolutions.todomanagement.data.local.dao.AccessTokenDAO;
import com.nuasolutions.todomanagement.data.local.dao.TodoDAO;
import com.nuasolutions.todomanagement.data.local.entity.AccessTokenEntity;
import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;
import com.nuasolutions.todomanagement.data.local.entity.TodoItemTypeConverter;

@Database(entities = {AccessTokenEntity.class, TodoEntity.class}, version = 1, exportSchema = false)
@TypeConverters({TodoItemTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract AccessTokenDAO accessTokenDAO();
    public abstract TodoDAO todoDAO();

}
