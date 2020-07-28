package com.nuasolutions.todomanagement.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;
import com.nuasolutions.todomanagement.databinding.TodoListItemBinding;
import com.nuasolutions.todomanagement.interfaces.OnTodoItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoItemViewHolder> {

    private List<TodoEntity> mTodoList = new ArrayList<>();
    private OnTodoItemClickListener mListener;

    public TodoListAdapter(OnTodoItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public TodoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TodoListItemBinding itemBinding = TodoListItemBinding.inflate(inflater, parent, false);
        TodoItemViewHolder itemViewHolder = new TodoItemViewHolder(itemBinding);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoItemViewHolder holder, int position) {
        holder.bindTo(mTodoList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTodoList.size();
    }

    public void setTodoList(List<TodoEntity> newTodoList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
            new TodoDiffCallback(this.mTodoList, newTodoList)
        );
        this.mTodoList.clear();
        this.mTodoList.addAll(newTodoList);
        diffResult.dispatchUpdatesTo(this);
    }

    protected class TodoItemViewHolder extends RecyclerView.ViewHolder {
        private TodoListItemBinding mBinding;
        private TodoEntity mTodoEntity;
        public TodoItemViewHolder(TodoListItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
            this.mBinding.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TodoListAdapter.this.mListener.onItemClicked(mTodoEntity);
                }
            });
        }
        public void bindTo(TodoEntity todoEntity) {
            this.mTodoEntity = todoEntity;
            mBinding.setTodoEntity(todoEntity);
        }
    }
}
