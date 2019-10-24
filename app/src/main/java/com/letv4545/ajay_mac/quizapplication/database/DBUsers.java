package com.letv4545.ajay_mac.quizapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBUsers {
    public static final String TABLE_NAME = "tblUsers";
    public static final String USER_ID = "uId";
    public static final String USER_EMAIL = "uName";
    public static final String USER_NAME = "uEmail";
    public static final String USER_PASSWORD = "uPassword";
    public static final String USER_GENDER = "uGender";
    public static final String USER_PHONE = "uPhone";

    private DatabaseHelper databaseHelper;

    public DBUsers(Context context)
    {
        databaseHelper=new DatabaseHelper(context);
    }
    public boolean add(Users user){
        SQLiteDatabase db=databaseHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(USER_EMAIL,user.getUserEmail());
        contentValues.put(USER_NAME,user.getUserName());
        contentValues.put(USER_PASSWORD,user.getUserPassword());
        contentValues.put(USER_GENDER,user.getUserGender());
        contentValues.put(USER_PHONE,user.getUserPhone());

        long result=db.insert(TABLE_NAME,null,contentValues);
        db.close();

        if(result == -1) {
            return false;
        } else {
            return true;
        }


    }
    public void update(Users user){
        SQLiteDatabase db=databaseHelper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(USER_EMAIL,user.getUserEmail());
        contentValues.put(USER_NAME,user.getUserName());
        contentValues.put(USER_PASSWORD,user.getUserPassword());
        contentValues.put(USER_GENDER,user.getUserGender());
        contentValues.put(USER_PHONE,user.getUserPhone());

        db.update(TABLE_NAME,contentValues,USER_EMAIL + "=?",
                new String[]
                        {
                                String.valueOf(user.getUserEmail())
                        });
        db.close();
    }
    public ArrayList<Users> getAllUser()
    {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        //Cursor mCursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<Users> users = new ArrayList<>();
        if(mCursor != null)
        {
            if(mCursor.getCount() != 0)
            {
                mCursor.moveToFirst();
                while (!mCursor.isAfterLast())
                {
                    /*String email=mCursor.getString(1);
                    String name=mCursor.getString(2);
                    String password=mCursor.getString(3);
                    String gender=mCursor.getString(4);
                    String phone=mCursor.getString(5);*/
                    Users users1=new Users();
                    users1.setUserEmail(mCursor.getString(1));
                    users1.setUserName(mCursor.getString(2));
                    users1.setUserPassword(mCursor.getString(3));
                    users1.setUserGender(mCursor.getString(4));
                    users1.setUserPhone(mCursor.getString(5));

                    users.add(users1);

                    mCursor.moveToNext();
                }
            }
        }

        db.close();
        return users;
    }
    public boolean checkUser(String email,String pass){
        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        String[] columns = {
                USER_ID
        };
        String selection = USER_EMAIL + " = ?" + " AND " + USER_PASSWORD + " =?";
        String[] selectionArgs = { email, pass };

        Cursor cursor = db.query(TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        else {
            return false;
        }
    }

    public Users getUserDetails(String email,String pass){
        Users users=new Users();
        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USER_EMAIL + "=? AND " + USER_PASSWORD + "=?", new String[]{email, pass});
        /*String[] columns = {
                USER_ID
        };
        String selection = USER_EMAIL + " = ?" + " AND " + USER_PASSWORD + " =?";
        String[] selectionArgs = { email, pass };

        Cursor cursor = db.query(TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);*/
        if (cursor.moveToNext()){

            users.setUserEmail(cursor.getString(1));
            users.setUserName(cursor.getString(2));
            users.setUserPassword(cursor.getString(3));
            users.setUserGender(cursor.getString(4));
            users.setUserPhone(cursor.getString(5));
        }
        //int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        /*if (cursorCount > 0){
            return true;
        }
        return false;*/
        return users;
    }
}
