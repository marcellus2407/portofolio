package com.favorite.movie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "KEL3.db";
    private static final int DATABASE_VERSION = 1;

    //table user
    public static final String TABLE_USERS = "user_table";
    public static final String ID_USER = "IDUSER";
    public static final String EMAIL_USER = "EMAIL";
    public static final String USERNAME_USER  = "USERNAME";
    public static final String PASSWORD_USER  = "PASSWORD";

    //table movie
    public static final String TABLE_MOVIE = "movie_table";
    public static final String ID_MOVIE = "IDMOVIE";
    public static final String TITLE_MOVIE = "TITLE";
    public static final String IMAGE_MOVIE = "IMAGE";
    public static final String VOTE_MOVIE  = "VOTEMOVIE";
    public static final String DESC_MOVIE  = "DESCRIPTION";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_USERS+" ( "+
                ID_USER+" integer primary key autoincrement," +
                EMAIL_USER+" text," +
                USERNAME_USER+" text," +
                PASSWORD_USER+" text);");
        db.execSQL("create table "+TABLE_MOVIE+" ( "+
                ID_MOVIE+" integer primary key autoincrement," +
                ID_USER+" integer," +
                TITLE_MOVIE+" text," +
                IMAGE_MOVIE+" text," +
                VOTE_MOVIE+" real," +
                DESC_MOVIE+" text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_MOVIE);
        onCreate(db);
    }

    public boolean insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMAIL_USER,user.email);
        contentValues.put(USERNAME_USER,user.username);
        contentValues.put(PASSWORD_USER,user.password);
        long check = db.insert(TABLE_USERS, null, contentValues);
        if(check!=-1){
            return true;
        }else{
            return false;
        }
    }

    public boolean insertMovie(String title, String image,double vote, String desc, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_USER,id);
        contentValues.put(TITLE_MOVIE,title);
        contentValues.put(IMAGE_MOVIE,image);
        contentValues.put(VOTE_MOVIE,vote);
        contentValues.put(DESC_MOVIE,desc);
        long check = db.insert(TABLE_MOVIE, null, contentValues);
        if(check!=-1){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.query (TABLE_USERS,
                new String[]{EMAIL_USER, USERNAME_USER, PASSWORD_USER},
                EMAIL_USER + "=?",
                new String[]{email},//Where clause
                null, null, null);
        if (cursor.getCount () > 0){
            return true;
        }else{
            return false;
        }
    }

    public User Authenticate(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.query (TABLE_USERS,
                new String[]{ID_USER, EMAIL_USER, USERNAME_USER, PASSWORD_USER},
                EMAIL_USER + "=?",
                new String[]{email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst () && cursor.getCount () > 0) {
            User user1 = new User(
                    cursor.getInt (0),
                    cursor.getString (1),
                    cursor.getString (2),
                    cursor.getString (3)
            );
            if (password.equals(user1.password)) {
                return user1;
            }
        }
        return null;
    }

    public User getUser(int ID) {
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.query (TABLE_USERS,
                new String[]{ID_USER, EMAIL_USER, USERNAME_USER, PASSWORD_USER},
                ID_USER + "=?",
                new String[]{String.valueOf(ID)},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst () && cursor.getCount () > 0) {
            User user1 = new User(
                    cursor.getInt (0),
                    cursor.getString (1),
                    cursor.getString (2),
                    cursor.getString (3)
            );
            return user1;
        }
        return null;
    }

    public Cursor getallMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select rowid _id,* from "+TABLE_MOVIE+" where "+ID_USER+" = "+id, null);
        return res;
    }
    public boolean deleteMovie(int id){
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(TABLE_MOVIE, ID_MOVIE + "=" + id, null) > 0;
    }
}
