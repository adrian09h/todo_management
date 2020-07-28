package com.nuasolutions.todomanagement.ui.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuasolutions.todomanagement.R;
import com.nuasolutions.todomanagement.viewmodel.TodoListViewModel;

public class TodoListFragment extends BaseFragment {

    private TodoListViewModel mViewModel;

    public static TodoListFragment newInstance() {
        return new TodoListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todo_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TodoListViewModel.class);
        // TODO: Use the ViewModel
    }

}