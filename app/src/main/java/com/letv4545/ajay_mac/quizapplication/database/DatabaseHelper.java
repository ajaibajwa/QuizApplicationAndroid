package com.letv4545.ajay_mac.quizapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.letv4545.ajay_mac.quizapplication.database.DBQuizQuestions.*;
import com.letv4545.ajay_mac.quizapplication.database.DBQuizQuestions.QuestionsTB.*;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="dbQuiz";
    private static final int DATABASE_VERSION=1;
    private DatabaseHelper databaseHelpe;
    private SQLiteDatabase db;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        String CREATE_USERS_TABLE = "CREATE TABLE " + DBUsers.TABLE_NAME
                + "(" + DBUsers.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DBUsers.USER_EMAIL + " TEXT,"
                + DBUsers.USER_NAME + " TEXT,"
                + DBUsers.USER_PASSWORD + " TEXT,"
                + DBUsers.USER_GENDER + " TEXT,"
                + DBUsers.USER_PHONE + " INTEGER)";

        db.execSQL(CREATE_USERS_TABLE);
        final String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + QuestionsTB.TABLE_NAME
                + "(" + QuestionsTB._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + QuestionsTB.QUESTION + " TEXT,"
                + QuestionsTB.OPTION1 + " TEXT,"
                + QuestionsTB.OPTION2 + " TEXT,"
                + QuestionsTB.OPTION3 + " TEXT,"
                + QuestionsTB.OPTION4 + " TEXT,"
                + QuestionsTB.ANSWER_NO + " INTEGER,"
                + QuestionsTB.CATEGORY + " TEXT" +
                ")";
        db.execSQL(CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();

        String CREATE_QUIZ_REPORT_DATA= "CREATE TABLE " + DBQuizReportData.TABLE_NAME
                + "(" + DBQuizReportData.QUIZ_NO + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DBQuizReportData.USER_EMAIL + " TEXT,"
                + DBQuizReportData.QUIZ_START_TIME + " TEXT,"
                + DBQuizReportData.CATEGORY + " TEXT" +
                ")";
        db.execSQL(CREATE_QUIZ_REPORT_DATA);

        String CREATE_QUIZ_REPORT_TABLE = "CREATE TABLE " + DBQuizReport.TABLE_NAME
                + "(" + DBQuizReport.RQ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DBQuizReport.USER_EMAIL + " TEXT,"
                //+ DBQuizReport.QUIZ_START_TIME + " TEXT,"
                //+ DBQuizReport.QUIZ_END_TIME + " TEXT,"
                //+ DBQuizReport.CORRECT_ANSWERS_COUNT + " INTEGER,"
                //+ DBQuizReport.WRONG_ANSWERS_COUNT + " INTEGER,"
                + DBQuizReport.QUESTION + " TEXT,"
                + DBQuizReport.OPTION1 + " TEXT,"
                + DBQuizReport.OPTION2 + " TEXT,"
                + DBQuizReport.OPTION3 + " TEXT,"
                + DBQuizReport.OPTION4 + " TEXT,"
                + DBQuizReport.ANSWER_NO + " INTEGER,"
                + DBQuizReport.CATEGORY + " TEXT,"
                + DBQuizReport.USER_ANS + " INTEGER,"
                + DBQuizReport.QUIZ_NO + " INTEGER,"
                + "FOREIGN KEY(" + DBQuizReport.QUIZ_NO + ") REFERENCES " +
                DBQuizReportData.TABLE_NAME + "(" + DBQuizReportData.QUIZ_NO + ")" + "ON DELETE CASCADE" +
                ")";
        db.execSQL(CREATE_QUIZ_REPORT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE " + DBUsers.TABLE_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE " + QuestionsTB.TABLE_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE " + DBQuizReportData.TABLE_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE " + DBQuizReport.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillQuestionsTable() {
        /*QuestionAnswers q1 = new QuestionAnswers("1AG K", "A", "B", "C","D", 1, QuestionAnswers.GENERAL_KNOWLEDGE);
        addQuestion(q1);
        QuestionAnswers q2 = new QuestionAnswers("2BG K", "A", "B", "C", "D",2, QuestionAnswers.GENERAL_KNOWLEDGE);
        addQuestion(q2);
        QuestionAnswers q3 = new QuestionAnswers("3C C O", "A", "B", "C", "D",3, QuestionAnswers.COMPUTER);
        addQuestion(q3);
        QuestionAnswers q4 = new QuestionAnswers("4D M", "A", "B", "C", "D",4, QuestionAnswers.MATHS);
        addQuestion(q4);
        QuestionAnswers q5 = new QuestionAnswers("5B M", "A", "B", "C", "D",2, QuestionAnswers.MATHS);
        addQuestion(q5);*/
        QuestionAnswers q1 = new QuestionAnswers("What type of animal is Bambi?","Deer","Dog","Bear","Snake",1,QuestionAnswers.GENERAL_KNOWLEDGE);
        addQuestion(q1);
        QuestionAnswers q2 = new QuestionAnswers("Which country features a maple leaf on its flag?","India","China","Canada","USA",3,QuestionAnswers.GENERAL_KNOWLEDGE);
        addQuestion(q2);
        QuestionAnswers q3 = new QuestionAnswers("What game features the terms love, deuce, match and volley?","Cricket","Tennis","Soccer","GTA",2,QuestionAnswers.GENERAL_KNOWLEDGE);
        addQuestion(q3);
        QuestionAnswers q4 = new QuestionAnswers("Who is Winnie the Pooh's gloomy donkey friend?","Eeyore","Mickey","Jerry","Oggy",1,QuestionAnswers.GENERAL_KNOWLEDGE);
        addQuestion(q4);
        QuestionAnswers q5 = new QuestionAnswers("What is the standard unit of distance in the metric system?","Meter","Millimeter","Centimeter","Kilometer",1,QuestionAnswers.GENERAL_KNOWLEDGE);
        addQuestion(q5);
        QuestionAnswers q6 = new QuestionAnswers("What chemical element is diamond made of?","Neon","Calcium","Magnesium","Carbon",4,QuestionAnswers.GENERAL_KNOWLEDGE);
        addQuestion(q6);
        QuestionAnswers q7 = new QuestionAnswers("What part of the body produces insulin?","Pancreas","Kidney","Mitochondria","Lungs",1,QuestionAnswers.GENERAL_KNOWLEDGE);
        addQuestion(q7);
        QuestionAnswers q8 = new QuestionAnswers("Which month is Black History Month in USA?","June","December","February","August",3,QuestionAnswers.GENERAL_KNOWLEDGE);
        addQuestion(q8);
        QuestionAnswers q9 = new QuestionAnswers("In what country were the 2014 Winter Olympics held in the town of Sochi?","Spain","Russia","China","Turkey",2,QuestionAnswers.GENERAL_KNOWLEDGE);
        addQuestion(q9);
        QuestionAnswers q10 = new QuestionAnswers("Which planet did Superman come from?","Uranus","Mars","Krypton","Pluto",3,QuestionAnswers.GENERAL_KNOWLEDGE);
        addQuestion(q10);
        QuestionAnswers q11 = new QuestionAnswers("Who was the Greek or Roman God of War?","Ares","Mars","Ares,Mars","None of the above",3,QuestionAnswers.GENERAL_KNOWLEDGE);
        addQuestion(q11);
        QuestionAnswers q12 = new QuestionAnswers("What is the official language of Brazil?","Portuguese","French","Mandarin","English",1,QuestionAnswers.GENERAL_KNOWLEDGE);
        addQuestion(q12);
        QuestionAnswers q13 = new QuestionAnswers("How many syllables make up a haiku?","13","14","19","17",4,QuestionAnswers.GENERAL_KNOWLEDGE);
        addQuestion(q13);
        QuestionAnswers q14 = new QuestionAnswers("What Shakespeare play features a brooding Danish prince and his girlfriend Ophelia?","Hamlet","Othello","Macbeth","Richard III",1,QuestionAnswers.GENERAL_KNOWLEDGE);
        addQuestion(q14);
        QuestionAnswers q15 = new QuestionAnswers("Who did Matthew Perry play in 'Friends'?","Chandler Bing","Jennifer","Matt LeBlanc","Paul Rudd",1,QuestionAnswers.GENERAL_KNOWLEDGE);
        addQuestion(q15);
        QuestionAnswers q16 = new QuestionAnswers("97, 86, 73, 58, 45, (…..)","56","54","34","32",3,QuestionAnswers.MATHS);
        addQuestion(q16);
        QuestionAnswers q17 = new QuestionAnswers("612 × 612 × 612 + 321 × 321 × 321 612 × 612 − 612 × 321 + 321 × 321 = ?","842","933","712","1000",2,QuestionAnswers.MATHS);
        addQuestion(q17);
        QuestionAnswers q18 = new QuestionAnswers("What smallest number should be added to 8444 such that the sum is completely divisible by 7 ?","3","5","6","4",2,QuestionAnswers.MATHS);
        addQuestion(q18);
        QuestionAnswers q19 = new QuestionAnswers("Which one of the following numbers is completely divisible by 99?","115929","933","115939","115919",1,QuestionAnswers.MATHS);
        addQuestion(q19);
        QuestionAnswers q20 = new QuestionAnswers("( 232 + 323 ) 2 − ( 232 − 323 ) 2 232 × 323 = ?","3424","4","8","2344",2,QuestionAnswers.MATHS);
        addQuestion(q20);
        QuestionAnswers q21 = new QuestionAnswers("The number 1242*2 is completely divisible by 3. What is the smallest number in place of * ?","3","2","1","0",1,QuestionAnswers.MATHS);
        addQuestion(q21);
        QuestionAnswers q22 = new QuestionAnswers("A person croses a 600 m long street in 5 minutes. What is his speed in km per hour?","8.4","7.2","10","3.6",2,QuestionAnswers.MATHS);
        addQuestion(q22);
        QuestionAnswers q23 = new QuestionAnswers("January 1, 2007 was Monday. What day of the week lies on Jan. 1, 2008?","Monday","Tuesday","Wednesday","Sunday",2,QuestionAnswers.MATHS);
        addQuestion(q23);
        QuestionAnswers q24 = new QuestionAnswers("The calendar for the year 2007 was same for the year:","2014","2017","2018","2016",3,QuestionAnswers.MATHS);
        addQuestion(q24);
        QuestionAnswers q25 = new QuestionAnswers("The last day of a century cannot be","Wednesday","Monday","Friday","Tuesday",4,QuestionAnswers.MATHS);
        addQuestion(q25);
        QuestionAnswers q26 = new QuestionAnswers("Which of the following is not a leap year?","2000","700","800","1200",2,QuestionAnswers.MATHS);
        addQuestion(q26);
        QuestionAnswers q27 = new QuestionAnswers("If A = {0,2) and B = {1,3), then Cartesian product ?","AxB not equal BxA","AxB = BxA","is not possible","None of the above",1,QuestionAnswers.MATHS);
        addQuestion(q27);
        QuestionAnswers q28 = new QuestionAnswers("How many rational and irrational numbers are possible between 0 and 1 ?","Finite","Infifnite","0","1",2,QuestionAnswers.MATHS);
        addQuestion(q28);
        QuestionAnswers q29 = new QuestionAnswers("The intersection of sets A and B is expressed as ?","AxB","AnB","A-B","A/B",2,QuestionAnswers.MATHS);
        addQuestion(q29);
        QuestionAnswers q30 = new QuestionAnswers("In 4th quadrant ?","X > 0, Y > 0","X < 0, Y < 0","X > 0, Y < 0","X < 0, Y > 0",3,QuestionAnswers.MATHS);
        addQuestion(q30);
        QuestionAnswers q31 = new QuestionAnswers("Computer Moniter is also known as :","VDU","DVU","UVD","CCTV",1,QuestionAnswers.COMPUTER);
        addQuestion(q31);
        QuestionAnswers q32 = new QuestionAnswers("Free of cost repair of software bug available at Internet is called ….","Version","Ad-on","Tutorial","Patch",4,QuestionAnswers.COMPUTER);
        addQuestion(q32);
        QuestionAnswers q33 = new QuestionAnswers("The Internet was originally a project of which agency?","ARPA","NSF","NSA","None of these",1,QuestionAnswers.COMPUTER);
        addQuestion(q33);
        QuestionAnswers q34 = new QuestionAnswers("Which one of these stores more data than a DVD ?","CD ROM","Floppy","Blu Ray Disk","Red Ray Disk",3,QuestionAnswers.COMPUTER);
        addQuestion(q34);
        QuestionAnswers q35 = new QuestionAnswers("HTML is used to create","machine language program","high level program","web page","web server",3,QuestionAnswers.COMPUTER);
        addQuestion(q35);
        QuestionAnswers q36 = new QuestionAnswers("The computer jargon - WWWW, stands for :","World Wide Web Worm","World Wide Wildlife Web","World Wide Women's Web","World Wide Women's Week",1,QuestionAnswers.COMPUTER);
        addQuestion(q36);
        QuestionAnswers q37 = new QuestionAnswers("Every computer connected to the Internet is identified by a unique four-part string, known as","IP Address","Host Name","Domain Name","None",1,QuestionAnswers.COMPUTER);
        addQuestion(q37);
        QuestionAnswers q38 = new QuestionAnswers("A network of computers and other devices that is confined to a relatively small space is called?","Wide Area Network","Local Area Network","Global Network","Peer-to-Peer Network",2,QuestionAnswers.COMPUTER);
        addQuestion(q38);
        QuestionAnswers q39 = new QuestionAnswers("USB is a device used to store data and it stands for","Universal Serial Bus","U Serial Bus","Uni Serial Bus","Universal Service Bus",1,QuestionAnswers.COMPUTER);
        addQuestion(q39);
        QuestionAnswers q40 = new QuestionAnswers("Which of the following memories must be refreshed many times per second?","Static RAM","Dynamic RAM","EPROM","Peer-to-Peer ROM",2,QuestionAnswers.COMPUTER);
        addQuestion(q40);
        QuestionAnswers q41 = new QuestionAnswers("PNG refers to","Audio File","Image file","Movie/animation file","MS Office document",2,QuestionAnswers.COMPUTER);
        addQuestion(q41);

    }

    private void addQuestion(QuestionAnswers question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTB.QUESTION, question.getQuestion());
        cv.put(QuestionsTB.OPTION1, question.getOption1());
        cv.put(QuestionsTB.OPTION2, question.getOption2());
        cv.put(QuestionsTB.OPTION3, question.getOption3());
        cv.put(QuestionsTB.OPTION4,question.getOption4());
        cv.put(QuestionsTB.ANSWER_NO, question.getAnswerNo());
        cv.put(QuestionsTB.CATEGORY,question.getCategory());
        db.insert(QuestionsTB.TABLE_NAME, null, cv);
    }
    public ArrayList<QuestionAnswers> getAllQuestions() {
        ArrayList<QuestionAnswers> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTB.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                QuestionAnswers question = new QuestionAnswers();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTB.QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTB.OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTB.OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTB.OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTB.OPTION4)));
                question.setAnswerNo(c.getInt(c.getColumnIndex(QuestionsTB.ANSWER_NO)));
                question.setCategory(c.getString(c.getColumnIndex(QuestionsTB.CATEGORY)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
    public ArrayList<QuestionAnswers> getQuestions(String category) {
        ArrayList<QuestionAnswers> questionList = new ArrayList<>();
        db = getReadableDatabase();
        String[] categorySel=new String[]{category};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTB.TABLE_NAME + " WHERE " + QuestionsTB.CATEGORY + " = ?", categorySel);

        if (c.moveToFirst()) {
            do {
                QuestionAnswers question = new QuestionAnswers();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTB.QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTB.OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTB.OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTB.OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTB.OPTION4)));
                question.setAnswerNo(c.getInt(c.getColumnIndex(QuestionsTB.ANSWER_NO)));
                question.setCategory(c.getString(c.getColumnIndex(QuestionsTB.CATEGORY)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
