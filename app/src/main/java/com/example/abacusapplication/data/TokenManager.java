package com.example.abacusapplication.data;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static String cachedToken;
    private static TokenManager instance;
    private SharedPreferences prefs;

    private TokenManager(Context context) {
        prefs = context.getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE);
        cachedToken = prefs.getString("jwt_token", null);
    }
    public static synchronized TokenManager getInstance(Context context) {
        if (instance == null) {
            instance = new TokenManager(context.getApplicationContext());
        }
        return instance;
    }

    public String getToken() {
        return cachedToken;
    }

    public void setToken(String token) {
        cachedToken = token;
        prefs.edit().putString("jwt_token", token).apply();
    }
}
