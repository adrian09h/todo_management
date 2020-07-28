package com.nuasolutions.todomanagement.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;
import com.nuasolutions.todomanagement.databinding.TodoListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoItemViewHolder> {

    private List<TodoEntity> todoList = new ArrayList<>();

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
        holder.bindTo(todoList.get(position));
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void setTodoList(List<TodoEntity> newTodoList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
            new TodoDiffCallback(this.todoList, newTodoList)
        );
        this.todoList.clear();
        this.todoList.addAll(newTodoList);
        diffResult.dispatchUpdatesTo(this);
    }

    protected class TodoItemViewHolder extends RecyclerView.ViewHolder {
        private TodoListItemBinding binding;
        public TodoItemViewHolder(TodoListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindTo(TodoEntity todoEntity) {
            binding.setTodoEntity(todoEntity);
        }
    }
}
