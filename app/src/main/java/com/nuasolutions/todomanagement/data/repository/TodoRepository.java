package com.nuasolutions.todomanagement.data.repository;


import androidx.annotation.NonNull;

import com.nuasolutions.todomanagement.data.NetworkBoundResource;
import com.nuasolutions.todomanagement.data.Resource;
import com.nuasolutions.todomanagement.data.local.dao.TodoDAO;
import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;
import com.nuasolutions.todomanagement.data.remote.api.TodoAPIService;
import com.nuasolutions.todomanagement.data.remote.model.requests.CreateTodoItemRequest;
import com.nuasolutions.todomanagement.data.remote.model.requests.CreateTodoRequest;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Response;

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
                if (response != null) {
                    todoDAO.insertTodos(response);
                }
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

    public Observable<Resource<List<TodoEntity>>> createTodo(String title) {
        return new NetworkBoundResource<List<TodoEntity>, TodoEntity>() {

            @Override
            protected void saveCallResult(@NonNull TodoEntity response) {
                todoDAO.insertOneTodo(response);
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
            protected Observable<Resource<TodoEntity>> createCall() {
                return apiService.createTodo(new CreateTodoRequest(title))
                    .flatMap(response -> Observable.just(response == null
                        ? Resource.error("Failed to create TODO.", null)
                        : Resource.success(response)));
            }
        }.getAsObservable();
    }

    public Observable<Resource<TodoEntity>> updateTodo(TodoEntity todoEntity) {
        return new NetworkBoundResource<TodoEntity, TodoEntity>() {

            @Override
            protected void saveCallResult(@NonNull TodoEntity response) {

            }

            @Override
            protected boolean shouldFetch() {
                return true;
            }

            @NonNull
            @Override
            protected Flowable<TodoEntity> loadFromDb() {
                TodoEntity entitity = todoDAO.getTodoById(todoEntity.getId());
                if(entitity == null) {
                    return Flowable.empty();
                }
                return Flowable.just(entitity);
            }

            @NonNull
            @Override
            protected Observable<Resource<TodoEntity>> createCall() {
                todoDAO.insertOneTodo(todoEntity);
                return apiService.updateTodo(todoEntity.getId(), todoEntity)
                    .flatMap(response -> Observable.just(response == null
                        ? Resource.error("Failed to create TODO.", null)
                        : Resource.success(response)));
            }
        }.getAsObservable();
    }

    public Observable<Resource<List<TodoEntity>>> deleteTodo(Long todoId) {
        return new NetworkBoundResource<List<TodoEntity>, TodoEntity>() {

            @Override
            protected void saveCallResult(@NonNull TodoEntity response) {

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
            protected Observable<Resource<TodoEntity>> createCall() {
                todoDAO.deleteById(todoId);
                return apiService.deleteTodo(todoId)
                    .flatMap(res -> Observable.just(res == null
                        ? Resource.error("Failed to delete TODO.", null)
                        : Resource.success(res)));
            }
        }.getAsObservable();
    }

    public Observable<Resource<TodoEntity>> createTodoItem(Long todoId, String name, boolean done) {
        return new NetworkBoundResource<TodoEntity, TodoEntity>() {

            @Override
            protected void saveCallResult(@NonNull TodoEntity response) {
                todoDAO.insertOneTodo(response);
            }

            @Override
            protected boolean shouldFetch() {
                return true;
            }

            @NonNull
            @Override
            protected Flowable<TodoEntity> loadFromDb() {
                TodoEntity entitity = todoDAO.getTodoById(todoId);
                if(entitity == null) {
                    return Flowable.empty();
                }
                return Flowable.just(entitity);
            }

            @NonNull
            @Override
            protected Observable<Resource<TodoEntity>> createCall() {
                return apiService.createTodoItem(todoId, new CreateTodoItemRequest(name, done))
                    .flatMap(response -> Observable.just(response == null
                        ? Resource.error("Failed to create TODO.", null)
                        : Resource.success(response)));
            }
        }.getAsObservable();
    }

    public Observable<Resource<TodoEntity>> deleteTodoItem(TodoEntity todoEntity, Long todoId, Long itemId) {
        return new NetworkBoundResource<TodoEntity, TodoEntity>() {

            @Override
            protected void saveCallResult(@NonNull TodoEntity response) {
            }

            @Override
            protected boolean shouldFetch() {
                return true;
            }

            @NonNull
            @Override
            protected Flowable<TodoEntity> loadFromDb() {
                TodoEntity entitity = todoDAO.getTodoById(todoId);
                if(entitity == null) {
                    return Flowable.empty();
                }
                return Flowable.just(entitity);
            }

            @NonNull
            @Override
            protected Observable<Resource<TodoEntity>> createCall() {
                todoDAO.insertOneTodo(todoEntity);
                return apiService.deleteTodoItem(todoId, itemId)
                    .flatMap(response -> Observable.just(response == null
                        ? Resource.error("Failed to delete TODO item.", null)
                        : Resource.success(response)));
            }
        }.getAsObservable();
    }

}
