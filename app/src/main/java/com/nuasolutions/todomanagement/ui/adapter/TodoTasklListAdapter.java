package com.nuasolutions.todomanagement.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nuasolutions.todomanagement.data.local.entity.TodoItemEntity;
import com.nuasolutions.todomanagement.databinding.TodoTaskListItemBinding;
import com.nuasolutions.todomanagement.interfaces.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

public class TodoTasklListAdapter extends RecyclerView.Adapter<TodoTasklListAdapter.TodoTaskItemViewHolder> {

    private List<TodoItemEntity> mTodoTaskList = new ArrayList<>();
    private OnItemLongClickListener mListener;

    public TodoTasklListAdapter(OnItemLongClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public TodoTaskItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TodoTaskListItemBinding itemBinding = TodoTaskListItemBinding.inflate(inflater, parent, false);
        TodoTaskItemViewHolder itemViewHolder = new TodoTaskItemViewHolder(itemBinding);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoTaskItemViewHolder holder, int position) {
        holder.bindTo(mTodoTaskList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTodoTaskList.size();
    }

    public void setTodoTaskList(List<TodoItemEntity> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
            new TodoTaskDiffCallback(this.mTodoTaskList, newList)
        );
        this.mTodoTaskList.clear();
        this.mTodoTaskList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    protected class TodoTaskItemViewHolder extends RecyclerView.ViewHolder {
        private TodoTaskListItemBinding mBinding;
        private TodoItemEntity mTodoItemEntity;
        public TodoTaskItemViewHolder(TodoTaskListItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
            this.mBinding.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mListener.onItemLongClicked(mTodoItemEntity.getId());
                    return false;
                }
            });
        }
        public void bindTo(TodoItemEntity todoItemEntity) {
            this.mTodoItemEntity = todoItemEntity;
            mBinding.setTodoItemEntity(todoItemEntity);
        }
    }
}
