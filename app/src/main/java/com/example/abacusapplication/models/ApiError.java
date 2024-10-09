package com.example.abacusapplication.models;

    public class ApiError {
        private String error;
        private int statusCode;
        private String timestamp;
        private boolean success;

        // Getters and setters
        public String getError() { return error; }
        public void setError(String error) { this.error = error; }

        public int getStatusCode() { return statusCode; }
        public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

        public String getTimestamp() { return timestamp; }
        public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
    }