package com.nuasolutions.todomanagement.interfaces;

import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;
import com.nuasolutions.todomanagement.data.local.entity.TodoItemEntity;

public interface OnTodoTaskItemClickListener {
    void onItemClicked(TodoItemEntity todoItemEntity);
    void onItemLongClicked(TodoItemEntity todoItemEntity);
}
