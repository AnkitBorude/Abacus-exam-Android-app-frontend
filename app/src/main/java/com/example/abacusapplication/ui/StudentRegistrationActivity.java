package com.example.abacusapplication.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.abacusapplication.R;

public class StudentRegistrationActivity extends AppCompatActivity {

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

        Button registerbtn=findViewById(R.id.registerButton);
        Button backtologinbtn=findViewById(R.id.backToLoginButton);

        registerbtn.setOnClickListener(view->{
            if(validateForm())
            {
                Toast.makeText(StudentRegistrationActivity.this,"Valid data format",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(StudentRegistrationActivity.this,"Invalid format",Toast.LENGTH_SHORT).show();
            }

        });
    }

    private boolean validateForm() {
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
        return isValid;
    }

}