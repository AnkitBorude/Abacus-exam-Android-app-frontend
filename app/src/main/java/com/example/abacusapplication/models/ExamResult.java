package com.example.abacusapplication.models;

import com.google.gson.annotations.SerializedName;

public class ExamResult {

        @SerializedName("result_id")
        private String id;

        @SerializedName("score")
        private int score;

        @SerializedName("time_taken")
        private int timeTaken;

        @SerializedName("total_correct")
        private int totalCorrect;

        @SerializedName("date_completed")
        private String dateCompleted;

        @SerializedName("exam_name")
        private String examName;

        @SerializedName("exam_duration")
        private int examDuration;

        @SerializedName("exam_level")
        private String examLevel;

        @SerializedName("exam_total_question")
        private int examTotalQuestion;

        @SerializedName("exam_marks")
        private int examMarks;

        // Getters and setters

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getTimeTaken() {
            return timeTaken;
        }

        public void setTimeTaken(int timeTaken) {
            this.timeTaken = timeTaken;
        }

        public int getTotalCorrect() {
            return totalCorrect;
        }

        public void setTotalCorrect(int totalCorrect) {
            this.totalCorrect = totalCorrect;
        }

        public String getDateCompleted() {
            return dateCompleted;
        }

        public void setDateCompleted(String dateCompleted) {
            this.dateCompleted = dateCompleted;
        }

        public String getExamName() {
            return examName;
        }

        public void setExamName(String examName) {
            this.examName = examName;
        }

        public int getExamDuration() {
            return examDuration;
        }

        public void setExamDuration(int examDuration) {
            this.examDuration = examDuration;
        }

        public String getExamLevel() {
            return examLevel;
        }

        public void setExamLevel(String examLevel) {
            this.examLevel = examLevel;
        }

        public int getExamTotalQuestion() {
            return examTotalQuestion;
        }

        public void setExamTotalQuestion(int examTotalQuestion) {
            this.examTotalQuestion = examTotalQuestion;
        }

        public int getExamMarks() {
            return examMarks;
        }

        public void setExamMarks(int examMarks) {
            this.examMarks = examMarks;
        }
}
