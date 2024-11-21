package com.example.abacusapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.abacusapplication.ui.AdminAllExam;


public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        CardView profileCard=findViewById(R.id.profileCard);
        CardView createExamcard=findViewById(R.id.create_exam);
        CardView viewExam=findViewById(R.id.adminViewexam);
        CardView viewResult=findViewById(R.id.viewResult);
        CardView viewStudents=findViewById(R.id.adminViewStudents);
        Button logoutbtn=findViewById(R.id.logoutbutton);
        profileCard.setOnClickListener(view->{
            Intent intent=new Intent(getBaseContext(),AdminProfile.class);
            startActivity(intent);
        });

        createExamcard.setOnClickListener(view -> {
            Intent intent=new Intent(getBaseContext(),AdminCreateExam.class);
            startActivity(intent);
        });

        viewExam.setOnClickListener(view->{
            Intent intent=new Intent(getBaseContext(), AdminAllExam.class);
            startActivity(intent);
        });

        viewResult.setOnClickListener(view->{
            Intent intent=new Intent(getBaseContext(), AdminAllExamResult.class);
            startActivity(intent);
        });

        viewStudents.setOnClickListener(view->{
            Intent intent=new Intent(getBaseContext(), AdminViewAllStudents.class);
            startActivity(intent);
        });
        logoutbtn.setOnClickListener(view->{
            finish();
        });
    }
}