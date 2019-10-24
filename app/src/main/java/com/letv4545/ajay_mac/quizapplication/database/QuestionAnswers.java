package com.letv4545.ajay_mac.quizapplication.database;

public class QuestionAnswers {
    public static final String GENERAL_KNOWLEDGE="General Knowledge";
    public static final String MATHS="Maths";
    public static final String COMPUTER="Computer Science";

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int answerNo;
    private String category;

    public QuestionAnswers() {
    }

    public QuestionAnswers(String question, String option1, String option2, String option3, String option4, int answerNo, String category) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answerNo = answerNo;
        this.category = category;
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
}
