package com.megalotto.megalotto.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CustomerResponseModel {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("referral")
    @Expose
    private String referral;
    @SerializedName("fcmToken")
    @Expose
    private String fcmToken;
    @SerializedName("totalCoin")
    @Expose
    private String totalCoin;
    @SerializedName("wonCoin")
    @Expose
    private String wonCoin;
    @SerializedName("bonusCoin")
    @Expose
    private String bonusCoin;
    @SerializedName("isDeleted")
    @Expose
    private boolean isDeleted;
    @SerializedName("_id")
    @Expose
    private String _id;

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getReferral() {
        return referral;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public String getTotalCoin() {
        return totalCoin;
    }

    public String getWonCoin() {
        return wonCoin;
    }

    public String getBonusCoin() {
        return bonusCoin;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public String get_id() {
        return _id;
    }
}
