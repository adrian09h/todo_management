package com.nuasolutions.todomanagement.data.remote.api;

import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;
import com.nuasolutions.todomanagement.data.local.entity.TodoItemEntity;
import com.nuasolutions.todomanagement.data.remote.model.requests.CreateTodoItemRequest;
import com.nuasolutions.todomanagement.data.remote.model.requests.CreateTodoRequest;


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

    @POST("/todos")
    Observable<TodoEntity> createTodo(@Body CreateTodoRequest request);

    @DELETE("/todos/{todo_id}")
    Observable<TodoEntity> deleteTodo(@Path("todo_id") Long todoId);

    @PUT("/todos/{todo_id}")
    Observable<TodoEntity> updateTodo(@Path("todo_id") Long todoId, @Body TodoEntity todoEntity);

    @POST("/todos/{todo_id}/items")
    Observable<TodoEntity> createTodoItem(@Path("todo_id") Long todoId, @Body CreateTodoItemRequest request);

    @PUT("/todos/{todo_id}/items/{item_id}")
    Observable<TodoEntity> updateTodoItem(@Path("todo_id") Long todoId,
                                          @Path("item_id") Long item_id,
                                          @Body TodoItemEntity todoItemEntity);

    @DELETE("/todos/{todo_id}/items/{item_id}")
    Observable<TodoEntity> deleteTodoItem(@Path("todo_id") Long todoId,
                                          @Path("item_id") Long item_id);


}
