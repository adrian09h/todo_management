package com.nuasolutions.todomanagement.ui.fragment;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.nuasolutions.todomanagement.ui.adapter.TodoListAdapter;
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
    private TodoListAdapter todoListAdapter;

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
        initViewModel();
        initViews();
        displayLoader();
        mViewModel.loadTodoList();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(TodoListViewModel.class);
        mViewModel.getTodoListLiveData().observe(this, resource -> {
            if (resource.isLoading()) {
            } else {
                hideLoader();
                if (!resource.data.isEmpty()) {
                    Log.d(TodoListFragment.class.getSimpleName(), "recevied todoList:" + resource.data.toString());
                    if (resource.status != Status.SUCCESS) {
                        //failed to get a response from server, but from DB
                    } else {
                        //successfully got a response from server
                    }
                    todoListAdapter.setTodoList(resource.data);
                } else {
                    handleErrorResponse(resource);
                }
            }
        });
    }

    private void initViews() {
        activity.setTitle(getString(R.string.todos_frag_title));
        binding.setEmptyVisiblity(mViewModel.getEmptyVisibility());
        binding.setListVisiblity(mViewModel.getListVisibility());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        todoListAdapter = new TodoListAdapter();
        binding.recyclerView.setAdapter(todoListAdapter);
    }

    private void handleErrorResponse(Resource<List<TodoEntity>> resource) {
        String error = resource.message;
        showErrorSnack(error);
    }

}