package com.example.abacusapplication.models;

public class CreatedBy {
    private String fullname;

    public CreatedBy(String fullname) {
        this.fullname=fullname;
    }

    // Getter and Setter methods
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
