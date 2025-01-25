package com.example.abacusapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.abacusapplication.services.ApiEndpointsService;
import com.example.abacusapplication.services.RetrofitClientFactoryService;
import com.example.abacusapplication.models.ApiError;
import com.example.abacusapplication.models.ApiResponse;
import com.example.abacusapplication.models.Exam;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAllExam extends AppCompatActivity {

    private LinearLayout linearLayoutExams;
    private List<Exam> examList;
    private CircularProgressIndicator progressIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_exam);
        linearLayoutExams = findViewById(R.id.linearLayoutExams);
        progressIndicator = findViewById(R.id.progress_circular);
        fetchExam();

    }

    private void fetchExam()
    {
        progressIndicator.setVisibility(View.VISIBLE);
        RetrofitClientFactoryService client= RetrofitClientFactoryService.getInstance();
        ApiEndpointsService apiEndpointsService = client.getApi();
        Call<ApiResponse<List<Exam>>> call= apiEndpointsService.getExams();
        call.enqueue(new Callback<ApiResponse<List<Exam>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Exam>>> call, Response<ApiResponse<List<Exam>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Exam>> response1 = response.body();
                    createExamUI(response1.getData());
                } else {
                    ApiError error = client.convertError(response.errorBody());
                    Toast.makeText(getBaseContext(),error.getError(),Toast.LENGTH_LONG).show();
                    progressIndicator.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<Exam>>> call, Throwable t) {
                Toast.makeText(getBaseContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                progressIndicator.setVisibility(View.GONE);
            }
        });
    }
    private void createExamUI(List<Exam> exams)
    {
        linearLayoutExams.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (Exam exam : exams) {
            View examCard = inflater.inflate(R.layout.item_admin_exam_card, linearLayoutExams, false);

            // Set exam details
            TextView textViewTitle = examCard.findViewById(R.id.textViewTitle);
            TextView textViewDuration = examCard.findViewById(R.id.textViewDuration);
            TextView textViewLevel = examCard.findViewById(R.id.textViewLevel);
            TextView textViewTotalQuestions = examCard.findViewById(R.id.textViewTotalQuestions);
            TextView marksperquestion=examCard.findViewById(R.id.totalmarksperquestion);
            TextView marks=examCard.findViewById(R.id.textViewMarks);
            TextView totalAttended=examCard.findViewById(R.id.texttotaltimesattended);
            TextView totalDistinctAttended=examCard.findViewById(R.id.texttotaldistinctattended);
            TextView highestMarks=examCard.findViewById(R.id.texthighestscore);
            TextView badge=examCard.findViewById(R.id.badge);
            Button buttonAttend = examCard.findViewById(R.id.buttonAttend);
            Button buttonMoreoptions=examCard.findViewById(R.id.buttonMoreOptions);

            textViewTitle.setText(exam.getTitle());
            textViewDuration.setText("Duration: " + exam.getDuration() + " mins");
            textViewLevel.setText("Level: " + exam.getLevel());
            textViewTotalQuestions.setText("Total Questions: " + exam.getTotal_questions());
            marksperquestion.setText("Marks Per Question: " +exam.getTotal_marks_per_question());
            marks.setText("Total Marks: "+exam.getTotal_marks());
            totalAttended.setText("Total Times Attended: "+exam.getTotalAttended());
            totalDistinctAttended.setText("Total Distinct Attended: "+exam.getUniqueStudents());
            highestMarks.setText("Highest Marks Scored :"+exam.getHighestScore());
            if(exam.isSingleAttempt()) {
                badge.setText(" Assessment");
                badge.setBackgroundResource(R.drawable.badge_background);
            }else {
                badge.setText(" Practice");
            }
            // Enable the Attend button only if the exam is active
            if (exam.isIs_active()) {
                buttonAttend.setText("DEACTIVATE");
                buttonAttend.setBackgroundResource(R.drawable.roundbutton_red);
            }
            else
            {
                buttonAttend.setText("ACTIVATE");
                buttonAttend.setBackgroundResource(R.drawable.roundbutton_green);
            }
            buttonAttend.setOnClickListener(v -> {
                if(buttonAttend.getText().equals("ACTIVATE"))
                {
                    activateExam(exam.getId());
                    exam.setIs_active(true);
                    buttonAttend.setText("DEACTIVATE");
                    buttonAttend.setBackgroundResource(R.drawable.roundbutton_red);
                }else {
                    deActivateExam(exam.getId());
                    exam.setIs_active(false);
                    buttonAttend.setText("ACTIVATE");
                    buttonAttend.setBackgroundResource(R.drawable.roundbutton_green);
                }

            });

            buttonMoreoptions.setOnClickListener(view->{
                showOptionsDialog(linearLayoutExams,examCard, exam.getId());
            });
            // Add the card to the LinearLayout
            linearLayoutExams.addView(examCard);
        }
        progressIndicator.setVisibility(View.GONE);
    }
    private void activateExam(String examId)
    {
        progressIndicator.setVisibility(View.VISIBLE);
        RetrofitClientFactoryService client= RetrofitClientFactoryService.getInstance();
        ApiEndpointsService apiEndpointsService = client.getApi();
        Call<ApiResponse<String>> call= apiEndpointsService.activateExam(examId);
        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<String> response1 = response.body();
                    Toast.makeText(getBaseContext(),response1.getData(),Toast.LENGTH_SHORT).show();
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
    private void deActivateExam(String examId)
    {
        progressIndicator.setVisibility(View.VISIBLE);
        RetrofitClientFactoryService client= RetrofitClientFactoryService.getInstance();
        ApiEndpointsService apiEndpointsService = client.getApi();
        Call<ApiResponse<String>> call= apiEndpointsService.deactivateExam(examId);
        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<String> response1 = response.body();
                    Toast.makeText(getBaseContext(),response1.getData(),Toast.LENGTH_SHORT).show();
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

    private void showOptionsDialog(LinearLayout parentLayout,View card,String examId)
    {
        String[] options = {"Delete Exam", "Delete All Results"};

        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminAllExam.this);
        builder.setTitle("Exam Options");

        // Set the options in the dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Delete
                        Toast.makeText(AdminAllExam.this, "Deleting Exam...", Toast.LENGTH_SHORT).show();
                        deleteExam(examId,parentLayout,card);
                        break;
                    case 1: // DeleteFull
                        Toast.makeText(AdminAllExam.this, "Deleting All Results...", Toast.LENGTH_SHORT).show();
                        deleteResults(examId);
                        break;
                    case 2: // View More Info
                        Toast.makeText(AdminAllExam.this, "View More Info for Card", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        // Show the dialog
        builder.show();
    }

    private void deleteExam(String examId,LinearLayout parentLayout,View card)
    {
        progressIndicator.setVisibility(View.VISIBLE);
        RetrofitClientFactoryService client= RetrofitClientFactoryService.getInstance();
        ApiEndpointsService apiEndpointsService = client.getApi();
        Call<ApiResponse<String>> call= apiEndpointsService.deleteExam(examId);
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

    private void deleteResults(String examId)
    {
        progressIndicator.setVisibility(View.VISIBLE);
        RetrofitClientFactoryService client= RetrofitClientFactoryService.getInstance();
        ApiEndpointsService apiEndpointsService = client.getApi();
        Call<ApiResponse<String>> call= apiEndpointsService.deleteResults(examId);
        call.enqueue(new Callback<ApiResponse<String>>() {
            @Override
            public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<String> response1 = response.body();
                    Toast.makeText(getBaseContext(),response1.getData(),Toast.LENGTH_SHORT).show();
                    fetchExam();
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
}