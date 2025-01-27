package com.example.abacusapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.abacusapplication.services.ApiEndpointsService;
import com.example.abacusapplication.services.RetrofitClientFactoryService;
import com.example.abacusapplication.models.ApiError;
import com.example.abacusapplication.models.ApiResponse;
import com.example.abacusapplication.models.CreateExam;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCreateExam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_exam);
        TextInputEditText examName = findViewById(R.id.exam_name);
        TextInputEditText examDuration = findViewById(R.id.exam_duration);
        TextInputEditText MarksPerQuestion = findViewById(R.id.marks_per_question);
        RangeSlider sliderLevel = findViewById(R.id.slider_level);
        RangeSlider sliderMaxTerms = findViewById(R.id.slider_max_terms);
        RangeSlider sliderTotalQuestions = findViewById(R.id.slider_total_questions);
        RangeSlider sliderMinNumber = findViewById(R.id.slider_min_number);
        RangeSlider sliderMaxNumber = findViewById(R.id.slider_max_number);
        CheckBox cbAddition = findViewById(R.id.addition);
        CheckBox cbSubtraction = findViewById(R.id.subtraction);
        CheckBox cbMultiplication = findViewById(R.id.multiplication);
        CheckBox cbDivision = findViewById(R.id.division);
        CircularProgressIndicator progressIndicator = findViewById(R.id.progress_circular);

        RadioGroup rgIsActive = findViewById(R.id.rg_is_active);
        RadioButton rbYes = findViewById(R.id.rb_yes);
        RadioButton rbNo = findViewById(R.id.rb_no);

        RadioGroup rgExamType=findViewById(R.id.rg_exam_type);
        RadioButton rbExams=findViewById(R.id.rb_assessment);
        RadioButton rbPractice=findViewById(R.id.rb_practice);

        Button btnCreateExam = findViewById(R.id.create_exam);
        Button btnGoBack = findViewById(R.id.go_back_button);

        btnCreateExam.setOnClickListener(view -> {

            progressIndicator.setVisibility(View.VISIBLE);
            // Create an instance of CreateExam
            CreateExam createExam = new CreateExam();

            // Capture the title (Exam Name)
            String title = examName.getText().toString();
            createExam.setTitle(title);

            // Capture the duration
            int duration = Integer.parseInt(examDuration.getText().toString());
            createExam.setDuration(duration);

            // Capture marks per question
            int marksPerQuestionValue = Integer.parseInt(MarksPerQuestion.getText().toString());
            createExam.setTotalMarksPerQuestion(marksPerQuestionValue);

            // Capture the level (slider)
            int level = Math.round(sliderLevel.getValues().get(0));
            createExam.setLevel(level);

            // Capture max terms (slider)
            int maxTerms = Math.round(sliderMaxTerms.getValues().get(0));
            createExam.setMaxTerms(String.valueOf(maxTerms));

            // Capture total questions (slider)
            int totalQuestions = Math.round(sliderTotalQuestions.getValues().get(0));
            createExam.setTotalQuestions(String.valueOf(totalQuestions));

            // Capture min and max numbers (sliders)
            int minNumber = Math.round(sliderMinNumber.getValues().get(0));
            createExam.setMinNumber(String.valueOf(minNumber));

            int maxNumber = Math.round(sliderMaxNumber.getValues().get(0));
            createExam.setMaxNumber(String.valueOf(maxNumber));

            // Capture selected operators (checkboxes)
            List<String> operatorsList = new ArrayList<>();
            if (cbAddition.isChecked()) {
                operatorsList.add("+");
            }
            if (cbSubtraction.isChecked()) {
                operatorsList.add("-");
            }
            if (cbMultiplication.isChecked()) {
                operatorsList.add("*");
            }
            if (cbDivision.isChecked()) {
                operatorsList.add("/");
            }
            // Convert List to Array
            String[] operatorsArray = operatorsList.toArray(new String[0]);
            createExam.setOperators(operatorsArray);

            boolean isActive;
            if (rgIsActive.getCheckedRadioButtonId() == R.id.rb_yes) {
                isActive = true;  // Yes is selected, so isActive is true
            } else {
                isActive = false; // No is selected, so isActive is false
            }

            if(rgExamType.getCheckedRadioButtonId()==R.id.rb_assessment)
            {
                createExam.setSingleAttempt(true);
            }
            else
            {
                createExam.setSingleAttempt(false);
            }
            // Set exam as active
            createExam.setActive(isActive);

            // Log the CreateExam object to verify
            Log.d("CreateExam Object", createExam.toString());

            RetrofitClientFactoryService client= RetrofitClientFactoryService.getInstance();
            ApiEndpointsService service= client.getApi();
            Call<ApiResponse<String>> call=service.createExam(createExam);
            call.enqueue(new Callback<ApiResponse<String>>() {
                @Override
                public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                    if (response.isSuccessful()) {
                        ApiResponse<String> response1 = response.body();
                        progressIndicator.setVisibility(View.GONE);
                        Toast.makeText(AdminCreateExam.this,"Exam Created SuccessFully",Toast.LENGTH_SHORT).show();
                    } else {
                        ApiError error = client.convertError(response.errorBody());
                        progressIndicator.setVisibility(View.GONE);
                        Toast.makeText(AdminCreateExam.this,error.getError(),Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                    progressIndicator.setVisibility(View.GONE);
                    Toast.makeText(AdminCreateExam.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        });

        // Handle Go Back button click
        btnGoBack.setOnClickListener(view -> {
            // Handle go back logic
            finish(); // Close the current activity, for example
        });
    }
}