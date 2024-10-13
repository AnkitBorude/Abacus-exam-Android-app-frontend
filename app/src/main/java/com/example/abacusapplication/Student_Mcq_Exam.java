package com.example.abacusapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abacusapplication.adapters.McqAdapter;
import com.example.abacusapplication.models.Question;

import java.util.ArrayList;
import java.util.List;

public class Student_Mcq_Exam extends AppCompatActivity {
    private McqAdapter mcqAdapter;
    private List<Question> questionList;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_mcq_exam);
        recyclerView=findViewById(R.id.recyclerView);
        Button submitExam=findViewById(R.id.submit_exam);
        Button goback=findViewById(R.id.go_back_button);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        questionList = new ArrayList<>();
        questionList.add(new Question("149/7+150", 171, 2, 171, 136, 211, 111));
        questionList.add(new Question("What is 2+2?", 4, 1, 3, 4, 5, 6));

        // Initialize and set the adapter
       mcqAdapter = new McqAdapter(this, questionList);
        recyclerView.setAdapter(mcqAdapter);

        submitExam.setOnClickListener(view->{
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
        });
    }
}