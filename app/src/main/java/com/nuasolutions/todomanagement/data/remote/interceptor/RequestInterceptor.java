package com.nuasolutions.todomanagement.data.remote.interceptor;

import android.text.TextUtils;

import com.nuasolutions.todomanagement.ui.fragment.BaseFragment;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalUrl = originalRequest.url();
        HttpUrl url = originalUrl.newBuilder()
//                .addQueryParameter("api_key", AppConstants.APIKEY)
                .build();
        Request.Builder requestBuilder = originalRequest.newBuilder().url(url);
        if (!TextUtils.isEmpty(BaseFragment.tempToken)) {
            String headerKey2 = "Authorization";
            String headerValue2 = "Bearer " + BaseFragment.tempToken;
            requestBuilder.addHeader(headerKey2, headerValue2);
        }
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
