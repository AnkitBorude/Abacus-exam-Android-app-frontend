package com.example.abacusapplication.data;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.example.abacusapplication.models.Admin;
import com.example.abacusapplication.models.ApiResponse;
import com.example.abacusapplication.models.CreateExam;
import com.example.abacusapplication.models.Exam;
import com.example.abacusapplication.models.ExamResult;
import com.example.abacusapplication.models.Login;
import com.example.abacusapplication.models.LoginRequest;
import com.example.abacusapplication.models.LoginResponse;
import com.example.abacusapplication.models.LoginData;
import com.example.abacusapplication.models.Question;
import com.example.abacusapplication.models.Result;
import com.example.abacusapplication.models.Student;
import com.example.abacusapplication.models.StudentresultInfo;

import java.util.List;

public interface ApiService {
    @POST("student/login")
    Call<LoginResponse> studentLogin(@Body LoginRequest req);
    @POST("admin/login")
    Call<LoginResponse> adminLogin(@Body LoginRequest req);
    @GET("echo")
    Call<Echoreponse> echo();

    @POST("student/register")
    Call<ApiResponse<String>>studentRegister(@Body Student student);

    @POST("admin/register")
    Call<ApiResponse<String>>adminRegister(@Body Admin admin);

    @GET("admin/me")
    Call<ApiResponse<Admin>> getAdmin();

    @POST("exam")
    Call<ApiResponse<String>> createExam(@Body CreateExam exam);
    @GET("exam")
    Call<ApiResponse<List<Exam>>> getExams();

    @GET("exam/{examId}/questions")
    Call<ApiResponse<List<Question>>> getQuestions(@Path("examId") String examId);

    @POST("result")
    Call<ApiResponse<String>> createResult(@Body Result result);

    @POST("exam/{examId}/activate")
    Call<ApiResponse<String>> activateExam(@Path("examId") String examId);
    @POST("exam/{examId}/deactivate")
    Call<ApiResponse<String>> deactivateExam(@Path("examId") String examId);

    @GET("exam/{examId}/results")
    Call<ApiResponse<List<ExamResult>>> getStudentResult(@Path("examId") String examId);

    @GET("exam/{examId}/students/{studentId}/results")
    Call<ApiResponse<List<ExamResult>>> getStudentResult(@Path("examId") String examId,@Path("studentId") String studentId);
    @GET("exam/{examId}/students")
    Call<ApiResponse<List<StudentresultInfo>>> getStudentsofExam(@Path("examId") String examId);

    @GET("result/{resultId}/pdf")
    Call<ResponseBody> getStudentresultPdf(@Path("resultId") String resultId);

    @GET("student")
    Call<ApiResponse<List<StudentresultInfo>>> getStudents(@Query("name") String queryName, @Query("class") String studentClass,@Query("level") String studentLevel);

    @DELETE("student/{studentId}")
    Call<ApiResponse<String>> deleteStudent(@Path("studentId") String studentId);

    @DELETE("student/{studentId}/clear")
    Call<ApiResponse<String>> deleteFullStudent(@Path ("studentId") String studentId);

    @DELETE ("exam/{examId}")
    Call<ApiResponse<String>> deleteExam(@Path("examId") String examId);

    @DELETE ("exam/{examId}/results")
    Call<ApiResponse<String>> deleteResults(@Path("examId") String examId);

    @DELETE("result/{resultId}")
    Call<ApiResponse<String>> deleteResult(@Path("resultId") String resultId);
    class Echoreponse{
       public boolean echoed;
    }
}
