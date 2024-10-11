package com.example.abacusapplication.models;

import com.google.gson.annotations.SerializedName;

public class CreateExam {

    @SerializedName("maxTerms")
    private String maxTerms;

    @SerializedName("total_questions")
    private String totalQuestions;

    @SerializedName("operators")
    private String[] operators;

    @SerializedName("maxNumber")
    private String maxNumber;

    @SerializedName("minNumber")
    private String minNumber;

    @SerializedName("is_active")
    private boolean isActive;

    @SerializedName("total_marks_per_question")
    private int totalMarksPerQuestion;

    @SerializedName("level")
    private int level;

    @SerializedName("duration")
    private int duration;

    @SerializedName("title")
    private String title;

    // Getters and Setters

    public String getMaxTerms() {
        return maxTerms;
    }

    public void setMaxTerms(String maxTerms) {
        this.maxTerms = maxTerms;
    }

    public String getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(String totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String[] getOperators() {
        return operators;
    }

    public void setOperators(String[] operators) {
        this.operators = operators;
    }

    public String getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(String maxNumber) {
        this.maxNumber = maxNumber;
    }

    public String getMinNumber() {
        return minNumber;
    }

    public void setMinNumber(String minNumber) {
        this.minNumber = minNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getTotalMarksPerQuestion() {
        return totalMarksPerQuestion;
    }

    public void setTotalMarksPerQuestion(int totalMarksPerQuestion) {
        this.totalMarksPerQuestion = totalMarksPerQuestion;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
