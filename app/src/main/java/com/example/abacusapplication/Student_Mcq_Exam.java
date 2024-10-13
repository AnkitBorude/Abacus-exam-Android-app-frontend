package com.example.abacusapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abacusapplication.adapters.McqAdapter;
import com.example.abacusapplication.data.ApiService;
import com.example.abacusapplication.data.RetrofitClient;
import com.example.abacusapplication.models.ApiError;
import com.example.abacusapplication.models.ApiResponse;
import com.example.abacusapplication.models.Exam;
import com.example.abacusapplication.models.Question;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Student_Mcq_Exam extends AppCompatActivity {
    private McqAdapter mcqAdapter;
    private List<Question> questionList;
    private RecyclerView recyclerView;
    private TextView timerTextView;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_mcq_exam);
        Intent intent = getIntent();

        String examId = intent.getStringExtra("examId");

        String examName = intent.getStringExtra("examName");
        int examDuration = intent.getIntExtra("examDuration", 0);
        int examTotalQuestion = intent.getIntExtra("examTotalQuestion", 0);
        int examTotalMarks = intent.getIntExtra("examTotalMarks", 0);

        recyclerView=findViewById(R.id.recyclerView);
        Button submitExam=findViewById(R.id.submit_exam);
        Button goback=findViewById(R.id.go_back_button);
        timerTextView=findViewById(R.id.timer);

        TextView examTitle=findViewById(R.id.textViewTitle);
        TextView totalQuestions=findViewById(R.id.textViewTotalQuestions);
        TextView totalMarks=findViewById(R.id.textViewMarks);
        examTitle.setText(""+examName);
        totalQuestions.setText("Total Questions :"+examTotalQuestion+"");
        totalMarks.setText("Total Marks :"+examTotalMarks+"");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        questionList = new ArrayList<>();
        questionList.add(new Question("149/7+150", 171, 2, 171, 136, 211, 111));
        questionList.add(new Question("What is 2+2?", 4, 1, 3, 4, 5, 6));

        Context context=this;


        RetrofitClient client=RetrofitClient.getInstance();
        ApiService apiService = client.getApi();
        Call<ApiResponse<List<Question>>> call=apiService.getQuestions(examId);
        call.enqueue(new Callback<ApiResponse<List<Question>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Question>>> call, Response<ApiResponse<List<Question>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Question>> response1 = response.body();
                    questionList=response1.getData();
                    mcqAdapter = new McqAdapter(context, questionList);
                    recyclerView.setAdapter(mcqAdapter);
                    startTimer(examDuration);
                } else {
                    ApiError error = client.convertError(response.errorBody());
                    Toast.makeText(getBaseContext(),error.getError(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<Question>>> call, Throwable t) {
                Toast.makeText(getBaseContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        // Initialize and set the adapter


        submitExam.setOnClickListener(view->{
           submitExam();
        });
    }
    private void startTimer(int minutes) {
        // Convert minutes to milliseconds
        timeLeftInMillis = minutes * 60 * 1000;

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }
            @Override
            public void onFinish() {
                // Timer finished, show a message or take other actions
                timerTextView.setText("Time's up!");
                submitExam();//submit exam when countdown ends
            }
        }.start();
    }

    private void updateTimer() {
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted;

        if (hours > 0) {
            // If the duration is more than 1 hour, show hours too
            timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            // Otherwise, show only minutes and seconds
            timeFormatted = String.format("%02d:%02d", minutes, seconds);
        }

        timerTextView.setText(timeFormatted);
    }
    private void submitExam()
    {
        int score = 0;
        int solvedQuestions=0;
        int correctAnswers=0;
        int incorrectAnswers=0;
        int[] selectedAnswers = mcqAdapter.getSelectedAnswers();
        for (int i = 0; i < questionList.size(); i++) {
            if(selectedAnswers[i]!=-1) {
                solvedQuestions++;
                if (selectedAnswers[i] == questionList.get(i).getAnswer()) {
                    correctAnswers++;
                    score += questionList.get(i).getMarks(); // Add marks if the answer is correct
                }
            }
        }
        incorrectAnswers=solvedQuestions-correctAnswers;
        Toast.makeText(Student_Mcq_Exam.this, "Your Score: " + score+" Solved Questions "+solvedQuestions +" InCorrect "+incorrectAnswers, Toast.LENGTH_LONG).show();
    }
}