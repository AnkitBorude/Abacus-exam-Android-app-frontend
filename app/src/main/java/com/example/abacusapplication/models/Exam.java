package com.example.abacusapplication.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Exam{
    private String title;
    private int duration;
    private String level;
    private int total_questions;
    private int total_marks;
    private int total_marks_per_question;
    private boolean is_active;
    private CreatedBy created_by;
    private String createdAt;
    private String id;

    @SerializedName("total_attended")
    private int totalAttended;

    @SerializedName("highest_score")
    private int highestScore;

    @SerializedName("unique_students")
    private int uniqueStudents;

    public HasAttempted getHasAttempted() {
        return hasAttempted;
    }

    public void setHasAttempted(HasAttempted hasAttempted) {
        this.hasAttempted = hasAttempted;
    }

    @SerializedName("hasAttempted")
    private HasAttempted hasAttempted;


    public boolean isSingleAttempt() {
        return isSingleAttempt;
    }

    public void setSingleAttempt(boolean singleAttempt) {
        isSingleAttempt = singleAttempt;
    }

    @SerializedName("isSingleAttempt")
    private boolean isSingleAttempt;

    public int getTotalAttempted() {
        return totalAttempted;
    }

    @SerializedName("totalAttempted")
    private int totalAttempted;

    // Getter and Setter methods

    public Exam(String title, int duration, String level, int total_questions, int total_marks, int total_marks_per_question, boolean is_active, CreatedBy created_by, String createdAt, String id) {
        this.title = title;
        this.duration = duration;
        this.level = level;
        this.total_questions = total_questions;
        this.total_marks = total_marks;
        this.total_marks_per_question = total_marks_per_question;
        this.is_active = is_active;
        this.created_by = created_by;
        this.createdAt = createdAt;
        this.id = id;
        this.isSingleAttempt=false;//setting default to false might change letter
    }

    public static class HasAttempted {
        public boolean isAttempted() {
            return attempted;
        }

        public void setAttempted(boolean attempted) {
            this.attempted = attempted;
        }

        @SerializedName("attempted")
        private boolean attempted;

        public String getAttemptDate() {
            return attemptDate;
        }

        public void setAttemptDate(String attemptDate) {
            this.attemptDate = attemptDate;
        }

        @SerializedName("attempt_date")
        private String attemptDate;

        // Getters and Setters
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getTotal_questions() {
        return total_questions;
    }

    public void setTotal_questions(int total_questions) {
        this.total_questions = total_questions;
    }

    public int getTotal_marks() {
        return total_marks;
    }

    public void setTotal_marks(int total_marks) {
        this.total_marks = total_marks;
    }

    public int getTotal_marks_per_question() {
        return total_marks_per_question;
    }

    public void setTotal_marks_per_question(int total_marks_per_question) {
        this.total_marks_per_question = total_marks_per_question;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public CreatedBy getCreated_by() {
        return created_by;
    }

    public void setCreated_by(CreatedBy created_by) {
        this.created_by = created_by;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public int getTotalAttended() {
        return this.totalAttended;
    }
    public Integer getHighestScore() {
        return this.highestScore;
    }
    public int getUniqueStudents() {
        return this.uniqueStudents;
    }
}
