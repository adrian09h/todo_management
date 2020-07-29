package com.nuasolutions.todomanagement.ui.fragment;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nuasolutions.todomanagement.R;
import com.nuasolutions.todomanagement.data.Resource;
import com.nuasolutions.todomanagement.data.Status;
import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;
import com.nuasolutions.todomanagement.data.local.entity.TodoItemEntity;
import com.nuasolutions.todomanagement.databinding.FragmentTodoDetailBinding;
import com.nuasolutions.todomanagement.interfaces.OnTodoTaskItemClickListener;
import com.nuasolutions.todomanagement.ui.adapter.TodoTasklListAdapter;
import com.nuasolutions.todomanagement.utils.ContextUtils;
import com.nuasolutions.todomanagement.viewmodel.TodoDetailViewModel;
import com.nuasolutions.todomanagement.viewmodel.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class TodoDetailFragment extends BaseFragment implements OnTodoTaskItemClickListener {
    @Inject
    ViewModelFactory viewModelFactory;
    private TodoDetailViewModel mViewModel;
    private FragmentTodoDetailBinding mBinding;
    private TodoEntity mTodoEntity;
    private TodoTasklListAdapter mAdapter;

    private AlertDialog mAlertDialogAddNew;
    private EditText mEditTodoTask;
    private AlertDialog mAlertDialogConfirmDelete;
    private int pickedItemPosition = 0;
    private Long pickedItemId;
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
        mViewModel.getTodoLiveData().observe(this, resource -> {
            if (resource.isLoading()) {
            } else {
                hideLoader();
                if (resource.data != null) {
                    if (resource.status != Status.SUCCESS) {
                        //failed to get a response from server, but from DB
                    } else {
                        //successfully got a response from server
                    }
                    mTodoEntity = resource.data;
                    mAdapter.setTodoTaskList(resource.data.getItemList());
                } else {
                    mAdapter.setTodoTaskList(new ArrayList<>());
                    handleErrorResponse(resource);
                }
            }
        });
        mViewModel.initTodoLiveData(mTodoEntity);
    }

    private void handleErrorResponse(Resource<TodoEntity> resource) {
        String error = resource.message;
        showErrorSnack(error);
    }

    private void initViews() {
        activity.setTitle(getString(R.string.todos_detail_title));
        mBinding.setTodoEntity(mTodoEntity);
        mBinding.setEmptyVisiblity(mViewModel.getEmptyVisiblity());
        mBinding.setListVisiblity(mViewModel.getListVisibility());

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new TodoTasklListAdapter(this);
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.fab.setOnClickListener( view -> onAddNewClicked());

    }

    private void onAddNewClicked() {
        if (mAlertDialogAddNew == null) {
            mEditTodoTask = new EditText(getContext());
            mAlertDialogAddNew = new AlertDialog.Builder(getContext())
                .setTitle(R.string.add_newtask_dlg_title)
                .setMessage(R.string.add_newtask_dlg_message)
                .setView(mEditTodoTask)
                .setPositiveButton(R.string.add, (dialogInterface, i) -> {
                    if (!TextUtils.isEmpty(mEditTodoTask.getText().toString())) {
                        createTodoItem(mEditTodoTask.getText().toString());
                        mAlertDialogAddNew.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                    mAlertDialogAddNew.dismiss();
                })
                .create();
        }

        mAlertDialogAddNew.show();
    }

    // ==== api calls ===
    private void createTodoItem(String newTaskName) {
        displayLoader();
        mViewModel.createTodoItem(mTodoEntity.getId(), newTaskName, false);
    }

    private void deleteTodoItem(int position, Long itemId) {
        displayLoader();
        mTodoEntity.getItemList().remove(position);
        mViewModel.deleteTodoItem(mTodoEntity, itemId);
    }
    //----------------

    // ======= Task Item ClickListener ====
    @Override
    public void onItemClicked(TodoItemEntity todoItemEntity) {

    }

    @Override
    public void onItemLongClicked(TodoItemEntity todoItemEntity) {
        for (int i=0; i < mTodoEntity.getItemList().size(); i++) {
            if (mTodoEntity.getItemList().get(i).getId().equals(todoItemEntity.getId())) {
                pickedItemPosition =  i;
                break;
            }
        }
        pickedItemId = todoItemEntity.getId();
        ContextUtils.vibrate(activity);
        if (mAlertDialogConfirmDelete == null) {
            mAlertDialogConfirmDelete = new AlertDialog.Builder(getContext())
                .setTitle(R.string.delete_confirm_dlg_title)
                .setMessage(R.string.delete_confirm_dlg_message)
                .setPositiveButton(R.string.delete, (dialogInterface, i) -> {
                    deleteTodoItem(pickedItemPosition, pickedItemId);
                    mAlertDialogConfirmDelete.dismiss();
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                    mAlertDialogConfirmDelete.dismiss();
                })
                .create();
        }

        mAlertDialogConfirmDelete.show();
    }
    //-----------------------
}