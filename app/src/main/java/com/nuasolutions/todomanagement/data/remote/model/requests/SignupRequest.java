package com.nuasolutions.todomanagement.data.remote.model.requests;

import com.google.gson.annotations.SerializedName;

public class SignupRequest {
    @SerializedName("name")
    public String name;

    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;

    @SerializedName("password_confirmation")
    public String passwordConfirmation;

    public SignupRequest(String name, String email, String password, String pwdConfirmation) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = pwdConfirmation;
    }
}
