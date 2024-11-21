package com.example.abacusapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.abacusapplication.data.ApiService;
import com.example.abacusapplication.data.RetrofitClient;
import com.example.abacusapplication.models.StudentresultInfo;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class AdminViewAllStudents extends AppCompatActivity {
    private LinearLayout linearLayoutStudents;
    private CircularProgressIndicator progressIndicator;

    private RetrofitClient client;
    private ApiService apiService;

    private Spinner classSpinner,levelSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_all_students);
        linearLayoutStudents = findViewById(R.id.studentcardlayout);

        progressIndicator = findViewById(R.id.progress_circular);

        SearchView view=findViewById(R.id.searchview);
        this.client=RetrofitClient.getInstance();
        this.apiService = this.client.getApi();
        this.classSpinner=findViewById(R.id.classSpinner);
        this.levelSpinner=findViewById(R.id.levelSpinner);

        view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        progressIndicator.setVisibility(View.VISIBLE);
    }

    private List<StudentresultInfo> searchStudent(String name, int studentClass,int studentLevel)
    {

        return null;
    }
    private void createExamUI(List<StudentresultInfo> students) {
        LayoutInflater inflater = LayoutInflater.from(this);
        for (StudentresultInfo student : students) {
            View studentCard = inflater.inflate(R.layout.item_student_exam_info_card, linearLayoutStudents, false);

            // Set student details
            TextView textViewStudentName = studentCard.findViewById(R.id.textViewStudentName);
            TextView textViewStudentEmail = studentCard.findViewById(R.id.textViewStudentEmail);
            TextView textViewStudentLevel = studentCard.findViewById(R.id.textViewStudentLevel);
            TextView textViewStudentClass = studentCard.findViewById(R.id.textViewStudentClass);
            TextView textViewStudentPhone = studentCard.findViewById(R.id.textViewStudentPhone);

            Button buttonAttend = studentCard.findViewById(R.id.buttonAttend);

            textViewStudentName.setText(student.getStudentName());
            textViewStudentEmail.setText("Email: " + student.getStudentEmail());
            textViewStudentLevel.setText("Level: " + student.getStudentLevel());
            textViewStudentClass.setText("Class: " + student.getStudentClass());
            textViewStudentPhone.setText("Phone: " + student.getStudentPhoneNo());

            // Add the card to the LinearLayout
            linearLayoutStudents.addView(studentCard);
            progressIndicator.setVisibility(View.GONE);
        }
    }
}