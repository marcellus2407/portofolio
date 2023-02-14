package com.telemedicine.model;

import com.google.gson.annotations.SerializedName;

public class Obat {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String nama;
    @SerializedName("price")
    public int harga;
    @SerializedName("description")
    public String deskripsi;
    @SerializedName("stock")
    public int stok;
    @SerializedName("photo")
    public String foto;
}
