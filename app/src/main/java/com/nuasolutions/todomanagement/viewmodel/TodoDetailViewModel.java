package com.nuasolutions.todomanagement.viewmodel;

import android.view.View;

import androidx.databinding.ObservableInt;

import com.nuasolutions.todomanagement.data.local.dao.TodoDAO;
import com.nuasolutions.todomanagement.data.remote.api.TodoAPIService;
import com.nuasolutions.todomanagement.data.repository.TodoRepository;

import javax.inject.Inject;

public class TodoDetailViewModel extends BaseViewModel {
    private TodoRepository mRepository;
    private ObservableInt mEmptyVisiblity = new ObservableInt(View.GONE);
    private ObservableInt mListVisibility = new ObservableInt(View.GONE);

    @Inject
    public TodoDetailViewModel(TodoDAO todoDAO, TodoAPIService apiService) {
        mRepository = new TodoRepository(todoDAO, apiService);
    }

    public ObservableInt getmEmptyVisiblity() {
        return mEmptyVisiblity;
    }

    public ObservableInt getmListVisibility() {
        return mListVisibility;
    }

    public void setmEmptyVisiblity(int visiblity) {
        this.mEmptyVisiblity.set(visiblity);
    }

    public void setmListVisibility(int visibility) {
        this.mListVisibility.set(visibility);
    }
}