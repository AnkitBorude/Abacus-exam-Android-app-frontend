package com.example.abacusapplication.models;

import com.google.gson.annotations.SerializedName;

public class LoginData {
   @SerializedName("message")
    private String message;


   @SerializedName("token")
    private String token;

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
