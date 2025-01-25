package com.example.abacusapplication.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class JwtTokenManagerService {
    private static String cachedToken;
    private static JwtTokenManagerService instance;
    private SharedPreferences prefs;

    private JwtTokenManagerService(Context context) {
        prefs = context.getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE);
        prefs.edit().remove("jwt_token").apply();
        cachedToken = prefs.getString("jwt_token", null);
    }
    public static synchronized JwtTokenManagerService getInstance(Context context) {
        if (instance == null) {
            instance = new JwtTokenManagerService(context);
        }
        return instance;
    }

    public String getToken() {
        return cachedToken;
    }

    public void setToken(String token) {
        cachedToken = null;  // Clear the cached token
        prefs.edit().remove("jwt_token").apply();
        cachedToken = token;
        Log.d("Token","Token Updated");
        prefs.edit().putString("jwt_token", token).apply();
    }
}
