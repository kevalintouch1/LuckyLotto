package com.megalotto.megalotto.model;

import androidx.core.app.NotificationCompat;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ConfigurationModel {
    @SerializedName("result")
    private List<Result> result;

    public List<Result> getResult() {
        return this.result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }


    public static class Result {
        @SerializedName("about")
        private String about;
        @SerializedName("contact")
        private String contact;
        @SerializedName("faq")
        private String faq;
        @SerializedName(NotificationCompat.CATEGORY_MESSAGE)
        private String msg;
        @SerializedName("privacy")
        private String privacy;
        @SerializedName("success")
        private int success;
        @SerializedName("terms")
        private String terms;

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

        public String getAbout() {
            return this.about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getContact() {
            return this.contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getFaq() {
            return this.faq;
        }

        public void setFaq(String faq) {
            this.faq = faq;
        }

        public String getPrivacy() {
            return this.privacy;
        }

        public void setPrivacy(String privacy) {
            this.privacy = privacy;
        }

        public String getTerms() {
            return this.terms;
        }

        public void setTerms(String terms) {
            this.terms = terms;
        }
    }
}
