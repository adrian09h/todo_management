package com.nuasolutions.todomanagement.api;

import com.nuasolutions.todomanagement.model.AccessToken;
import com.nuasolutions.todomanagement.model.requests.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @POST("/auth/login")
    Call<AccessToken> doLogin(@Body LoginRequest loginRequest);

}
