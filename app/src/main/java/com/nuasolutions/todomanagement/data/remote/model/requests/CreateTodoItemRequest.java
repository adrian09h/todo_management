package com.nuasolutions.todomanagement.data.remote.model.requests;

import com.google.gson.annotations.SerializedName;

public class CreateTodoItemRequest {
    @SerializedName("name")
    public String name;

    @SerializedName("done")
    public Boolean done;

    public CreateTodoItemRequest(String name, boolean done) {
        this.name = name;
        this.done = done;
    }
}
