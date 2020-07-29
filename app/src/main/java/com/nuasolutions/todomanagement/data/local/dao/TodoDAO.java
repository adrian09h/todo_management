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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertOneTodo(TodoEntity todoEntity);

    @Query("DELETE FROM 'TodoEntity' where id = :id")
    void deleteById(Long id);

    @Query("SELECT * FROM 'TodoEntity'")
    List<TodoEntity> getTodoList();

    @Query("SELECT * FROM 'TodoEntity' where id = :id")
    TodoEntity getTodoById(Long id);

}
