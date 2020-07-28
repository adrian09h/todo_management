package com.nuasolutions.todomanagement.data.remote.api;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

import com.nuasolutions.todomanagement.data.remote.model.requests.LoginRequest;
import com.nuasolutions.todomanagement.data.remote.model.response.AccessTokenResponse;

public interface OnboardingAPIService {
    @POST("/auth/login")
    Observable<AccessTokenResponse> doLogin(@Body LoginRequest loginRequest);

}
