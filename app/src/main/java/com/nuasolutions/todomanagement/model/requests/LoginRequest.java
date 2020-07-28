package com.nuasolutions.todomanagement.model.requests;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;

    LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
