package com.example.abacusapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class StudentMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        CardView card=findViewById(R.id.attend_exam);
        CardView resultCard=findViewById(R.id.resultCard);
        CardView downloadResult=findViewById(R.id.studentDownloadResult);
        CardView profileCard=findViewById(R.id.profileCard);
        CardView updateProfile=findViewById(R.id.updateProfile);
        CardView helpCard=findViewById(R.id.helpCard);
        Button logoutbtn=findViewById(R.id.logoutbutton);
        card.setOnClickListener(view->{
            Intent intent=new Intent(StudentMainActivity.this,StudentAllExam.class);
            startActivity(intent);
        });
        resultCard.setOnClickListener(view->{
            Intent intent=new Intent(StudentMainActivity.this,StudentAllExamResult.class);
            intent.putExtra("type","VIEW");
            startActivity(intent);
        });
        downloadResult.setOnClickListener(view->{
            Intent intent=new Intent(StudentMainActivity.this,StudentAllExamResult.class);
            intent.putExtra("type","DOWNLOAD");
            startActivity(intent);
        });
        profileCard.setOnClickListener(view->{
            Intent intent=new Intent(StudentMainActivity.this, ComingsoonActivity.class);
            startActivity(intent);
        });
        updateProfile.setOnClickListener(view->{
            Intent intent=new Intent(StudentMainActivity.this, ComingsoonActivity.class);
            startActivity(intent);
        });

        helpCard.setOnClickListener(view->{
            Intent intent=new Intent(StudentMainActivity.this, ComingsoonActivity.class);
            startActivity(intent);
        });
        logoutbtn.setOnClickListener(view->{
            finish();
        });
    }
}