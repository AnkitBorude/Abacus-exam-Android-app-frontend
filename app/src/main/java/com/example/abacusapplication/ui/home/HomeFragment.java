package com.example.abacusapplication.ui.home;

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
import androidx.lifecycle.ViewModelProvider;

import com.example.abacusapplication.MainActivity;
import com.example.abacusapplication.R;
import com.example.abacusapplication.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button loginbutton =view.findViewById(R.id.loginbutton);
        EditText ip=view.findViewById(R.id.serverip);
        EditText username=view.findViewById(R.id.studentusername);
        EditText password=view.findViewById(R.id.studentpassword);


        loginbutton.setOnClickListener(v->{
            String ipText = ip.getText().toString();
            String usernameText = username.getText().toString();
            String passwordText = password.getText().toString();

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