package com.nuasolutions.todomanagement.viewmodel;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nuasolutions.todomanagement.data.Resource;
import com.nuasolutions.todomanagement.data.local.dao.TodoDAO;
import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;
import com.nuasolutions.todomanagement.data.remote.api.TodoAPIService;
import com.nuasolutions.todomanagement.data.repository.TodoRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Action;

public class TodoListViewModel extends BaseViewModel {
    private ObservableInt emptyVisibility = new ObservableInt(View.GONE);
    private ObservableInt listVisibility = new ObservableInt(View.GONE);
    private TodoRepository repository;
    private MutableLiveData<Resource<List<TodoEntity>>> todoListLiveData = new MutableLiveData<>();

    @Inject
    public TodoListViewModel(TodoDAO todoDAO, TodoAPIService apiService) {
        repository = new TodoRepository(todoDAO, apiService);
    }

    @SuppressLint("CheckResult")
    public void loadTodoList() {
        repository.loadTodoList()
            .switchIfEmpty(emptyResource())
            .subscribe(this::postResult);
    }

    @SuppressLint("CheckResult")
    public void deleteTodo(Long todoId) {
        repository.deleteTodo(todoId)
            .switchIfEmpty(emptyResource())
            .subscribe(this::postResult);
    }

    @SuppressLint("CheckResult")
    public void createTodo(String title) {
        repository.createTodo(title)
            .subscribe(resource -> {
                postResult(resource);
            });
    }

    private Observable<Resource<List<TodoEntity>>> emptyResource() {
        postResult(Resource.error("No data", null));
        return Observable.empty();
    }

    private void postResult(@Nullable Resource<List<TodoEntity>> resource) {
        todoListLiveData.postValue(resource);
        if (resource.data != null && !resource.data.isEmpty()) {
            emptyVisibility.set(View.GONE);
            listVisibility.set(View.VISIBLE);
        } else {
            emptyVisibility.set(View.VISIBLE);
            listVisibility.set(View.GONE);
        }
    }

    public ObservableInt getEmptyVisibility() {
        return emptyVisibility;
    }

    public ObservableInt getListVisibility() {
        return listVisibility;
    }

    public MutableLiveData<Resource<List<TodoEntity>>> getTodoListLiveData() {
        return todoListLiveData;
    }
}