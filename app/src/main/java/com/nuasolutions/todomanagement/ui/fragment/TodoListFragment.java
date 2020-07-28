package com.nuasolutions.todomanagement.ui.fragment;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nuasolutions.todomanagement.R;
import com.nuasolutions.todomanagement.data.Resource;
import com.nuasolutions.todomanagement.data.Status;
import com.nuasolutions.todomanagement.data.local.entity.AccessTokenEntity;
import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;
import com.nuasolutions.todomanagement.databinding.FragmentTodoListBinding;
import com.nuasolutions.todomanagement.viewmodel.TodoListViewModel;
import com.nuasolutions.todomanagement.viewmodel.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;
import dagger.android.support.AndroidSupportInjection;

public class TodoListFragment extends BaseFragment {
    @Inject
    ViewModelFactory viewModelFactory;

    private TodoListViewModel mViewModel;
    private FragmentTodoListBinding binding;

    public static TodoListFragment newInstance() {
        return new TodoListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTodoListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity.setTitle(getString(R.string.todos_frag_title));
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(TodoListViewModel.class);
        binding.setEmptyVisiblity(mViewModel.getEmptyVisibility());
        binding.setListVisiblity(mViewModel.getListVisibility());
        mViewModel.getTodoListLiveData().observe(this, resource -> {
            if (resource.isLoading()) {
                //TODO: show loading
            } else {
                hideLoader();
                if (!resource.data.isEmpty()) {
                    Log.d(TodoListFragment.class.getSimpleName(), "recevied todoList:" + resource.data.toString());
                    if (resource.status != Status.SUCCESS) {
                        //failed to get a response from server, but from DB
                    } else {
                        //successfully got a response from server
                    }
                    
                } else {
                    handleErrorResponse(resource);
                }
            }
        });
        mViewModel.loadTodoList();
    }

    private void handleErrorResponse(Resource<List<TodoEntity>> resource) {
        String error = resource.message;
        showErrorSnack(error);
    }

}