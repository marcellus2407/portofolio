package com.telemedicine.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    public int id;
    @SerializedName("email")
    public String email;
    @SerializedName("name")
    public String nama;
    @SerializedName("role")
    public String role;
}
