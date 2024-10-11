package com.example.abacusapplication.models;

import com.google.gson.annotations.SerializedName;

public class Admin {
        @SerializedName("fullname")
        private String fullName;

        @SerializedName("username")
        private String username;

        @SerializedName("email")
        private String email;

        // Constructor
        public Admin(String fullName, String username, String email) {
            this.fullName = fullName;
            this.username = username;
            this.email = email;
        }

        // Getters and Setters
        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
}
