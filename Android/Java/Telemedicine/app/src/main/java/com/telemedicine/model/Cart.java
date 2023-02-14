package com.telemedicine.model;

import com.google.gson.annotations.SerializedName;

public class Cart extends Obat{
    @SerializedName("quantity")
    public int quantity;
}
