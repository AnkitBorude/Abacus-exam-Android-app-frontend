package com.example.abacusapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.abacusapplication.R;
import com.example.abacusapplication.StudentMainActivity;
import com.example.abacusapplication.services.ApiEndpointsService;
import com.example.abacusapplication.services.RetrofitClientFactoryService;
import com.example.abacusapplication.services.JwtTokenManagerService;
import com.example.abacusapplication.models.ApiError;
import com.example.abacusapplication.models.LoginRequest;
import com.example.abacusapplication.models.LoginResponse;
import com.example.abacusapplication.ui.StudentRegistrationActivity;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RetrofitClientFactoryService client;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button loginbutton =view.findViewById(R.id.loginbutton);
        EditText username=view.findViewById(R.id.studentusername);
        EditText password=view.findViewById(R.id.studentpassword);
        TextView signupbtn=view.findViewById(R.id.studentsignupbtn);
        CircularProgressIndicator progressIndicator = view.findViewById(R.id.progress_circular);
        username.setText("ankitborude");
        password.setText("ankit@123");
        loginbutton.setOnClickListener(v->{
            progressIndicator.show();
            String usernameText = username.getText().toString();
            String passwordText = password.getText().toString();

            client = RetrofitClientFactoryService.getInstance();
            if(client==null)
            {
                progressIndicator.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Connect to server First",Toast.LENGTH_LONG).show();
                return;
            }
            ApiEndpointsService apiEndpointsService = client.getApi();
            Call<LoginResponse> call = apiEndpointsService.studentLogin(new LoginRequest(usernameText,passwordText));
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse response1 = response.body();
                        JwtTokenManagerService manager= JwtTokenManagerService.getInstance(getContext());
                        manager.setToken(response1.getData().getToken());
                        progressIndicator.setVisibility(View.GONE);

                        Intent intent=new Intent(getContext(), StudentMainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getContext(),"Login Successfull Token--> "+manager.getToken(),Toast.LENGTH_SHORT).show();
                    } else {
                        ApiError error = client.convertError(response.errorBody());
                        progressIndicator.hide();
                        Toast.makeText(getContext(),error.getError(),Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    progressIndicator.setVisibility(View.GONE);
                    Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        });
        signupbtn.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), StudentRegistrationActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}