package com.letv4545.ajay_mac.quizapplication.database;

import java.io.Serializable;

public class QuizReport implements Serializable {
    //private String quizStartTime;
    //private String quizEndTime;
    //private int correctAnswersCount;
   //private int wrongAnswersCount;
    private String userEmail;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int answerNo;
    private String category;
    private int userAns;
    private int quizNo;

    public QuizReport() {
    }

    public QuizReport(String userEmail, String question, String option1, String option2, String option3, String option4, int answerNo, String category, int userAns, int quizNo) {
        this.userEmail = userEmail;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answerNo = answerNo;
        this.category = category;
        this.userAns = userAns;
        this.quizNo = quizNo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getAnswerNo() {
        return answerNo;
    }

    public void setAnswerNo(int answerNo) {
        this.answerNo = answerNo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getUserAns() {
        return userAns;
    }

    public void setUserAns(int userAns) {
        this.userAns = userAns;
    }

    public int getQuizNo() {
        return quizNo;
    }

    public void setQuizNo(int quizNo) {
        this.quizNo = quizNo;
    }
}
