package com.tugas.kontrolkeuangan.Database.DAO;

import com.tugas.kontrolkeuangan.Database.Entity.Hutang;
import com.tugas.kontrolkeuangan.Database.Entity.Keuangan;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface KeuanganDAO {
    /*UANG*/
    @Query("SELECT * FROM Keuangan")
    List<Keuangan> getAllkeuangan();

    @Insert
    void insertAllkeuangan(Keuangan... Keuangans);

    @Query("UPDATE Keuangan SET" +
            " BCA = :BCA, " +
            "ShopeePay = :ShopeePay, " +
            "OVO = :OVO, " +
            "GoPay = :GoPay, " +
            "Dana = :Dana, " +
            "Saham = :Saham," +
            "Cash = :Cash WHERE id = :id")
    void updateALLkeuangan(int id, String BCA,String ShopeePay,String OVO,String GoPay,String Dana,String Saham,String Cash);

    /*HUTANG*/

    @Query("SELECT * FROM Hutang")
    List<Hutang> getAllhutang();

    @Insert
    void insertAllhutang(Hutang... hutangs);

    @Query("UPDATE Hutang SET " +
            "Hutang = :Hutang, " +
            "Nama = :Nama, " +
            "Tanggal = :Tanggal WHERE id = :id")
    void updateALLhutang(int id, String Nama, String Hutang, String Tanggal);

    @Delete
    void deletehutang(Hutang hutang);

}
