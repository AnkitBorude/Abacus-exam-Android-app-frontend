package com.example.abacusapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        CardView profileCard=findViewById(R.id.profileCard);
        CardView createExamcard=findViewById(R.id.create_exam);

        profileCard.setOnClickListener(view->{
            Intent intent=new Intent(getBaseContext(),AdminProfile.class);
            startActivity(intent);
        });

        createExamcard.setOnClickListener(view -> {
            Intent intent=new Intent(getBaseContext(),AdminCreateExam.class);
            startActivity(intent);
        });
    }
}