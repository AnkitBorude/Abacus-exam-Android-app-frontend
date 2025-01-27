package com.example.abacusapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.abacusapplication.services.ApiEndpointsService;
import com.example.abacusapplication.services.RetrofitClientFactoryService;
import com.example.abacusapplication.models.ApiError;
import com.example.abacusapplication.models.ApiResponse;
import com.example.abacusapplication.models.Exam;
import com.example.abacusapplication.models.ExamResult;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAllResult extends AppCompatActivity {
    private LinearLayout linearLayoutExamResults;
    private List<Exam> examList;
    private CircularProgressIndicator progressIndicator;
    private String examId;
    private String studentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_result);
        linearLayoutExamResults = findViewById(R.id.linearLayoutAllResult);
        Intent intent = getIntent();
        this.examId=intent.getStringExtra("examId");
        this.studentId=intent.getStringExtra("studentId");
        progressIndicator = findViewById(R.id.progress_circular);
        progressIndicator.setVisibility(View.VISIBLE);
        RetrofitClientFactoryService client= RetrofitClientFactoryService.getInstance();
        ApiEndpointsService apiEndpointsService = client.getApi();
        Call<ApiResponse<List<ExamResult>>> call= apiEndpointsService.getStudentResult(this.examId,this.studentId);
        call.enqueue(new Callback<ApiResponse<List<ExamResult>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ExamResult>>> call, Response<ApiResponse<List<ExamResult>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<ExamResult>> response1 = response.body();
                    createExamUI(response1.getData());
                } else {
                    ApiError error = client.convertError(response.errorBody());
                    Toast.makeText(getBaseContext(),error.getError(),Toast.LENGTH_LONG).show();
                    progressIndicator.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<ExamResult>>> call, Throwable t) {
                Toast.makeText(getBaseContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                progressIndicator.setVisibility(View.GONE);
            }
        });

    }private void createExamUI(List<ExamResult> examResults) {
        LayoutInflater inflater = LayoutInflater.from(this);
        int i=1;
        for (ExamResult result : examResults) {
            View examResultCard = inflater.inflate(R.layout.item_result_card, linearLayoutExamResults, false);

            // Set exam result details
            TextView textViewExamName = examResultCard.findViewById(R.id.textViewExamName);
            TextView textViewScore = examResultCard.findViewById(R.id.textViewScore);
            TextView textViewTotalCorrect = examResultCard.findViewById(R.id.textViewTotalCorrect);
            TextView textViewTimeTaken = examResultCard.findViewById(R.id.textViewTimeTaken);
            TextView textViewDateCompleted = examResultCard.findViewById(R.id.textViewDateCompleted);
            TextView textViewExamDuration = examResultCard.findViewById(R.id.textViewExamDuration);
            TextView textViewExamLevel = examResultCard.findViewById(R.id.textViewExamLevel);
            TextView textViewTotalQuestions = examResultCard.findViewById(R.id.textViewTotalQuestions);
            TextView textViewExamMarks = examResultCard.findViewById(R.id.textViewExamMarks);

            TextView textViewPercentage = examResultCard.findViewById(R.id.textViewPercentage);
            TextView textViewAttempt =examResultCard.findViewById(R.id.textViewAttempt);

            int percentage=0;
            textViewExamName.setText(result.getExamName());
            textViewScore.setText("Score: " + result.getScore());
            textViewPercentage.setText("Percentage: "+percentage+" %");
            textViewTotalCorrect.setText("Total Correct: " + result.getTotalCorrect());
            textViewTimeTaken.setText("Time Taken: " + result.getTimeTaken() + " minutes");
            textViewDateCompleted.setText("Date Completed: " + result.getDateCompleted());
            textViewExamDuration.setText("Exam Duration: " + result.getExamDuration() + " minutes");
            textViewExamLevel.setText("Exam Level: " + result.getExamLevel());
            textViewTotalQuestions.setText("Total Questions: " + result.getExamTotalQuestion());
            textViewExamMarks.setText("Total Marks: " + result.getExamMarks());
            textViewAttempt.setText("Attempt : "+i);
            Button deleteResultbtn=examResultCard.findViewById(R.id.buttonDelete);
            deleteResultbtn.setOnClickListener(view->{
                Toast.makeText(getBaseContext(),"Deleting Result...",Toast.LENGTH_SHORT).show();
                deleteResult(result.getId(),linearLayoutExamResults,examResultCard);
            });
            // Add the card to the LinearLayout
            linearLayoutExamResults.addView(examResultCard);
            i++;
        }
        progressIndicator.setVisibility(View.GONE);
    }

    private void deleteResult(String resultId,LinearLayout parentLayout,View card)
    {
        progressIndicator.setVisibility(View.VISIBLE);
        RetrofitClientFactoryService client= RetrofitClientFactoryService.getInstance();
        ApiEndpointsService apiEndpointsService = client.getApi();
        Call<ApiResponse<String>> call= apiEndpointsService.deleteResult(resultId);
        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<String> response1 = response.body();
                    Toast.makeText(getBaseContext(),response1.getData(),Toast.LENGTH_SHORT).show();
                    parentLayout.removeView(card);
                    progressIndicator.setVisibility(View.GONE);
                } else {
                    ApiError error = client.convertError(response.errorBody());
                    Toast.makeText(getBaseContext(),error.getError(),Toast.LENGTH_LONG).show();
                    progressIndicator.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Toast.makeText(getBaseContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                progressIndicator.setVisibility(View.GONE);
            }
        });
    }
}