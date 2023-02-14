package com.sewain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "sewain.db";
    private static final int DATABASE_VERSION = 1;

    //table item
    public static final String TABLE_ITEM = "item_table";
    public static final String ID_ITEM = "ID";
    public static final String NAME_ITEM = "NAME";
    public static final String IMG_ITEM = "IMAGE";
    public static final String LT_ITEM = "LUAS_TANAH";
    public static final String LB_ITEM = "LUAS_BANGUNAN";
    public static final String KT_ITEM = "KAMAR_TIDUR";
    public static final String KM_ITEM = "KAMAR_MANDI";
    public static final String LISTRIK_ITEM = "LISTRIK";
    public static final String LANTAI_ITEM = "LANTAI";
    public static final String HARGA_ITEM = "HARGA";
    public static final String DESKRIPSI_ITEM = "DESKRIPSI";
    public static final String LAT_ITEM = "LATITUDE";
    public static final String LNG_ITEM = "LONGITUDE";
    public boolean insert(String nama,byte[] img,int LB,int LT,int KT,int KM,int listrik,int lantai,int harga,String deskripsi,double lat,double lng) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_ITEM,nama);
        contentValues.put(IMG_ITEM,img);
        contentValues.put(LT_ITEM,LT);
        contentValues.put(LB_ITEM,LB);
        contentValues.put(KT_ITEM,KT);
        contentValues.put(KM_ITEM,KM);
        contentValues.put(LISTRIK_ITEM,listrik);
        contentValues.put(LANTAI_ITEM,lantai);
        contentValues.put(HARGA_ITEM,harga);
        contentValues.put(DESKRIPSI_ITEM,deskripsi);
        contentValues.put(LAT_ITEM,lat);
        contentValues.put(LNG_ITEM,lng);
        long check = db.insert(TABLE_ITEM, null, contentValues);
        if(check!=-1){
            return true;
        }else{
            return false;
        }
    }
    public boolean update(int id,String nama,byte[] img,int LB,int LT,int KT,int KM,int listrik,int lantai,int harga,String deskripsi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_ITEM,nama);
        contentValues.put(IMG_ITEM,img);
        contentValues.put(LT_ITEM,LT);
        contentValues.put(LB_ITEM,LB);
        contentValues.put(KT_ITEM,KT);
        contentValues.put(KM_ITEM,KM);
        contentValues.put(LISTRIK_ITEM,listrik);
        contentValues.put(LANTAI_ITEM,lantai);
        contentValues.put(HARGA_ITEM,harga);
        contentValues.put(DESKRIPSI_ITEM,deskripsi);
        return db.update(TABLE_ITEM, contentValues,ID_ITEM + "=" + id, null) > 0;
    }
    public boolean delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ITEM, ID_ITEM + "=" + id, null) > 0;
    }
    public Cursor getItems() {
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.rawQuery(
                "SELECT rowid _id, "+
                        ID_ITEM+", "+
                        NAME_ITEM+", "+
                        IMG_ITEM+
                        " FROM "+TABLE_ITEM,
                null);
    }
    public Cursor getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        return db.rawQuery(
                "SELECT rowid _id, * FROM "+TABLE_ITEM+
                        " WHERE "+ID_ITEM+" = "+id,
                null);
    }
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_ITEM+" ( "+
                ID_ITEM+" integer primary key autoincrement," +
                NAME_ITEM+" text," +
                IMG_ITEM+" BLOB," +
                LB_ITEM+" integer," +
                LT_ITEM+" integer," +
                KT_ITEM+" integer," +
                KM_ITEM+" integer," +
                LISTRIK_ITEM+" integer," +
                LANTAI_ITEM+" integer," +
                HARGA_ITEM+" integer," +
                DESKRIPSI_ITEM+" integer," +
                LAT_ITEM+" real," +
                LNG_ITEM+" real);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ITEM);
        onCreate(db);
    }
}
