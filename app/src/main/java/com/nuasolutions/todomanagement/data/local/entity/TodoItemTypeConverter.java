package com.nuasolutions.todomanagement.data.local.entity;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TodoItemTypeConverter {
    @TypeConverter
    public static List<TodoItemEntity> stringToList(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<TodoItemEntity>>() {}.getType();
        List<TodoItemEntity> items = gson.fromJson(json, type);
        return items;
    }

    @TypeConverter
    public static String listToString(List<TodoItemEntity> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<TodoItemEntity>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }
}