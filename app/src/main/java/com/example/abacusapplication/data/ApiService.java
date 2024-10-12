package com.example.abacusapplication.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import com.example.abacusapplication.models.Admin;
import com.example.abacusapplication.models.ApiResponse;
import com.example.abacusapplication.models.CreateExam;
import com.example.abacusapplication.models.Login;
import com.example.abacusapplication.models.LoginRequest;
import com.example.abacusapplication.models.LoginResponse;
import com.example.abacusapplication.models.LoginData;
import com.example.abacusapplication.models.Student;

public interface ApiService {
    @POST("student/login")
    Call<LoginResponse> studentLogin(@Body LoginRequest req);
    @POST("admin/login")
    Call<LoginResponse> adminLogin(@Body LoginRequest req);
    @GET("echo")
    Call<Echoreponse> echo();

    @POST("student/register")
    Call<ApiResponse<String>>studentRegister(@Body Student student);

    @GET("admin/me")
    Call<ApiResponse<Admin>> getAdmin();

    @POST("admin/exam")
    Call<ApiResponse<String>> createExam(@Body CreateExam exam);
    class Echoreponse{
       public boolean echoed;
    }
}
