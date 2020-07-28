package com.nuasolutions.todomanagement.data.remote.model.response;
import com.google.gson.annotations.SerializedName;

public class AccessTokenResponse{
    @SerializedName("auth_token")
    public String accessToken;

    public AccessTokenResponse(String token) {
        this.accessToken = token;
    }
}
