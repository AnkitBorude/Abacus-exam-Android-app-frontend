package com.example.abacusapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.abacusapplication.data.ApiService;
import com.example.abacusapplication.data.RetrofitClient;
import com.example.abacusapplication.models.ApiError;
import com.example.abacusapplication.models.ApiResponse;
import com.example.abacusapplication.models.Exam;
import com.example.abacusapplication.models.StudentresultInfo;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAllStudentExam extends AppCompatActivity {
    private LinearLayout linearLayoutStudents;
    private List<StudentresultInfo> studentList;
    private CircularProgressIndicator progressIndicator;
    private String examId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_student_exam);

        Intent intent=getIntent();
        this.examId=intent.getStringExtra("examId");
        linearLayoutStudents = findViewById(R.id.linearLayoutExams);

        progressIndicator = findViewById(R.id.progress_circular);
        progressIndicator.setVisibility(View.VISIBLE);
        RetrofitClient client=RetrofitClient.getInstance();
        ApiService apiService = client.getApi();
        Call<ApiResponse<List<StudentresultInfo>>> call=apiService.getStudentsofExam(this.examId);
        call.enqueue(new Callback<ApiResponse<List<StudentresultInfo>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<StudentresultInfo>>> call, Response<ApiResponse<List<StudentresultInfo>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<StudentresultInfo>> response1 = response.body();
                    createExamUI(response1.getData());
                } else {
                    ApiError error = client.convertError(response.errorBody());
                    Toast.makeText(getBaseContext(),error.getError(),Toast.LENGTH_LONG).show();
                    progressIndicator.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<StudentresultInfo>>> call, Throwable t) {
                Toast.makeText(getBaseContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                progressIndicator.setVisibility(View.GONE);
            }
        });
    }
    private void createExamUI(List<StudentresultInfo> students) {
        LayoutInflater inflater = LayoutInflater.from(this);
        for (StudentresultInfo student : students) {
            View studentCard = inflater.inflate(R.layout.item_student_exam_info_card, linearLayoutStudents, false);

            // Set student details
            TextView textViewStudentName = studentCard.findViewById(R.id.textViewStudentName);
            TextView textViewStudentEmail = studentCard.findViewById(R.id.textViewStudentEmail);
            TextView textViewStudentLevel = studentCard.findViewById(R.id.textViewStudentLevel);
            TextView textViewStudentClass = studentCard.findViewById(R.id.textViewStudentClass);
            TextView textViewStudentPhone = studentCard.findViewById(R.id.textViewStudentPhone);

            Button buttonAttend = studentCard.findViewById(R.id.buttonAttend);

            textViewStudentName.setText(student.getStudentName());
            textViewStudentEmail.setText("Email: " + student.getStudentEmail());
            textViewStudentLevel.setText("Level: " + student.getStudentLevel());
            textViewStudentClass.setText("Class: " + student.getStudentClass());
            textViewStudentPhone.setText("Phone: " + student.getStudentPhoneNo());

            buttonAttend.setOnClickListener(v -> {
                // Add any click listener logic here if needed
            });

            // Add the card to the LinearLayout
            linearLayoutStudents.addView(studentCard);
            progressIndicator.setVisibility(View.GONE);
        }
    }
}