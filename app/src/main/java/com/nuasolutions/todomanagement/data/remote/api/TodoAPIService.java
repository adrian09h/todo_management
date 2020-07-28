package com.nuasolutions.todomanagement.data.remote.api;

import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TodoAPIService {
    @GET("/todos")
    Observable<List<TodoEntity>> fetchTodoList();

}
