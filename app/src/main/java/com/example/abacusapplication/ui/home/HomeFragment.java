package com.example.abacusapplication.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.abacusapplication.MainActivity;
import com.example.abacusapplication.R;
import com.example.abacusapplication.data.ApiService;
import com.example.abacusapplication.data.RetrofitClient;
import com.example.abacusapplication.models.ApiError;
import com.example.abacusapplication.models.LoginRequest;
import com.example.abacusapplication.models.LoginResponse;
import com.google.android.material.progressindicator.BaseProgressIndicator;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button loginbutton =view.findViewById(R.id.loginbutton);
        EditText ip=view.findViewById(R.id.serverip);
        EditText username=view.findViewById(R.id.studentusername);
        EditText password=view.findViewById(R.id.studentpassword);
        ip.setText("192.168.0.0:3000");
        CircularProgressIndicator progressIndicator = view.findViewById(R.id.progress_circular);

        loginbutton.setOnClickListener(v->{
            progressIndicator.show();
            String ipText = ip.getText().toString();
            String usernameText = username.getText().toString();
            String passwordText = password.getText().toString();

            RetrofitClient client=RetrofitClient.getInstance(ipText);
            ApiService apiService = client.getApi();
            Call<LoginResponse> call =apiService.studentLogin(new LoginRequest(usernameText,passwordText));
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        LoginResponse response1 = response.body();
                        progressIndicator.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"Login Sucessfull",Toast.LENGTH_LONG).show();
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
            // Combine the text into a single string
            String combinedText = "IP: " + ipText + ", Username: " + usernameText + ", Password: " + passwordText;

            Toast.makeText(requireContext(), combinedText , Toast.LENGTH_SHORT).show();
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}