package com.nuasolutions.todomanagement.ui.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuasolutions.todomanagement.R;
import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;
import com.nuasolutions.todomanagement.data.local.entity.TodoItemEntity;
import com.nuasolutions.todomanagement.databinding.FragmentTodoDetailBinding;
import com.nuasolutions.todomanagement.interfaces.OnTodoTaskItemClickListener;
import com.nuasolutions.todomanagement.ui.adapter.TodoTasklListAdapter;
import com.nuasolutions.todomanagement.viewmodel.TodoDetailViewModel;
import com.nuasolutions.todomanagement.viewmodel.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class TodoDetailFragment extends BaseFragment implements OnTodoTaskItemClickListener {
    @Inject
    ViewModelFactory viewModelFactory;
    private TodoDetailViewModel mViewModel;
    private FragmentTodoDetailBinding mBinding;
    private TodoEntity mTodoEntity;
    private TodoTasklListAdapter mAdapter;

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
        if (mTodoEntity.getItemList().isEmpty()) {
            mViewModel.setmEmptyVisiblity(View.VISIBLE);
            mViewModel.setmListVisibility(View.GONE);
        } else {
            mViewModel.setmEmptyVisiblity(View.GONE);
            mViewModel.setmListVisibility(View.VISIBLE);
        }
    }

    private void initViews() {
        activity.setTitle(getString(R.string.todos_detail_title));
        mBinding.setTodoEntity(mTodoEntity);
        mBinding.setEmptyVisiblity(mViewModel.getmEmptyVisiblity());
        mBinding.setListVisiblity(mViewModel.getmListVisibility());

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new TodoTasklListAdapter(this);
        mBinding.recyclerView.setAdapter(mAdapter);
        mAdapter.setTodoTaskList(mTodoEntity.getItemList());
    }

    // ======= Task Item ClickListener ====
    @Override
    public void onItemClicked(TodoItemEntity todoItemEntity) {

    }

    @Override
    public void onItemLongClicked(TodoItemEntity todoItemEntity) {

    }
    //-----------------------
}