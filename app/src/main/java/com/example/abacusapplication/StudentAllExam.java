package com.example.abacusapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.abacusapplication.models.CreatedBy;
import com.example.abacusapplication.models.Exam;

import java.util.ArrayList;
import java.util.List;

public class StudentAllExam extends AppCompatActivity {
    private LinearLayout linearLayoutExams;
    private List<Exam> examList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_all_exam);
        linearLayoutExams = findViewById(R.id.linearLayoutExams);
        examList=new ArrayList<>();
        CreatedBy createdBy1 = new CreatedBy("Ankit Ravsaheb Borude");
        CreatedBy createdBy2 = new CreatedBy("Jane Doe");
        examList.add(new Exam(
                "Demo Exam 1",
                120,
                "2",
                10,
                20,
                2,
                true,
                createdBy1,
                "2024-09-29T08:24:22.404Z",
                "66f90eb6d2f850991d0ea86d"
        ));

        examList.add(new Exam(
                "Demo Exam 2",
                90,
                "1",
                8,
                16,
                2,
                false,
                createdBy2,
                "2024-10-01T10:24:22.404Z",
                "67a90fb6e1f840991d0fa87d"
        ));

        LayoutInflater inflater = LayoutInflater.from(this);
        for (Exam exam : examList) {
            View examCard = inflater.inflate(R.layout.item_exam_card, linearLayoutExams, false);

            // Set exam details
            TextView textViewTitle = examCard.findViewById(R.id.textViewTitle);
            TextView textViewDuration = examCard.findViewById(R.id.textViewDuration);
            TextView textViewLevel = examCard.findViewById(R.id.textViewLevel);
            TextView textViewTotalQuestions = examCard.findViewById(R.id.textViewTotalQuestions);
            TextView marksperquestion=examCard.findViewById(R.id.totalmarksperquestion);
            TextView marks=examCard.findViewById(R.id.textViewMarks);
            TextView createdBy=examCard.findViewById(R.id.textcreatedBy);

            Button buttonAttend = examCard.findViewById(R.id.buttonAttend);

            textViewTitle.setText(exam.getTitle());
            textViewDuration.setText("Duration: " + exam.getDuration() + " mins");
            textViewLevel.setText("Level: " + exam.getLevel());
            textViewTotalQuestions.setText("Total Questions: " + exam.getTotal_questions());
            marksperquestion.setText("Marks Per Question: " +exam.getTotal_marks_per_question());
            marks.setText("Total Marks: "+exam.getTotal_marks());
            createdBy.setText("Created by: "+exam.getCreated_by().getFullname());
            // Enable the Attend button only if the exam is active
            if (exam.isIs_active()) {
                buttonAttend.setEnabled(true);
                buttonAttend.setText("ATTEND");
                buttonAttend.setBackgroundResource(R.drawable.roundbutton_green);
                buttonAttend.setOnClickListener(v -> {
                    // Handle attend exam logic here
//                    Intent intent = new Intent(MainActivity.this, ExamDetailsActivity.class);
//                    // Pass the exam ID to the next activity
//                    intent.putExtra("exam_id", exam.getId());
//                    startActivity(intent);
                });
            }

            // Add the card to the LinearLayout
            linearLayoutExams.addView(examCard);
        }
    }
}