package com.example.abacusapplication.ui.connect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.abacusapplication.R;
import com.example.abacusapplication.data.ApiService;
import com.example.abacusapplication.data.RetrofitClient;
import com.example.abacusapplication.models.ApiError;
import com.example.abacusapplication.models.LoginResponse;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectionFragment extends Fragment {
    boolean isConnectionFailed=false;
    RetrofitClient client;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_connect,container,false);
        EditText ip=view.findViewById(R.id.iptext);
        Button btn=view.findViewById(R.id.connectbtn);
        ip.setText("192.168.0.0:3000");
        CircularProgressIndicator progressIndicator = view.findViewById(R.id.progress_circular);

        if(RetrofitClient.getInstance()!=null)
        {
            btn.setBackgroundResource(R.drawable.roundbutton_green);
        }
        btn.setOnClickListener(view1->{
        progressIndicator.show();

            client = RetrofitClient.updateInstance(ip.getText().toString());

            ApiService service= client.getApi();
            Call<ApiService.Echoreponse> call=service.echo();
            call.enqueue(new Callback<ApiService.Echoreponse>() {
                @Override
                public void onResponse(Call<ApiService.Echoreponse> call, Response<ApiService.Echoreponse> response) {
                    if (response.isSuccessful()) {
                        ApiService.Echoreponse response1 = response.body();
                        progressIndicator.setVisibility(View.GONE);
                        Toast.makeText(getContext(),"Connection Successfull",Toast.LENGTH_LONG).show();
                        view1.setBackgroundResource(R.drawable.roundbutton_green);
                    } else {
                        progressIndicator.hide();
                        isConnectionFailed=true;
                        view1.setBackgroundResource(R.drawable.roundbutton_red);
                        Toast.makeText(getContext(),"Error While connecting",Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<ApiService.Echoreponse> call, Throwable t) {
                    progressIndicator.setVisibility(View.GONE);
                    isConnectionFailed=true;
                    view1.setBackgroundResource(R.drawable.roundbutton_red);
                    Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        });
        return view;
    }
}
