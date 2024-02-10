package com.megalotto.megalotto.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.megalotto.megalotto.R;


public class Function {
    public static void fireIntent(Context context, Class cls) {
        Intent i = new Intent(context, cls);
        i.setFlags(268435456);
        context.startActivity(i);
    }

    public static void fireIntent(Activity context) {
        context.finish();
    }

    public static void fireIntentWithData(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static boolean checkNetworkConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void shareApp(Context context, String Code) {
        Intent i = new Intent("android.intent.action.SEND");
        i.setType("text/plain");
        i.putExtra("android.intent.extra.TEXT", "Earn Up To " + AppConstant.CURRENCY_SIGN + "50,000/day. By Participating In Lottery Contest. Join " + context.getString(R.string.app_name) + " Today With My Refer Code & Get " + AppConstant.CURRENCY_SIGN + "" + AppConstant.APP_DOWNLOAD_PRIZE + " Instantly. \n\n\nRefer code: " + Code + "\n\nApp Link: " + AppConstant.WEB_URL);
        context.startActivity(Intent.createChooser(i, "Share"));
    }
}
