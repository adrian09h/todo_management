package com.nuasolutions.todomanagement.data.remote.api;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.nuasolutions.todomanagement.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private static final long READ_TIME_OUT = 15;
    private static final long CONNECTION_TIME_OUT = 15;
    private static OnboardingAPIService noAuthService;
    private static OnboardingAPIService authService;
    public static OnboardingAPIService getAPIService(@Nullable String token) {
        if (TextUtils.isEmpty(token)) {
            if (noAuthService == null) {
                noAuthService = createRetrofitClient().create(OnboardingAPIService.class);
            }
            return noAuthService;
        } else {
            if (authService == null) {
                authService = createAuthRetrofitClient(token).create(OnboardingAPIService.class);
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
            .baseUrl(BuildConfig.apiBaseURL)
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
            .baseUrl(BuildConfig.apiBaseURL)
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
