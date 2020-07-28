package com.nuasolutions.todomanagement.ui.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;
import com.nuasolutions.todomanagement.databinding.FragmentTodoDetailBinding;
import com.nuasolutions.todomanagement.viewmodel.TodoDetailViewModel;
import com.nuasolutions.todomanagement.viewmodel.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class TodoDetailFragment extends Fragment {
    @Inject
    ViewModelFactory viewModelFactory;
    private TodoDetailViewModel mViewModel;
    private FragmentTodoDetailBinding mBinding;
    private TodoEntity mTodoEntity;

    public static TodoDetailFragment newInstance() {
        return new TodoDetailFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mTodoEntity = TodoDetailFragmentArgs.fromBundle(getArguments()).getTodoData();
        mBinding = FragmentTodoDetailBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModel();
        initViews();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(TodoDetailViewModel.class);
    }

    private void initViews() {
        mBinding.setTodoEntity(mTodoEntity);
    }

}