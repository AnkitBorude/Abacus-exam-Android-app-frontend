package com.example.abacusapplication.models;

import com.google.gson.annotations.SerializedName;

public class StudentresultInfo {
    @SerializedName("_id")
    private String id;

    @SerializedName("student_id")
    private String studentId;

    @SerializedName("student_name")
    private String studentName;

    @SerializedName("student_email")
    private String studentEmail;

    @SerializedName("student_level")
    private String studentLevel;

    @SerializedName("student_class")
    private String studentClass;

    @SerializedName("student_phoneno")
    private String studentPhoneNo;

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentLevel() {
        return studentLevel;
    }

    public void setStudentLevel(String studentLevel) {
        this.studentLevel = studentLevel;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentPhoneNo() {
        return studentPhoneNo;
    }

    public void setStudentPhoneNo(String studentPhoneNo) {
        this.studentPhoneNo = studentPhoneNo;
    }
}
