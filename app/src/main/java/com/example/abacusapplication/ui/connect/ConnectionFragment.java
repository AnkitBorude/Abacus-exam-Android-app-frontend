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
import com.example.abacusapplication.services.ApiEndpointsService;
import com.example.abacusapplication.services.RetrofitClientFactoryService;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectionFragment extends Fragment {
    boolean isConnectionFailed=false;
    RetrofitClientFactoryService client;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_connect,container,false);
        EditText ip=view.findViewById(R.id.iptext);
        Button btn=view.findViewById(R.id.connectbtn);
        ip.setText("192.168.165.104:3000");
        CircularProgressIndicator progressIndicator = view.findViewById(R.id.progress_circular);

        if(RetrofitClientFactoryService.getInstance()!=null)
        {
            btn.setBackgroundResource(R.drawable.roundbutton_green);
            btn.setText("RE-CONNECT");
        }
        btn.setOnClickListener(view1->{
        progressIndicator.show();

            client = RetrofitClientFactoryService.updateInstance(ip.getText().toString(),getContext());

            ApiEndpointsService service= client.getApi();
            Call<ApiEndpointsService.Echoreponse> call=service.echo();
            call.enqueue(new Callback<ApiEndpointsService.Echoreponse>() {
                @Override
                public void onResponse(Call<ApiEndpointsService.Echoreponse> call, Response<ApiEndpointsService.Echoreponse> response) {
                    if (response.isSuccessful()) {
                        ApiEndpointsService.Echoreponse response1 = response.body();
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
                public void onFailure(Call<ApiEndpointsService.Echoreponse> call, Throwable t) {
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
