package com.example.abacusapplication.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import com.example.abacusapplication.models.Login;
import com.example.abacusapplication.models.LoginRequest;
import com.example.abacusapplication.models.LoginResponse;

public interface ApiService {
    @POST("student/login")
    Call<LoginResponse> studentLogin(@Body LoginRequest req);
}
