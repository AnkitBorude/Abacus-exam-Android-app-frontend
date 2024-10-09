package com.example.abacusapplication.models;

public class ApiResponse {
    private String data;
    private int statusCode;
    private boolean success;

    // Getters
    public String getData() {
        return data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    // Setters
    public void setData(String data) {
        this.data = data;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
