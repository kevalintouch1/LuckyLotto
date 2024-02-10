package com.megalotto.megalotto.model;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.gson.annotations.SerializedName;


public class NotificationModel {
    @SerializedName("status")
    public boolean status;
    @SerializedName("message")
    public String message;
    @SerializedName("sendNotificationData")
    public SendNotificationData sendNotificationData;

    public class SendNotificationData {
        @SerializedName("title")
        public String title;
        @SerializedName("message")
        public String message;
        @SerializedName("imageURL")
        public String imageURL;
        @SerializedName("externalLink")
        public String externalLink;

        public String getTitle() {
            return title;
        }

        public String getMessage() {
            return message;
        }

        public String getImageURL() {
            return imageURL;
        }

        public String getExternalLink() {
            return externalLink;
        }
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public SendNotificationData getSendNotificationData() {
        return sendNotificationData;
    }
}
