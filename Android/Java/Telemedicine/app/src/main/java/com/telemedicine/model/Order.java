package com.telemedicine.model;

import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("id")
    public int id;
    @SerializedName("address")
    public String alamat;
    @SerializedName("time")
    public String waktu;
}
