package com.nuasolutions.todomanagement.interfaces;

import com.nuasolutions.todomanagement.data.local.entity.TodoEntity;

public interface OnTodoItemClickListener {
    void onItemClicked(TodoEntity todoEntity);
}
