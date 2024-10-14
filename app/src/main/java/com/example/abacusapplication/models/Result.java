package com.example.abacusapplication.models;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("score")
    private int score;

    @SerializedName("exam")
    private String exam;

    @SerializedName("date_completed")
    private String dateCompleted;

    @SerializedName("total_correct")
    private int totalCorrect;

    @SerializedName("time_taken")
    private int timeTaken;

    // Getters and Setters

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public int getTotalCorrect() {
        return totalCorrect;
    }

    public void setTotalCorrect(int totalCorrect) {
        this.totalCorrect = totalCorrect;
    }

    public int getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }
}
