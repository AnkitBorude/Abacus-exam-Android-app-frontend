package com.example.abacusapplication.data;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.Request;
public class AuthInterceptor implements Interceptor {
    private String token=null;
    TokenManager manager;
    AuthInterceptor(Context context)
    {
        manager=TokenManager.getInstance(context);
    }
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        return chain.proceed(originalRequest.newBuilder().header("Authorization","Bearer "+manager.getToken()).build());
    }
}
