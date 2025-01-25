package com.example.abacusapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.abacusapplication.services.ApiEndpointsService;
import com.example.abacusapplication.services.RetrofitClientFactoryService;
import com.example.abacusapplication.models.Admin;
import com.example.abacusapplication.models.ApiError;
import com.example.abacusapplication.models.ApiResponse;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        RetrofitClientFactoryService client= RetrofitClientFactoryService.getInstance();
        ApiEndpointsService apiEndpointsService = client.getApi();
        CircularProgressIndicator progressIndicator = findViewById(R.id.progress_circular);
        TextView fullname=findViewById(R.id.fullNameTextView);
        TextView email=findViewById(R.id.emailTextView);
        TextView username=findViewById(R.id.usernameTextView);
        Call<ApiResponse<Admin>> call = apiEndpointsService.getAdmin();
        call.enqueue(new Callback<ApiResponse<Admin>>() {
            @Override
            public void onResponse(Call<ApiResponse<Admin>> call, Response<ApiResponse<Admin>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<Admin> response1 = response.body();
                    email.setText(response1.getData().getEmail());
                    username.setText(response1.getData().getUsername());
                    fullname.setText(response1.getData().getFullName());
                    progressIndicator.setVisibility(View.GONE);

                } else {
                    ApiError error = client.convertError(response.errorBody());
                    progressIndicator.hide();
                    Toast.makeText(getBaseContext(),error.getError(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<Admin>> call, Throwable t) {
                progressIndicator.setVisibility(View.GONE);
                Toast.makeText(getBaseContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}