package com.cinemacgp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CinemaCGP.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "user_table";
    public static final String ID_USER = "IDUSER";
    public static final String EMAIL_USER = "EMAIL";
    public static final String USERNAME_USER  = "USERNAME";
    public static final String PASSWORD_USER  = "PASSWORD";

    public static final String TABLE_RENTAL = "rental_table";
    public static final String ID_RENTAL = "IDRENTAL";
    public static final String ID_MOVIE = "IDMOVIE";
    public static final String LOCATION_RENTAL = "LOCATION";
    public static final String STUDIO_RENTAL = "STUDIO";
    public static final String SESSION_RENTAL = "SESSION";
    public static final String DATE_RENTAL = "DATE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_USERS+" ( "+
                ID_USER+" integer primary key autoincrement," +
                EMAIL_USER+" text," +
                USERNAME_USER+" text," +
                PASSWORD_USER+" text);");
        db.execSQL("create table "+TABLE_RENTAL+" ( "+
                ID_RENTAL+" integer primary key autoincrement," +
                ID_USER+" integer," +
                ID_MOVIE+" integer," +
                LOCATION_RENTAL+" text," +
                STUDIO_RENTAL+" text," +
                SESSION_RENTAL+" text," +
                DATE_RENTAL+" text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_RENTAL);
        onCreate(db);
    }

    public boolean register(String email, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMAIL_USER,email);
        contentValues.put(USERNAME_USER,username);
        contentValues.put(PASSWORD_USER,password);
        return db.insert(TABLE_USERS, null, contentValues)!=-1;
    }


    public boolean isEmailUnique(String email){
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.query (TABLE_USERS,
                new String[]{EMAIL_USER},
                EMAIL_USER + "=?",
                new String[]{email},
                null, null, null);
        return cursor.getCount () == 0;
    }

    public int login(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.query (TABLE_USERS,
                new String[]{ID_USER},
                EMAIL_USER + "=? AND "+PASSWORD_USER+"=?",
                new String[]{email,password},
                null, null, null);

        if(cursor.moveToFirst () && cursor.getCount () > 0){
            return  cursor.getInt (0);
        }
        return -1;
    }

    public boolean isStudioNotBooked(String location, String studio, String session, String date){
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.query (TABLE_RENTAL,
                new String[]{ID_RENTAL},
                LOCATION_RENTAL + "=? AND "+STUDIO_RENTAL+"=? AND "+SESSION_RENTAL+"=? AND "+DATE_RENTAL+"=?",
                new String[]{location,studio,session,date},
                null, null, null);
        return cursor.getCount () == 0;
    }
    public boolean rental(int idUser, int idMovie, String location, String studio, String session, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_USER,idUser);
        contentValues.put(ID_MOVIE,idMovie);
        contentValues.put(LOCATION_RENTAL,location);
        contentValues.put(STUDIO_RENTAL,studio);
        contentValues.put(SESSION_RENTAL,session);
        contentValues.put(DATE_RENTAL,date);
        return db.insert(TABLE_RENTAL, null, contentValues)!=-1;
    }
    public Cursor getRentalHistory(int idUser){
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.query (TABLE_RENTAL,
                new String[]{"rowid _id",ID_MOVIE,LOCATION_RENTAL,STUDIO_RENTAL,SESSION_RENTAL,DATE_RENTAL},
                ID_USER + "=?",
                new String[]{String.valueOf(idUser)},
                null, null, null);
    }
}
