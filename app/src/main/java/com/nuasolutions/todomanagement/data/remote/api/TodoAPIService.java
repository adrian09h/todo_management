package com.nuasolutions.todomanagement.data.remote.api;

import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;


import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TodoAPIService {
    @GET("/todos")
    Observable<List<TodoEntity>> fetchTodoList();

    @DELETE("/todos/{todo_id}")
    Completable deleteTodo(@Path("todo_id") String todoId);


}
