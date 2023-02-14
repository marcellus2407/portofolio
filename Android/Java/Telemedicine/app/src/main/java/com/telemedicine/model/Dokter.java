package com.telemedicine.model;

import com.google.gson.annotations.SerializedName;

public class Dokter extends User{
    @SerializedName("specialist")
    public String spesialis;
}
