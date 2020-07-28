package com.nuasolutions.todomanagement.data.repository;


import androidx.annotation.NonNull;

import com.nuasolutions.todomanagement.data.NetworkBoundResource;
import com.nuasolutions.todomanagement.data.Resource;
import com.nuasolutions.todomanagement.data.local.dao.TodoDAO;
import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;
import com.nuasolutions.todomanagement.data.remote.api.TodoAPIService;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;

@Singleton
public class TodoRepository {
    private TodoDAO todoDAO;
    private TodoAPIService apiService;
    public TodoRepository(TodoDAO todoDAO, TodoAPIService apiService) {
        this.todoDAO = todoDAO;
        this.apiService = apiService;
    }

    public Observable<Resource<List<TodoEntity>>> loadTodoList() {
        return new NetworkBoundResource<List<TodoEntity>, List<TodoEntity>>() {

            @Override
            protected void saveCallResult(@NonNull List<TodoEntity> response) {
                todoDAO.insertTodos(response);
            }

            @Override
            protected boolean shouldFetch() {
                return true;
            }

            @NonNull
            @Override
            protected Flowable<List<TodoEntity>> loadFromDb() {
                List<TodoEntity> entities = todoDAO.getTodoList();
                if(entities == null || entities.isEmpty()) {
                    return Flowable.empty();
                }
                return Flowable.just(entities);
            }

            @NonNull
            @Override
            protected Observable<Resource<List<TodoEntity>>> createCall() {
                return apiService.fetchTodoList()
                    .flatMap(response -> Observable.just(response == null
                        ? Resource.error("Failed to fetch TODOs.", null)
                        : Resource.success(response)));
            }
        }.getAsObservable();
    }
}
