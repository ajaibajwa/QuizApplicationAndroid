package com.letv4545.ajay_mac.quizapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBQuizReportData {
    public static final String TABLE_NAME = "tblQuizReportData";
    public static final String QUIZ_NO="quiz_no";
    public static final String USER_EMAIL="userEmail";
    public static final String QUIZ_START_TIME="quiz_start_time";
    public static final String CATEGORY="category";

    private DatabaseHelper databaseHelper;

    public DBQuizReportData(Context context)
    {
        databaseHelper=new DatabaseHelper(context);
    }

    public boolean addReportData(QuizReportData quizReportData){
        SQLiteDatabase db=databaseHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        //contentValues.put(QUIZ_NO,quizReportData.getQuizNo());
        contentValues.put(USER_EMAIL,quizReportData.getUserEmail());
        contentValues.put(QUIZ_START_TIME,quizReportData.getQuizStartTime());
        contentValues.put(CATEGORY,quizReportData.getCategory());

        long resultt=db.insert(TABLE_NAME,null,contentValues);
        db.close();

        if(resultt == -1) {
            return false;
        } else {
            return true;
        }
    }
    public ArrayList<QuizReportData> getReportData(String userEmail){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ArrayList<QuizReportData> quizReportDataList=new ArrayList<>();
        String[] reportSel=new String[]{userEmail};
        Cursor c = db.rawQuery("SELECT * FROM " + DBQuizReportData.TABLE_NAME + " WHERE " + DBQuizReportData.USER_EMAIL + " = ?", reportSel);
        //Cursor c=db.rawQuery("SELECT * FROM " + DBQuizReportData.TABLE_NAME,null);

        if (c.moveToFirst()) {
            do {
                QuizReportData quizReportData=new QuizReportData();
                quizReportData.setQuizId(c.getInt(0));
                quizReportData.setUserEmail(c.getString(1));
                quizReportData.setQuizStartTime(c.getString(2));
                quizReportData.setCategory(c.getString(3));
                quizReportDataList.add(quizReportData);
            } while (c.moveToNext());
        }
        c.close();
        return quizReportDataList;
    }
    /*public ArrayList<QuizReport> getQuizReport(String userEmail,int quizNo) {
        ArrayList<QuizReport> quizReportList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String selection=DBQuizReport.QUIZ_NO + " =? " +
                " AND " + DBQuizReport.USER_EMAIL + " =? ";
        String[] selectionArgs=new String[]{userEmail,String.valueOf(quizNo)};
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
                //quizReport.setUserEmail(c.getString());
                quizReport.setQuestion(c.getString(2));
                quizReport.setOption1(c.getString(3));
                quizReport.setOption2(c.getString(4));
                quizReport.setOption3(c.getString(5));
                quizReport.setOption4(c.getString(6));
                quizReport.setAnswerNo(c.getInt(7));
                quizReport.setCategory(c.getString(8));
                quizReport.setUserAns(c.getInt(9));
                quizReportList.add(quizReport);
            } while (c.moveToNext());
        }

        c.close();
        return quizReportList;
    }*/
}
