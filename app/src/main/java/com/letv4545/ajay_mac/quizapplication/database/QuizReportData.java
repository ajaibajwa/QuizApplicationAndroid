package com.letv4545.ajay_mac.quizapplication.database;

import java.io.Serializable;

public class QuizReportData implements Serializable {
    private int quizId;
    private String userEmail;
    private String quizStartTime;
    private String category;

    public QuizReportData() {
    }

    public QuizReportData(int quizId, String userEmail, String quizStartTime, String category) {
        this.quizId = quizId;
        this.userEmail = userEmail;
        this.quizStartTime = quizStartTime;
        this.category = category;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getQuizStartTime() {
        return quizStartTime;
    }

    public void setQuizStartTime(String quizStartTime) {
        this.quizStartTime = quizStartTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return " " +quizStartTime;
    }
}
