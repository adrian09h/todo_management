package com.nuasolutions.todomanagement.data.remote.model.requests;

import com.google.gson.annotations.SerializedName;

public class CreateTodoRequest {
    @SerializedName("title")
    public String title;

    public CreateTodoRequest(String title) {
        this.title = title;
    }
}
