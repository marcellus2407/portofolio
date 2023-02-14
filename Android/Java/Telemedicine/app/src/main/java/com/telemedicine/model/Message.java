package com.telemedicine.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Message {
    @SerializedName("id")
    public int id;
    @SerializedName("sender")
    public int sender;
    @SerializedName("receiver")
    public int receiver;
    @SerializedName("message")
    public String message;
    @SerializedName("time")
    public String time;
}
