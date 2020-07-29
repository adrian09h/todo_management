package com.nuasolutions.todomanagement.viewmodel;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;

import com.nuasolutions.todomanagement.data.Resource;
import com.nuasolutions.todomanagement.data.local.dao.TodoDAO;
import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;
import com.nuasolutions.todomanagement.data.remote.api.TodoAPIService;
import com.nuasolutions.todomanagement.data.repository.TodoRepository;

import java.util.List;

import javax.inject.Inject;

public class TodoDetailViewModel extends BaseViewModel {
    private TodoRepository mRepository;
    private ObservableInt mEmptyVisiblity = new ObservableInt(View.GONE);
    private ObservableInt mListVisibility = new ObservableInt(View.GONE);

    private MutableLiveData<Resource<TodoEntity>> todoLiveData = new MutableLiveData<>();

    @Inject
    public TodoDetailViewModel(TodoDAO todoDAO, TodoAPIService apiService) {
        mRepository = new TodoRepository(todoDAO, apiService);
    }

    @SuppressLint("CheckResult")
    public void createTodoItem(Long todoId, String name, boolean done) {
        mRepository.createTodoItem(todoId, name, done)
            .subscribe(this::postResult);
    }

    public void initTodoLiveData(TodoEntity todoEntity) {
        postResult(Resource.success(todoEntity));
    }

    private void postResult(@Nullable Resource<TodoEntity> resource) {
        todoLiveData.postValue(resource);
        if (resource.data != null) {
            mEmptyVisiblity.set(View.GONE);
            mListVisibility.set(View.VISIBLE);
        } else {
            mEmptyVisiblity.set(View.VISIBLE);
            mListVisibility.set(View.GONE);
        }
    }

    public ObservableInt getEmptyVisiblity() {
        return mEmptyVisiblity;
    }

    public ObservableInt getListVisibility() {
        return mListVisibility;
    }

    public MutableLiveData<Resource<TodoEntity>> getTodoLiveData() {
        return todoLiveData;
    }


}