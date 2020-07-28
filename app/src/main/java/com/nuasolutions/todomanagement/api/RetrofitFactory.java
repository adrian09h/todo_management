package com.nuasolutions.todomanagement.api;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private static final long READ_TIME_OUT = 15;
    private static final long CONNECTION_TIME_OUT = 15;
    private static APIService noAuthService;
    private static APIService authService;
    public static APIService getAPIService(@Nullable String token) {
        if (TextUtils.isEmpty(token)) {
            if (noAuthService == null) {
                noAuthService = createRetrofitClient().create(APIService.class);
            }
            return noAuthService;
        } else {
            if (authService == null) {
                authService = createAuthRetrofitClient(token).create(APIService.class);
            }
            return authService;
        }
    }

    public static Retrofit createRetrofitClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(noAuthInterceptor())
            .build();
        return new Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();
    }

    public static Retrofit createAuthRetrofitClient(String token) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authHeaderInterceptor(token))
            .build();
        return new Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();
    }

    private static Interceptor noAuthInterceptor() {
        return chain -> {
            String headerKey = "Content-Type";
            String headerValue = "application/json";
            return chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader(headerKey, headerValue)
                    .build()
            );
        };
    }

    private static Interceptor authHeaderInterceptor(String token) {
        return chain -> {
            String headerKey = "Content-Type";
            String headerValue = "application/json";
            String headerKey2 = "Authorization";
            String headerValue2 = token;
            return chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader(headerKey, headerValue)
                    .addHeader(headerKey2, headerValue2)
                    .build()
            );
        };
    }


}
