package com.example.abacusapplication.models;

public class Question {
    private String question;
    private int answer;
    private int marks;
    private int option_a;
    private int option_b;
    private int option_c;
    private int option_d;

    // Constructor
    public Question(String question, int answer, int marks, int option_a, int option_b, int option_c, int option_d) {
        this.question = question;
        this.answer = answer;
        this.marks = marks;
        this.option_a = option_a;
        this.option_b = option_b;
        this.option_c = option_c;
        this.option_d = option_d;
    }

    // Getters and Setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public int getOptionA() {
        return option_a;
    }

    public void setOptionA(int option_a) {
        this.option_a = option_a;
    }

    public int getOptionB() {
        return option_b;
    }

    public void setOptionB(int option_b) {
        this.option_b = option_b;
    }

    public int getOptionC() {
        return option_c;
    }

    public void setOptionC(int option_c) {
        this.option_c = option_c;
    }

    public int getOptionD() {
        return option_d;
    }

    public void setOptionD(int option_d) {
        this.option_d = option_d;
    }
}
