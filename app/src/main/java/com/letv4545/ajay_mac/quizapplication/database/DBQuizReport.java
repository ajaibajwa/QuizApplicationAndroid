package com.letv4545.ajay_mac.quizapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


public class DBQuizReport {
    //public static class quizReportData{
//
    //}
    public static final String TABLE_NAME = "tblQuizReport";
    public static final String USER_EMAIL= "user_email";
    public static final String QUIZ_NO="quiz_no";
    public static final String QUIZ_START_TIME="quiz_start_time";
    public static final String QUIZ_END_TIME="quiz_end_Time";
    public static final String CORRECT_ANSWERS_COUNT="correct_answers_count";
    public static final String WRONG_ANSWERS_COUNT="wrong_answers_count";
    public static final String RQ_ID="report_question_Id";
    public static final String QUESTION="question";
    public static final String OPTION1="option1";
    public static final String OPTION2="option2";
    public static final String OPTION3="option3";
    public static final String OPTION4="option4";
    public static final String ANSWER_NO="answer_no";
    public static final String CATEGORY="category";
    public static final String USER_ANS="user_ans";

    private DatabaseHelper databaseHelper;

    public DBQuizReport(Context context)
    {
        databaseHelper=new DatabaseHelper(context);
    }

    public boolean addReport(QuizReport quizReport){
        SQLiteDatabase db=databaseHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(USER_EMAIL,quizReport.getUserEmail());
        //contentValues.put(QUIZ_START_TIME,quizReport.getQuizStartTime());
        //contentValues.put(QUIZ_END_TIME,quizReport.getQuizEndTime());
        //ontentValues.put(CORRECT_ANSWERS_COUNT,quizReport.getCorrectAnswersCount());
        //contentValues.put(WRONG_ANSWERS_COUNT,quizReport.getWrongAnswersCount());
        contentValues.put(QUESTION,quizReport.getQuestion());
        contentValues.put(OPTION1,quizReport.getOption1());
        contentValues.put(OPTION2,quizReport.getOption2());
        contentValues.put(OPTION3,quizReport.getOption3());
        contentValues.put(OPTION4,quizReport.getOption4());
        contentValues.put(ANSWER_NO,quizReport.getAnswerNo());
        contentValues.put(CATEGORY,quizReport.getCategory());
        contentValues.put(USER_ANS,quizReport.getUserAns());
        contentValues.put(QUIZ_NO,quizReport.getQuizNo());

        long result=db.insert(TABLE_NAME,null,contentValues);
        db.close();

        if(result == -1) {
            return false;
        } else {
            return true;
        }


    }
    public ArrayList<QuizReport> getQuizReport(int quizNo,String userEmail) {
        ArrayList<QuizReport> quizReportList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String selection=DBQuizReport.QUIZ_NO + " =? " +
                " AND " + DBQuizReport.USER_EMAIL + " =? ";
        String[] selectionArgs=new String[]{String.valueOf(quizNo),userEmail};
        //Cursor c = db.rawQuery("SELECT * FROM " + DBQuizQuestions.QuestionsTB.TABLE_NAME + " WHERE " + DBQuizQuestions.QuestionsTB.CATEGORY + " = ?", categorySel);

        Cursor c=db.query(
                DBQuizReport.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            do {
                QuizReport quizReport = new QuizReport();
                quizReport.setUserEmail(c.getString(1));
                quizReport.setQuestion(c.getString(2));
                quizReport.setOption1(c.getString(3));
                quizReport.setOption2(c.getString(4));
                quizReport.setOption3(c.getString(5));
                quizReport.setOption4(c.getString(6));
                quizReport.setAnswerNo(c.getInt(7));
                quizReport.setCategory(c.getString(8));
                quizReport.setUserAns(c.getInt(9));
                quizReport.setQuizNo(c.getInt(10));
                quizReportList.add(quizReport);

                /*String uEmail=c.getString(1);
                String ques=c.getString(2);
                String op1=c.getString(3);
                String op2=c.getString(4);
                String op3=c.getString(5);
                String op4=c.getString(6);
                int ans=c.getInt(7);
                String cat=c.getString(8);
                int uans=c.getInt(9);
                int qno=c.getInt(10);
                QuizReport quizReport=new QuizReport(uEmail,ques,op1,op2,op3,op4,ans,cat,uans,qno);
                quizReportList.add(quizReport);*/
            } while (c.moveToNext());
        }

        c.close();
        return quizReportList;
    }
}
