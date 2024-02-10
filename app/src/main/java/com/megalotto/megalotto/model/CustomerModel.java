package com.megalotto.megalotto.model;

import com.google.gson.annotations.SerializedName;


public class CustomerModel {
    @SerializedName("status")
    public boolean status;

    @SerializedName("message")
    public String message;
}
