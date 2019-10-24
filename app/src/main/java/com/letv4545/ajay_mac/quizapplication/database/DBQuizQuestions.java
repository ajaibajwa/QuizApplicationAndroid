package com.letv4545.ajay_mac.quizapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class DBQuizQuestions {
    public static class QuestionsTB implements BaseColumns {

        public static final String TABLE_NAME ="quiz_questions";
        public static final String QUESTION="question";
        public static final String OPTION1="option1";
        public static final String OPTION2="option2";
        public static final String OPTION3="option3";
        public static final String OPTION4="option4";
        public static final String ANSWER_NO="answer_no";
        public static final String CATEGORY="category";

        private DatabaseHelper databaseHelper;

        public QuestionsTB(Context context)
        {
            databaseHelper=new DatabaseHelper(context);
        }


    }
}
