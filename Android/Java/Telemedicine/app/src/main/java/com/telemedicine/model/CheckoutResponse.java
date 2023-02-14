package com.telemedicine.model;

import com.google.gson.annotations.SerializedName;

public class CheckoutResponse {
    @SerializedName("response")
    public boolean response;
    @SerializedName("order")
    public Order order;
}
