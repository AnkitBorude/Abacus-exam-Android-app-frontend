package com.example.abacusapplication.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.abacusapplication.R;
import com.example.abacusapplication.services.ApiEndpointsService;
import com.example.abacusapplication.services.RetrofitClientFactoryService;
import com.example.abacusapplication.models.ApiError;
import com.example.abacusapplication.models.ApiResponse;
import com.example.abacusapplication.models.Student;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentRegistrationActivity extends AppCompatActivity {

    CircularProgressIndicator progressIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        // 1. Create the data source
        String[] classes = new String[10];
        for (int i = 0; i < 10; i++) {
            classes[i] = String.valueOf(i + 1);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,classes);
        AutoCompleteTextView classSpinner=findViewById(R.id.classSpinner);
        AutoCompleteTextView levelSpinner=findViewById(R.id.levelSpinner);
        levelSpinner.setAdapter(adapter);
        classSpinner.setAdapter(adapter);

       progressIndicator = findViewById(R.id.progress_circular);

        Button registerbtn=findViewById(R.id.registerbutton);
        Button backtologinbtn=findViewById(R.id.backToLoginButton);

        registerbtn.setOnClickListener(view->{
           if(!register()) {
               Toast.makeText(StudentRegistrationActivity.this,"Please fill proper data",Toast.LENGTH_SHORT).show();
           }
        });
        backtologinbtn.setOnClickListener(view->{
            finish();
        });
    }

    private boolean register() {
        boolean isValid = true;

        String fullName = ((EditText)findViewById(R.id.fullNameEditText)).getText().toString().trim();
        String username = ((EditText)findViewById(R.id.usernameEditText)).getText().toString().trim();
        String studentClass = ((AutoCompleteTextView)findViewById(R.id.classSpinner)).getText().toString();
        String level = ((AutoCompleteTextView)findViewById(R.id.levelSpinner)).getText().toString();
        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString().trim();
        String phoneNo = ((EditText)findViewById(R.id.phoneEditText)).getText().toString().trim();
        String password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();

        if (fullName.isEmpty()) {
            ((EditText)findViewById(R.id.fullNameEditText)).setError("Full name is required");
            isValid = false;
        }

        if (username.isEmpty()) {
            ((EditText)findViewById(R.id.usernameEditText)).setError("Username is required");
            isValid = false;
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ((EditText)findViewById(R.id.emailEditText)).setError("Valid email is required");
            isValid = false;
        }

        if (phoneNo.isEmpty()) {
            ((EditText)findViewById(R.id.phoneEditText)).setError("Phone number is required");
            isValid = false;
        }

        if (password.isEmpty()) {
            ((EditText)findViewById(R.id.passwordEditText)).setError("Password is required");
            isValid = false;
        }
        if (isValid)
        {
            progressIndicator.show();
            Student student=new Student(fullName,username,studentClass,level,email,phoneNo,password);
            RetrofitClientFactoryService client= RetrofitClientFactoryService.getInstance();
            if(client==null)
            {
                progressIndicator.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Connect to server First",Toast.LENGTH_LONG).show();
                return false;
            }
            ApiEndpointsService service= client.getApi();
            Call<ApiResponse<String>> call=service.studentRegister(student);

            call.enqueue(new Callback<ApiResponse<String>>() {
                @Override
                public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                    if (response.isSuccessful()) {
                        ApiResponse<String> response1 = response.body();
                        progressIndicator.setVisibility(View.GONE);
                        Toast.makeText(StudentRegistrationActivity.this,"Student Registration Successfull",Toast.LENGTH_SHORT).show();
                    } else {
                        ApiError error = client.convertError(response.errorBody());
                        progressIndicator.setVisibility(View.GONE);
                        Toast.makeText(StudentRegistrationActivity.this,error.getError(),Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                    progressIndicator.setVisibility(View.GONE);
                    Toast.makeText(StudentRegistrationActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            return false;
        }
        return true;
    }
}