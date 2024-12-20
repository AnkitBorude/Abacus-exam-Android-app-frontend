package com.example.abacusapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.abacusapplication.data.ApiService;
import com.example.abacusapplication.data.RetrofitClient;
import com.example.abacusapplication.models.ApiError;
import com.example.abacusapplication.models.ApiResponse;
import com.example.abacusapplication.models.StudentresultInfo;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminViewAllStudents extends AppCompatActivity {
    private LinearLayout linearLayoutStudents;
    private CircularProgressIndicator progressIndicator;

    private RetrofitClient client;
    private ApiService apiService;

    private Spinner classSpinner=null,levelSpinner=null;

    private String selectedClass=null,selectedLevel=null;
    private boolean isNewActivity=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_all_students);
        linearLayoutStudents = findViewById(R.id.studentcardlayout);

        progressIndicator = findViewById(R.id.progress_circular);

        SearchView sview=findViewById(R.id.searchview);
        this.client=RetrofitClient.getInstance();
        this.apiService = this.client.getApi();
        this.classSpinner=findViewById(R.id.classSpinner);
        this.levelSpinner=findViewById(R.id.levelSpinner);

        String[] dropdownitems = {"Select...", "1", "2", "3", "4", "5","6","7","8","9","10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dropdownitems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(adapter);
        levelSpinner.setAdapter(adapter);

       searchStudent("",null,null);
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0)
                {
                    selectedClass=dropdownitems[i];
                }else{
                    selectedClass=null;
                }
                Log.d("spinner","class selected");
                if(!isNewActivity) {
                    searchStudent(sview.getQuery().toString(), selectedClass, selectedLevel);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0)
                {
                    selectedLevel=dropdownitems[i];
                }else{
                    selectedLevel=null;
                }
                Log.d("spinner","level selected");
                if(!isNewActivity) {
                    searchStudent(sview.getQuery().toString(), selectedClass, selectedLevel);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Query Submission","Query Submitted--");
                searchStudent(query,selectedClass,selectedLevel);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {

                    Log.d("Query Change", "Query is empty--");
                    searchStudent("", selectedClass, selectedLevel); // Pass empty string for empty query
                }
                else {
                   // disabling for some time later will implement the debouncing of query
                    // searchStudent(newText, selectedClass, selectedLevel);
                }
                return true;
            }
        });
        progressIndicator.setVisibility(View.VISIBLE);
        isNewActivity=false;
    }

    private void searchStudent(String name, String studentClass,String studentLevel)
    {
        if(name.isEmpty()) {
            name=null;
        }
        Call<ApiResponse<List<StudentresultInfo>>> call=this.apiService.getStudents(name,studentClass,studentLevel);
        call.enqueue(new Callback<ApiResponse<List<StudentresultInfo>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<StudentresultInfo>>> call, Response<ApiResponse<List<StudentresultInfo>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<StudentresultInfo>> response1 = response.body();
                    createExamUI(response1.getData());
                } else {
                    ApiError error = client.convertError(response.errorBody());
                    Toast.makeText(getBaseContext(),error.getError(),Toast.LENGTH_LONG).show();
                    linearLayoutStudents.removeAllViews();
                    progressIndicator.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<StudentresultInfo>>> call, Throwable t) {
                Toast.makeText(getBaseContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                progressIndicator.setVisibility(View.GONE);
            }
        });
    }
    private void createExamUI(List<StudentresultInfo> students) {
        linearLayoutStudents.removeAllViews();
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
            Button buttonMoreoptions=studentCard.findViewById(R.id.buttonMoreOptions);
            textViewStudentName.setText(student.getStudentName());
            textViewStudentEmail.setText("Email: " + student.getStudentEmail());
            textViewStudentLevel.setText("Level: " + student.getStudentLevel());
            textViewStudentClass.setText("Class: " + student.getStudentClass());
            textViewStudentPhone.setText("Phone: " + student.getStudentPhoneNo());
            buttonAttend.setVisibility(View.GONE);
            buttonMoreoptions.setOnClickListener(view->{
                showOptionsDialog(linearLayoutStudents,studentCard, student.getStudentId());
            });
            // Add the card to the LinearLayout
            linearLayoutStudents.addView(studentCard);
            progressIndicator.setVisibility(View.GONE);
        }
    }

    private void deleteStudent(String studentId,LinearLayout parentLayout,View card)
    {
        progressIndicator.setVisibility(View.VISIBLE);
        RetrofitClient client=RetrofitClient.getInstance();
        ApiService apiService = client.getApi();
        Call<ApiResponse<String>> call=apiService.deleteStudent(studentId);
        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<String> response1 = response.body();
                    Toast.makeText(getBaseContext(),response1.getData(),Toast.LENGTH_SHORT).show();
                    parentLayout.removeView(card);
                    progressIndicator.setVisibility(View.GONE);
                } else {
                    ApiError error = client.convertError(response.errorBody());
                    Toast.makeText(getBaseContext(),error.getError(),Toast.LENGTH_LONG).show();
                    progressIndicator.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Toast.makeText(getBaseContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                progressIndicator.setVisibility(View.GONE);
            }
        });
    }

    private void deleteStudentFull(String studentId,LinearLayout parentLayout,View card)
    {
        progressIndicator.setVisibility(View.VISIBLE);
        RetrofitClient client=RetrofitClient.getInstance();
        ApiService apiService = client.getApi();
        Call<ApiResponse<String>> call=apiService.deleteFullStudent(studentId);
        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<String> response1 = response.body();
                    Toast.makeText(getBaseContext(),response1.getData(),Toast.LENGTH_SHORT).show();
                    parentLayout.removeView(card);
                    progressIndicator.setVisibility(View.GONE);
                } else {
                    ApiError error = client.convertError(response.errorBody());
                    Toast.makeText(getBaseContext(),error.getError(),Toast.LENGTH_LONG).show();
                    progressIndicator.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                Toast.makeText(getBaseContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                progressIndicator.setVisibility(View.GONE);
            }
        });
    }

    private void showOptionsDialog(LinearLayout parentLayout,View card,String studentId)
    {
        String[] options = {"Delete", "Delete All Records"};

        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminViewAllStudents.this);
        builder.setTitle("Student Options");

        // Set the options in the dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Delete
                        Toast.makeText(AdminViewAllStudents.this, "Deleting student...", Toast.LENGTH_SHORT).show();
                        deleteStudent(studentId,parentLayout,card);
                        break;
                    case 1: // DeleteFull
                        Toast.makeText(AdminViewAllStudents.this, "Deleting complete student record...", Toast.LENGTH_SHORT).show();
                        deleteStudentFull(studentId,parentLayout,card);
                        break;
                    case 2: // View More Info
                        Toast.makeText(AdminViewAllStudents.this, "View More Info for Card", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        // Show the dialog
        builder.show();
    }
}