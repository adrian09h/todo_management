package com.nuasolutions.todomanagement.ui.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;

import java.util.List;

public class TodoDiffCallback extends DiffUtil.Callback {
    private final List<TodoEntity> mOldTodoList;
    private final List<TodoEntity> mNewTodoList;

    public TodoDiffCallback(List<TodoEntity> oldList, List<TodoEntity> newList) {
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
        TodoEntity oldItem = mOldTodoList.get(oldItemPosition);
        TodoEntity newItem = mNewTodoList.get(newItemPosition);
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        TodoEntity oldItem = mOldTodoList.get(oldItemPosition);
        TodoEntity newItem = mNewTodoList.get(newItemPosition);
        if (!oldItem.getTitle().equals(newItem.getTitle())) {
            return false;
        }
        //Don't need to check other field, because only title is showing to users in RecyclerView
        return true;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
