package com.tugas.kontrolkeuangan.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Hutang {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    @ColumnInfo(name = "Nama")
    public String Nama;

    @ColumnInfo(name = "Hutang")
    public String Hutang;

    @ColumnInfo(name = "Tanggal")
    public String Tanggal;


}
