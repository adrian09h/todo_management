package com.nuasolutions.todomanagement.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nuasolutions.todomanagement.data.local.entity.AccessTokenEntity;
import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;

import java.util.List;

@Dao
public interface TodoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertTodos(List<TodoEntity> todoEntityList);
    @Query("SELECT * FROM 'TodoEntity'")
    List<TodoEntity> getTodoList();

}
