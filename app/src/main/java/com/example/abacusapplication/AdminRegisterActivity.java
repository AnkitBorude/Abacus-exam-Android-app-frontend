package com.example.abacusapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.abacusapplication.data.ApiService;
import com.example.abacusapplication.data.RetrofitClient;
import com.example.abacusapplication.models.Admin;
import com.example.abacusapplication.models.ApiError;
import com.example.abacusapplication.models.ApiResponse;
import com.example.abacusapplication.models.Student;
import com.example.abacusapplication.ui.StudentRegistrationActivity;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminRegisterActivity extends AppCompatActivity {

    CircularProgressIndicator progressIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        Button registerbtn=findViewById(R.id.registerbutton);
        Button backtologinbtn=findViewById(R.id.backToLoginButton);

        registerbtn.setOnClickListener(view->{
            if(!register()) {
                Toast.makeText(AdminRegisterActivity.this,"Please fill proper data",Toast.LENGTH_SHORT).show();
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
        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString().trim();
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

        if (password.isEmpty()) {
            ((EditText)findViewById(R.id.passwordEditText)).setError("Password is required");
            isValid = false;
        }
        if (isValid)
        {
            progressIndicator.show();
            Admin admin=new Admin(fullName,username,email,password);

            RetrofitClient client=RetrofitClient.getInstance();
            if(client==null)
            {
                progressIndicator.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Connect to server First",Toast.LENGTH_LONG).show();
                return false;
            }
            ApiService service= client.getApi();
            Call<ApiResponse<String>> call=service.adminRegister(admin);

            call.enqueue(new Callback<ApiResponse<String>>() {
                @Override
                public void onResponse(Call<ApiResponse<String>> call, Response<ApiResponse<String>> response) {
                    if (response.isSuccessful()) {
                        ApiResponse<String> response1 = response.body();
                        progressIndicator.setVisibility(View.GONE);
                        Toast.makeText(AdminRegisterActivity.this,"Admin Registration Successfull..",Toast.LENGTH_SHORT).show();
                    } else {
                        ApiError error = client.convertError(response.errorBody());
                        progressIndicator.setVisibility(View.GONE);
                        Toast.makeText(AdminRegisterActivity.this,error.getError(),Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                    progressIndicator.setVisibility(View.GONE);
                    Toast.makeText(AdminRegisterActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            return false;
        }
        return true;
    }
}