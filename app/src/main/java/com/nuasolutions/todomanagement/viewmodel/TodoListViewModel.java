package com.nuasolutions.todomanagement.viewmodel;

import android.view.View;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nuasolutions.todomanagement.data.Resource;
import com.nuasolutions.todomanagement.data.local.dao.TodoDAO;
import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;
import com.nuasolutions.todomanagement.data.remote.api.TodoAPIService;
import com.nuasolutions.todomanagement.data.repository.TodoRepository;

import java.util.List;

import javax.inject.Inject;

public class TodoListViewModel extends BaseViewModel {
    private ObservableInt emptyVisibility = new ObservableInt(View.GONE);
    private ObservableInt listVisibility = new ObservableInt(View.VISIBLE);
    private TodoRepository repository;
    private MutableLiveData<Resource<List<TodoEntity>>> todoListLiveData = new MutableLiveData<>();

    @Inject
    public TodoListViewModel(TodoDAO todoDAO, TodoAPIService apiService) {
        /* You can see we are initialising the MovieRepository class here */
        repository = new TodoRepository(todoDAO, apiService);
    }

    public void loadTodoList() {
        repository.loadTodoList()
            .subscribe(resource -> todoListLiveData.postValue(resource));
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