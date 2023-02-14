package com.tugas.kontrolkeuangan.Database.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Keuangan {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    @ColumnInfo(name = "BCA")
    public String BCA;

    @ColumnInfo(name = "ShopeePay")
    public String ShopeePay;

    @ColumnInfo(name = "OVO")
    public String OVO;

    @ColumnInfo(name = "GoPay")
    public String GoPay;

    @ColumnInfo(name = "Dana")
    public String Dana;

    @ColumnInfo(name = "Saham")
    public String Saham;

    @ColumnInfo(name = "Cash")
    public String Cash;
}

