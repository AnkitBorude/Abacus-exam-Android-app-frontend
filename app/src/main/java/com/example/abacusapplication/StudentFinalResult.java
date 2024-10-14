package com.example.abacusapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StudentFinalResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_final_result);

        String examName = getIntent().getStringExtra("examName");
        int examDuration = getIntent().getIntExtra("examDuration",0);
        int examTotalQuestion = getIntent().getIntExtra("examTotalQuestion", 0);
        int examTotalMarks = getIntent().getIntExtra("examTotalMarks", 0);
        int score = getIntent().getIntExtra("Score", 0);
        String dateCompleted = getIntent().getStringExtra("DateCompleted");
        int timeTaken = getIntent().getIntExtra("timeTaken",0);
        int totalCorrect = getIntent().getIntExtra("totalCorrect", 0);

        TextView examNameTextView = findViewById(R.id.examNameTextView);
        TextView examDurationTextView = findViewById(R.id.examDurationTextView);
        TextView examTotalQuestionTextView = findViewById(R.id.examTotalQuestionTextView);
        TextView examTotalMarksTextView = findViewById(R.id.examTotalMarksTextView);
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        TextView dateCompletedTextView = findViewById(R.id.dateCompletedTextView);
        TextView timeTakenTextView = findViewById(R.id.timeTakenTextView);
        TextView totalCorrectTextView = findViewById(R.id.totalCorrectTextView);
        Button goBackButton = findViewById(R.id.goBackButton);

        examNameTextView.setText("Exam: " + examName);
        examDurationTextView.setText("Duration: " + examDuration+" Minutes");
        examTotalQuestionTextView.setText("Total Questions: " + examTotalQuestion);
        examTotalMarksTextView.setText("Total Marks: " + examTotalMarks);
        scoreTextView.setText("Your Score: " + score + " / " + examTotalMarks);
        dateCompletedTextView.setText("Completed on: " + dateCompleted);
        timeTakenTextView.setText("Time Taken: " + timeTaken+" Minutes ");
        totalCorrectTextView.setText("Correct Answers: " + totalCorrect + " / " + examTotalQuestion);

        goBackButton.setOnClickListener(v -> goToDashboard());

    }




    private void goToDashboard() {
        Intent intent = new Intent(this, StudentMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}