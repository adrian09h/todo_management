package com.nuasolutions.todomanagement.viewmodel;

import androidx.lifecycle.ViewModel;

import com.nuasolutions.todomanagement.data.local.dao.TodoDAO;
import com.nuasolutions.todomanagement.data.remote.api.TodoAPIService;
import com.nuasolutions.todomanagement.data.repository.TodoRepository;

import javax.inject.Inject;

public class TodoDetailViewModel extends BaseViewModel {
    private TodoRepository repository;
    @Inject
    public TodoDetailViewModel(TodoDAO todoDAO, TodoAPIService apiService) {
        repository = new TodoRepository(todoDAO, apiService);
    }
}