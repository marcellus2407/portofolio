package com.moviefavorite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Favorite.db";
    private static final int DATABASE_VERSION = 1;

    //table movie
    public static final String TABLE_MOVIE = "movie_table";
    public static final String ID_MOVIE = "ID";
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
        db.execSQL("create table "+TABLE_MOVIE+" ( "+
                ID_MOVIE+" integer primary key autoincrement," +
                TITLE_MOVIE+" text," +
                IMAGE_MOVIE+" text," +
                VOTE_MOVIE+" real," +
                DESC_MOVIE+" text);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_MOVIE);
        onCreate(db);
    }
    public boolean insertMovie(String title, String image,double vote, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE_MOVIE,title);
        contentValues.put(IMAGE_MOVIE,image);
        contentValues.put(VOTE_MOVIE,vote);
        contentValues.put(DESC_MOVIE,desc);
        long check = db.insert(TABLE_MOVIE, null, contentValues);
        return check != -1;
    }

    public Cursor getallMovie() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select rowid _id,* from "+TABLE_MOVIE, null);
    }
    public boolean deleteMovie(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_MOVIE, ID_MOVIE + "=" + id, null) > 0;
    }
}
