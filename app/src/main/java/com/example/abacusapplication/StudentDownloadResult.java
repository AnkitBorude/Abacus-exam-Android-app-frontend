package com.example.abacusapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.abacusapplication.services.ApiEndpointsService;
import com.example.abacusapplication.services.RetrofitClientFactoryService;
import com.example.abacusapplication.models.ApiError;
import com.example.abacusapplication.models.ApiResponse;
import com.example.abacusapplication.models.Exam;
import com.example.abacusapplication.models.ExamResult;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentDownloadResult extends AppCompatActivity {
    private LinearLayout linearLayoutExamResults;
    private List<Exam> examList;
    private CircularProgressIndicator progressIndicator;
    private String examId;
    NotificationManager notificationManager;
    Notification.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_download_result);

       notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //building the notification through notifiation builder where we can
       builder= new Notification.Builder(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("My channel");
            notificationManager.createNotificationChannel(
                    new NotificationChannel("My channel","New Channel",NotificationManager.IMPORTANCE_HIGH));
        }

        linearLayoutExamResults = findViewById(R.id.linearLayoutExams);
        Intent intent = getIntent();
        this.examId=intent.getStringExtra("examId");

        progressIndicator = findViewById(R.id.progress_circular);
        progressIndicator.setVisibility(View.VISIBLE);
        RetrofitClientFactoryService client= RetrofitClientFactoryService.getInstance();
        ApiEndpointsService apiEndpointsService = client.getApi();
        Call<ApiResponse<List<ExamResult>>> call= apiEndpointsService.getStudentResult(this.examId);
        call.enqueue(new Callback<ApiResponse<List<ExamResult>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<ExamResult>>> call, Response<ApiResponse<List<ExamResult>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<ExamResult>> response1 = response.body();
                    createExamUI(response1.getData());
                } else {
                    ApiError error = client.convertError(response.errorBody());
                    Toast.makeText(getBaseContext(),error.getError(),Toast.LENGTH_LONG).show();
                    progressIndicator.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<ExamResult>>> call, Throwable t) {
                Toast.makeText(getBaseContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                progressIndicator.setVisibility(View.GONE);
            }
        });
    }
    private void createExamUI(List<ExamResult> examResults) {
        LayoutInflater inflater = LayoutInflater.from(this);
        int i=1;
        for (ExamResult result : examResults) {
            View examResultCard = inflater.inflate(R.layout.item_result_card, linearLayoutExamResults, false);

            // Set exam result details
            TextView textViewExamName = examResultCard.findViewById(R.id.textViewExamName);
            TextView textViewScore = examResultCard.findViewById(R.id.textViewScore);
            TextView textViewTotalCorrect = examResultCard.findViewById(R.id.textViewTotalCorrect);
            TextView textViewTimeTaken = examResultCard.findViewById(R.id.textViewTimeTaken);
            TextView textViewDateCompleted = examResultCard.findViewById(R.id.textViewDateCompleted);
            TextView textViewExamDuration = examResultCard.findViewById(R.id.textViewExamDuration);
            TextView textViewExamLevel = examResultCard.findViewById(R.id.textViewExamLevel);
            TextView textViewTotalQuestions = examResultCard.findViewById(R.id.textViewTotalQuestions);
            TextView textViewExamMarks = examResultCard.findViewById(R.id.textViewExamMarks);
            Button downloadbtn=examResultCard.findViewById(R.id.downloadbtn);
            downloadbtn.setVisibility(View.VISIBLE);

            Button deleteResultbtn=examResultCard.findViewById(R.id.buttonDelete);
            deleteResultbtn.setVisibility(View.GONE);
            TextView textViewPercentage = examResultCard.findViewById(R.id.textViewPercentage);
            TextView textViewAttempt =examResultCard.findViewById(R.id.textViewAttempt);
            int percentage = (int) ((result.getScore() / (double) result.getExamMarks()) * 100);
            textViewExamName.setText(result.getExamName());
            textViewScore.setText("Score: " + result.getScore());
            textViewPercentage.setText("Percentage: "+percentage+" %");
            textViewTimeTaken.setText("Time Taken: " + result.getTimeTaken() + " minutes");
            textViewDateCompleted.setText("Date Completed: " + result.getDateCompleted());
            textViewAttempt.setText("Attempt : "+i);
            textViewTotalCorrect.setVisibility(View.GONE);
            textViewExamDuration.setVisibility(View.GONE);
            textViewExamLevel.setVisibility(View.GONE);
            textViewTotalQuestions.setVisibility(View.GONE);
            textViewExamMarks.setVisibility(View.GONE);
            // Add the card to the LinearLayout
            linearLayoutExamResults.addView(examResultCard);
            i++;
            downloadbtn.setOnClickListener(view -> {
                downloadPdf(result.getId());
            });
        }
        progressIndicator.setVisibility(View.GONE);
    }

    private void downloadPdf(String resultId)
    {
        progressIndicator.setVisibility(View.VISIBLE);
        RetrofitClientFactoryService client= RetrofitClientFactoryService.getInstance();
        ApiEndpointsService apiEndpointsService = client.getApi();

        Call<ResponseBody> call= apiEndpointsService.getStudentresultPdf(resultId,"pdf");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    //passing the resultbody and downloadable file name here
                    savePdf(response.body(),"abacus-result"+resultId+".pdf");

                    builder.setContentTitle("Result Has been Downloaded..");
                    builder.setContentText("results downloaded at /Downloads/abacusapp/results");
                    builder.setSmallIcon(R.drawable.baseline_download_24);
                    notificationManager.notify(100,builder.build());
                } else {
                    ApiError error = client.convertError(response.errorBody());
                    Toast.makeText(getBaseContext(),error.getError(),Toast.LENGTH_LONG).show();
                    progressIndicator.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getBaseContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                progressIndicator.setVisibility(View.GONE);
            }
        });
    }

    private void savePdf(ResponseBody body,String fileName)
    {
        try {
            // Set up the file path
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File myAppDir = new File(downloadsDir, "abacusapp/results");

            // Create directory if it doesn't exist
            if (!myAppDir.exists()) {
                myAppDir.mkdirs();
            }

            // File to save
            File file = new File(myAppDir, fileName);

            // Write response body to file
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                int read;
                //copying data from the body bytestream and writing to file output stream
                while ((read = inputStream.read(fileReader)) != -1) {
                    outputStream.write(fileReader, 0, read);
                }

                outputStream.flush();
                // File saved successfully in Downloads/myapplication/results

                Toast.makeText(this,"File Saved in "+myAppDir.getPath(),Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        progressIndicator.setVisibility(View.GONE);
    }
}
