package com.megalotto.megalotto.model;

import androidx.core.app.NotificationCompat;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class AppModel {
    @SerializedName("result")
    private List<Result> result;

    public List<Result> getResult() {
        return this.result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }


    public static class Result {
        @SerializedName("bonus_used")
        private int bonus_used;
        @SerializedName("country_code")
        private String country_code;
        @SerializedName("currency_sign")
        private String currency_sign;
        @SerializedName("download_prize")
        private int download_prize;
        @SerializedName("force_update")
        private String force_update;
        @SerializedName("latest_version_code")
        private String latest_version_code;
        @SerializedName("latest_version_name")
        private String latest_version_name;
        @SerializedName("maintenance_mode")
        private int maintenance_mode;
        @SerializedName("max_deposit")
        private int max_deposit;
        @SerializedName("max_withdraw")
        private int max_withdraw;
        @SerializedName("min_deposit")
        private int min_deposit;
        @SerializedName("min_withdraw")
        private int min_withdraw;
        @SerializedName("mop")
        private int mop;
        @SerializedName(NotificationCompat.CATEGORY_MESSAGE)
        private String msg;
        @SerializedName("paytm_mer_id")
        private String paytm_mer_id;
        @SerializedName("razorpay_api_key")
        private String razorpay_api_key;
        @SerializedName("share_prize")
        private int share_prize;
        @SerializedName("success")
        private int success;
        @SerializedName("tawkto_chat_link")
        private String tawkto_chat_link;
        @SerializedName("update_date")
        private String update_date;
        @SerializedName("update_url")
        private String update_url;
        @SerializedName("upi")
        private String upi;
        @SerializedName("upi_mc")
        private String upi_mc;
        @SerializedName("upi_pn")
        private String upi_pn;
        @SerializedName("upi_tn")
        private String upi_tn;
        @SerializedName("upi_token")
        private String upi_token;
        @SerializedName("wallet_mode")
        private int wallet_mode;
        @SerializedName("whats_new")
        private String whats_new;

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getSuccess() {
            return this.success;
        }

        public void setSuccess(int success) {
            this.success = success;
        }

        public String getTawkto_chat_link() {
            return this.tawkto_chat_link;
        }

        public void setTawkto_chat_link(String tawkto_chat_link) {
            this.tawkto_chat_link = tawkto_chat_link;
        }

        public String getCountry_code() {
            return this.country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getCurrency_sign() {
            return this.currency_sign;
        }

        public void setCurrency_sign(String currency_sign) {
            this.currency_sign = currency_sign;
        }

        public int getMop() {
            return this.mop;
        }

        public void setMop(int mop) {
            this.mop = mop;
        }

        public int getWallet_mode() {
            return this.wallet_mode;
        }

        public void setWallet_mode(int wallet_mode) {
            this.wallet_mode = wallet_mode;
        }

        public int getMaintenance_mode() {
            return this.maintenance_mode;
        }

        public void setMaintenance_mode(int maintenance_mode) {
            this.maintenance_mode = maintenance_mode;
        }

        public String getUpi() {
            return this.upi;
        }

        public void setUpi(String upi) {
            this.upi = upi;
        }

        public String getUpi_mc() {
            return this.upi_mc;
        }

        public void setUpi_mc(String upi_mc) {
            this.upi_mc = upi_mc;
        }

        public String getUpi_pn() {
            return this.upi_pn;
        }

        public void setUpi_pn(String upi_pn) {
            this.upi_pn = upi_pn;
        }

        public String getUpi_tn() {
            return this.upi_tn;
        }

        public void setUpi_tn(String upi_tn) {
            this.upi_tn = upi_tn;
        }

        public String getUpi_token() {
            return this.upi_token;
        }

        public void setUpi_token(String upi_token) {
            this.upi_token = upi_token;
        }

        public String getPaytm_mer_id() {
            return this.paytm_mer_id;
        }

        public void setPaytm_mer_id(String paytm_mer_id) {
            this.paytm_mer_id = paytm_mer_id;
        }

        public String getRazorpay_api_key() {
            return this.razorpay_api_key;
        }

        public void setRazorpay_api_key(String razorpay_api_key) {
            this.razorpay_api_key = razorpay_api_key;
        }

        public int getMin_withdraw() {
            return this.min_withdraw;
        }

        public void setMin_withdraw(int min_withdraw) {
            this.min_withdraw = min_withdraw;
        }

        public int getMax_withdraw() {
            return this.max_withdraw;
        }

        public void setMax_withdraw(int max_withdraw) {
            this.max_withdraw = max_withdraw;
        }

        public int getMin_deposit() {
            return this.min_deposit;
        }

        public void setMin_deposit(int min_deposit) {
            this.min_deposit = min_deposit;
        }

        public int getMax_deposit() {
            return this.max_deposit;
        }

        public void setMax_deposit(int max_deposit) {
            this.max_deposit = max_deposit;
        }

        public int getShare_prize() {
            return this.share_prize;
        }

        public void setShare_prize(int share_prize) {
            this.share_prize = share_prize;
        }

        public int getDownload_prize() {
            return this.download_prize;
        }

        public void setDownload_prize(int download_prize) {
            this.download_prize = download_prize;
        }

        public int getBonus_used() {
            return this.bonus_used;
        }

        public void setBonus_used(int bonus_used) {
            this.bonus_used = bonus_used;
        }

        public String getForce_update() {
            return this.force_update;
        }

        public void setForce_update(String force_update) {
            this.force_update = force_update;
        }

        public String getWhats_new() {
            return this.whats_new;
        }

        public void setWhats_new(String whats_new) {
            this.whats_new = whats_new;
        }

        public String getUpdate_date() {
            return this.update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }

        public String getLatest_version_name() {
            return this.latest_version_name;
        }

        public void setLatest_version_name(String latest_version_name) {
            this.latest_version_name = latest_version_name;
        }

        public String getLatest_version_code() {
            return this.latest_version_code;
        }

        public void setLatest_version_code(String latest_version_code) {
            this.latest_version_code = latest_version_code;
        }

        public String getUpdate_url() {
            return this.update_url;
        }

        public void setUpdate_url(String update_url) {
            this.update_url = update_url;
        }
    }
}
