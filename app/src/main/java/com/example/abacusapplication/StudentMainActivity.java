package com.example.abacusapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class StudentMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        CardView card=findViewById(R.id.attend_exam);
        CardView resultCard=findViewById(R.id.resultCard);
        card.setOnClickListener(view->{
            Intent intent=new Intent(StudentMainActivity.this,StudentAllExam.class);
            startActivity(intent);
        });
        resultCard.setOnClickListener(view->{
            Intent intent=new Intent(StudentMainActivity.this,StudentAllExamResult.class);
            startActivity(intent);
        });
    }
}