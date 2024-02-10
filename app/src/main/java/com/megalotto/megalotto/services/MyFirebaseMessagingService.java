package com.megalotto.megalotto.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.internal.view.SupportMenu;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.activity.MainActivity;
import com.megalotto.megalotto.utils.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Objects;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private Integer notificationId = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String str = TAG;
        Log.e(str, "From: " + remoteMessage.getFrom());
        Map<String, String> data = remoteMessage.getData();
        String title = Objects.requireNonNull(remoteMessage.getNotification()).getTitle();
        String message = remoteMessage.getNotification().getBody();
        String img_uri = String.valueOf(remoteMessage.getNotification().getImageUrl());
        String action = data.get("action");
        Log.i(str, "onMessageReceived: title : " + title);
        Log.i(str, "onMessageReceived: message : " + message);
        Log.i(str, "onMessageReceived: imageUrl : " + img_uri);
        Log.i(str, "onMessageReceived: action : " + action);
        if (img_uri.equals("null")) {
            showNotificationMessage(title, message);
        } else {
            showNotificationMessageWithBigImage(title, message, img_uri);
        }
        if (remoteMessage.getNotification() != null) {
            Log.e(str, "Notification Body: " + remoteMessage.getNotification().getBody());
        }
        if (remoteMessage.getData().size() > 0) {
            Log.e(str, "Data Payload: " + remoteMessage.getData());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleDataMessage(JSONObject json) {
        String str = TAG;
        Log.e(str, "push json: " + json.toString());
        try {
            JSONObject data = json.getJSONObject("data");
            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            String url = data.getString("external_link");
            JSONObject payload = data.getJSONObject("payload");
            Log.e(str, "title: " + title);
            Log.e(str, "message: " + message);
            Log.e(str, "isBackground: " + isBackground);
            Log.e(str, "payload: " + payload);
            Log.e(str, "imageUrl: " + imageUrl);
            Log.e(str, "timestamp: " + timestamp);
            Log.e(str, "externalLink: " + url);
            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                Intent pushNotification = new Intent(com.megalotto.megalotto.helper.Constants.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                NotificationUtils notificationUtils = new NotificationUtils();
                notificationUtils.playNotificationSound(getApplicationContext());
            } else {
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(title, message);
                } else {
                    showNotificationMessageWithBigImage(title, message, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e2) {
            Log.e(TAG, "Exception: " + e2.getMessage());
        }
    }

    private void showNotificationMessage(String title, String message) {
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        resultIntent.putExtra("message", message);
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        Uri notificationSound = RingtoneManager.getDefaultUri(2);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel mChannel = new NotificationChannel("my_channel_01", "my_channel", 4);
            mChannel.setDescription("This is my channel");
            mChannel.enableLights(true);
            mChannel.setLightColor(SupportMenu.CATEGORY_MASK);
            mChannel.setShowBadge(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }
        }
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, 134217728);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "my_channel_01").setContentTitle(title).setSound(notificationSound).setContentText(message).setSmallIcon(R.drawable.app_icon).setPriority(0).setAutoCancel(true).setContentIntent(resultPendingIntent).setColor(getResources().getColor(17170451));
        if (notificationManager != null) {
            notificationManager.notify(incrementNotificationId().intValue(), builder.build());
            notificationManager.cancel(incrementNotificationId().intValue());
        }
    }

    private void showNotificationMessageWithBigImage(String title, String message, String imageUrl) {
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        resultIntent.putExtra("message", message);
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        Uri notificationSound = RingtoneManager.getDefaultUri(2);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel mChannel = new NotificationChannel("my_channel_01", "my_channel", 4);
            mChannel.setDescription("This is my channel");
            mChannel.enableLights(true);
            mChannel.setLightColor(SupportMenu.CATEGORY_MASK);
            mChannel.setShowBadge(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }
        }
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, 134217728);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "my_channel_01").setContentTitle(title).setSound(notificationSound).setStyle(new NotificationCompat.BigPictureStyle().bigPicture(getBitmapFromUrl(imageUrl)).setSummaryText(message)).setContentText(message).setSmallIcon(R.drawable.app_icon).setPriority(0).setAutoCancel(true).setContentIntent(resultPendingIntent).setColor(getResources().getColor(17170451));
        if (notificationManager != null) {
            notificationManager.notify(incrementNotificationId().intValue(), builder.build());
            notificationManager.cancel(incrementNotificationId().intValue());
        }
    }

    private Integer incrementNotificationId() {
        Integer num = this.notificationId;
        this.notificationId = Integer.valueOf(num.intValue() + 1);
        return num;
    }

    public Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
