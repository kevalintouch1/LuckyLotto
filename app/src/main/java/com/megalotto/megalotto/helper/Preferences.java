package com.megalotto.megalotto.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.megalotto.megalotto.activity.SigninActivity;


public class Preferences {
    public static final String KEY_CONSTANT_ID = "KEY_CONSTANT_ID";
    public static final String KEY_CONTST_ID = "KEY_CONTST_ID";
    public static final String KEY_DEPOSITE_AMOUNT = "KEY_DEPOSITE_AMOUNT";
    public static final String KEY_DOB = "KEY_DOB";
    public static final String KEY_EMAIL = "KEY_EMAIL";
    public static final String KEY_FEE_ID = "KEY_FEE_ID";
    public static final String KEY_FULL_NAME = "KEY_FULL_NAME";
    public static final String KEY_GENDER = "KEY_GENDER";
    public static final String KEY_IS_AUTO_LOGIN = "KEY_IS_AUTO_LOGIN";
    public static final String KEY_MOBILE = "KEY_MOBILE";
    public static final String KEY_PASSWORD = "KEY_PASSWORD";
    public static final String KEY_PKG_ID = "KEY_PKG_ID";
    public static final String KEY_PRICE = "KEY_PRICE";
    public static final String KEY_REFER_CODE = "KEY_REFER_CODE";
    public static final String KEY_RESORT_IMAGE = "KEY_RESORT_IMAGE";
    public static final String KEY_SOLD = "KEY_SOLD";
    public static final String KEY_USER = "KEY_CHAT_BY_USER";
    public static final String KEY_USER_ID = "KEY_USER_ID";
    public static final String KEY_USER_TOKEN = "USER_TOKEN";
    public static final String KEY_AUTH_KEY = "AUTH_KEY";
    public static final String KEY_USER_NAME = "KEY_USER_NAME";
    public static final String KEY_WINNER = "KEY_WINNER";
    public static final String KEY_USER_EMAIL = "KEY_USER_EMAIL";
    public static final String KEY_USER_MOBILE = "KEY_USER_MOBILE";
    public static final String KEY_USER_REF = "KEY_USER_REF";
    public static final String KEY_USER_TOTALCOIN = "KEY_USER_TOTALCOIN";
    public static final String KEY_USER_WONCOIN = "KEY_USER_WONCOIN";
    public static final String KEY_USER_BONUSCOIN = "KEY_USER_BONUSCOIN";
    private static final String PREF_NAME = "com.gscscl.app";
    private static Preferences instance;
    public Context context;
    private final SharedPreferences sharedPref;

    public Preferences(Context context) {
        this.context = context;
        this.sharedPref = context.getSharedPreferences(PREF_NAME, 0);
    }

    public static Preferences getInstance(Context c) {
        if (instance == null) {
            instance = new Preferences(c);
        }
        return instance;
    }

    public SharedPreferences getSharedPref() {
        return this.sharedPref;
    }

    public String getString(String key) {
        return this.sharedPref.getString(key, "");
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor sharedPrefEditor = getSharedPref().edit();
        sharedPrefEditor.putString(key, value);
        sharedPrefEditor.apply();
    }

    public void setlogout() {
        SharedPreferences.Editor sharedPrefEditor = getSharedPref().edit();
        sharedPrefEditor.clear();
        sharedPrefEditor.apply();
        Intent i = new Intent(this.context, SigninActivity.class);
        i.putExtra("finish", true);
        i.setFlags(335577088);
        this.context.startActivity(i);
    }

    public int getInt(String key) {
        return this.sharedPref.getInt(key, 0);
    }

    public void setInt(String key, int value) {
        SharedPreferences.Editor sharedPrefEditor = getSharedPref().edit();
        sharedPrefEditor.putInt(key, value);
        sharedPrefEditor.apply();
    }
}
