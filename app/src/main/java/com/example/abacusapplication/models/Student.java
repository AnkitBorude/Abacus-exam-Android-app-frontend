package com.example.abacusapplication.models;

import com.google.gson.annotations.SerializedName;

public class Student {
    @SerializedName("fullname")
    private String fullName;

    @SerializedName("username")
    private String username;

    @SerializedName("sclass")
    private String studentClass;

    @SerializedName("level")
    private String level;

    @SerializedName("email")
    private String email;

    @SerializedName("phone_no")
    private String phoneNo;

    @SerializedName("password")
    private String password;

    // Constructor
    public Student(String fullName, String username, String studentClass,
                   String level, String email, String phoneNo, String password) {
        this.fullName = fullName;
        this.username = username;
        this.studentClass = studentClass;
        this.level = level;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
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

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
