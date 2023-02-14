package com.telemedicine.model;

import com.google.gson.annotations.SerializedName;

public class OrderDetail extends Obat{
    @SerializedName("quantity")
    public int quantity;
}
