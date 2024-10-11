package com.example.abacusapplication.models;

public class LoginResponse {
        private LoginData data;
        private int statusCode;
        private boolean success;

        // Getters and setters
        public LoginData getData() { return data; }
        public void setData(LoginData data) { this.data = data; }

        public int getStatusCode() { return statusCode; }
        public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
    }
