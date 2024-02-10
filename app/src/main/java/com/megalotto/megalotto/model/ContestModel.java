package com.megalotto.megalotto.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ContestModel {
    @SerializedName("status")
    public boolean status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public ArrayList<Datas> data;

    public class Datas {
        @SerializedName("_id")
        public String _id;

        @SerializedName("status")
        public String status;
    }
}
