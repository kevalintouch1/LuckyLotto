package com.megalotto.megalotto.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paytm.pgsdk.Constants;


public class Token {
    @SerializedName(Constants.KEY_API_BODY)
    @Expose
    private Body body;
    @SerializedName(Constants.KEY_API_HEAD)
    @Expose
    private Head head;

    public Head getHead() {
        return this.head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Body getBody() {
        return this.body;
    }

    public void setBody(Body body) {
        this.body = body;
    }


    public static class ResultInfo {
        @SerializedName("resultCode")
        @Expose
        private String resultCode;
        @SerializedName("resultMsg")
        @Expose
        private String resultMsg;
        @SerializedName("resultStatus")
        @Expose
        private String resultStatus;

        public String getResultStatus() {
            return this.resultStatus;
        }

        public void setResultStatus(String resultStatus) {
            this.resultStatus = resultStatus;
        }

        public String getResultCode() {
            return this.resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getResultMsg() {
            return this.resultMsg;
        }

        public void setResultMsg(String resultMsg) {
            this.resultMsg = resultMsg;
        }
    }


    public static class Head {
        @SerializedName("responseTimestamp")
        @Expose
        private String responseTimestamp;
        @SerializedName("signature")
        @Expose
        private String signature;
        @SerializedName("version")
        @Expose
        private String version;

        public String getResponseTimestamp() {
            return this.responseTimestamp;
        }

        public void setResponseTimestamp(String responseTimestamp) {
            this.responseTimestamp = responseTimestamp;
        }

        public String getVersion() {
            return this.version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getSignature() {
            return this.signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }


    public static class Body {
        @SerializedName("authenticated")
        @Expose
        private boolean authenticated;
        @SerializedName("isPromoCodeValid")
        @Expose
        private boolean isPromoCodeValid;
        @SerializedName("resultInfo")
        @Expose
        private ResultInfo resultInfo;
        @SerializedName("txnToken")
        @Expose
        private String txnToken;

        public ResultInfo getResultInfo() {
            return this.resultInfo;
        }

        public void setResultInfo(ResultInfo resultInfo) {
            this.resultInfo = resultInfo;
        }

        public String getTxnToken() {
            return this.txnToken;
        }

        public void setTxnToken(String txnToken) {
            this.txnToken = txnToken;
        }

        public boolean isIsPromoCodeValid() {
            return this.isPromoCodeValid;
        }

        public void setIsPromoCodeValid(boolean isPromoCodeValid) {
            this.isPromoCodeValid = isPromoCodeValid;
        }

        public boolean isAuthenticated() {
            return this.authenticated;
        }

        public void setAuthenticated(boolean authenticated) {
            this.authenticated = authenticated;
        }
    }
}
