package com.nuasolutions.todomanagement.ui.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;
import com.nuasolutions.todomanagement.data.local.entity.TodoItemEntity;

import java.util.List;

public class TodoTaskDiffCallback extends DiffUtil.Callback {
    private final List<TodoItemEntity> mOldTodoList;
    private final List<TodoItemEntity> mNewTodoList;

    public TodoTaskDiffCallback(List<TodoItemEntity> oldList, List<TodoItemEntity> newList) {
        this.mOldTodoList = oldList;
        this.mNewTodoList = newList;
    }

    @Override
    public int getOldListSize() {
        return mOldTodoList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewTodoList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        TodoItemEntity oldItem = mOldTodoList.get(oldItemPosition);
        TodoItemEntity newItem = mNewTodoList.get(newItemPosition);
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        TodoItemEntity oldItem = mOldTodoList.get(oldItemPosition);
        TodoItemEntity newItem = mNewTodoList.get(newItemPosition);
        return oldItem.equals(newItem);
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
