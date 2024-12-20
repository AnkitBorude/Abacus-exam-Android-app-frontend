package com.example.abacusapplication.models;

import com.google.gson.annotations.SerializedName;

public class StudentresultInfo {
    @SerializedName("_id")
    private String id;

    @SerializedName("student_id")
    private String studentId;

    @SerializedName("fullname")
    private String studentName;

    @SerializedName("email")
    private String studentEmail;

    @SerializedName("level")
    private String studentLevel;

    @SerializedName("sclass")
    private String studentClass;

    @SerializedName("phone_no")
    private String studentPhoneNo;



    @SerializedName("is_deleted")
    private Boolean isDeleted;
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

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
    public void setStudentPhoneNo(String studentPhoneNo) {
        this.studentPhoneNo = studentPhoneNo;
    }
}
