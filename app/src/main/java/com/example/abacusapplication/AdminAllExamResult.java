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
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAllExamResult extends AppCompatActivity {
    private LinearLayout linearLayoutExams;
    private List<Exam> examList;
    private CircularProgressIndicator progressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_exam_result);
        linearLayoutExams = findViewById(R.id.linearLayoutExams);

        progressIndicator = findViewById(R.id.progress_circular);
        progressIndicator.setVisibility(View.VISIBLE);
        RetrofitClient client=RetrofitClient.getInstance();
        ApiService apiService = client.getApi();
        Call<ApiResponse<List<Exam>>> call=apiService.getExams();
        call.enqueue(new Callback<ApiResponse<List<Exam>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Exam>>> call, Response<ApiResponse<List<Exam>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Exam>> response1 = response.body();
                    createExamUI(response1.getData());
                } else {
                    ApiError error = client.convertError(response.errorBody());
                    Toast.makeText(getBaseContext(),error.getError(),Toast.LENGTH_LONG).show();
                    progressIndicator.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<Exam>>> call, Throwable t) {
                Toast.makeText(getBaseContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                progressIndicator.setVisibility(View.GONE);
            }
        });

    }
    private void createExamUI(List<Exam> exams)
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        for (Exam exam : exams) {
            View examCard = inflater.inflate(R.layout.item_admin_exam_card, linearLayoutExams, false);

            // Set exam details
            TextView textViewTitle = examCard.findViewById(R.id.textViewTitle);
            TextView textViewDuration = examCard.findViewById(R.id.textViewDuration);
            TextView textViewLevel = examCard.findViewById(R.id.textViewLevel);
            TextView textViewTotalQuestions = examCard.findViewById(R.id.textViewTotalQuestions);
            TextView marksperquestion=examCard.findViewById(R.id.totalmarksperquestion);
            TextView marks=examCard.findViewById(R.id.textViewMarks);
            TextView totalAttended=examCard.findViewById(R.id.texttotaltimesattended);
            TextView totalDistinctAttended=examCard.findViewById(R.id.texttotaldistinctattended);
            TextView highestMarks=examCard.findViewById(R.id.texthighestscore);

            Button buttonAttend = examCard.findViewById(R.id.buttonAttend);

            textViewTitle.setText(exam.getTitle());
            textViewDuration.setText("Duration: " + exam.getDuration() + " mins");
            textViewLevel.setText("Level: " + exam.getLevel());
            textViewTotalQuestions.setText("Total Questions: " + exam.getTotal_questions());
            marksperquestion.setText("Marks Per Question: " +exam.getTotal_marks_per_question());
            marks.setText("Total Marks: "+exam.getTotal_marks());
            totalAttended.setText("Total Times Attended: "+exam.getTotalAttended());
            totalDistinctAttended.setText("Total Distinct Attended: "+exam.getUniqueStudents());
            highestMarks.setText("Highest Marks Scored :"+exam.getHighestScore());
            // Enable the Attend button only if the exam is active
                buttonAttend.setText("VIEW STUDENTS");
                buttonAttend.setBackgroundResource(R.drawable.roundbutton_blue);
                buttonAttend.setOnClickListener(v -> {
                    Intent intent = new Intent(AdminAllExamResult.this, AdminAllStudentExam.class);
                    // Pass the exam ID to the next activity
                    intent.putExtra("examId", exam.getId());
                    startActivity(intent);
            });
            // Add the card to the LinearLayout
            linearLayoutExams.addView(examCard);
        }
        progressIndicator.setVisibility(View.GONE);
    }
}