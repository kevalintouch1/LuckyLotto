package com.megalotto.megalotto.utils;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;


public class WebAppInterface {
    Context mContext;

    public WebAppInterface(Context c) {
        this.mContext = c;
    }

    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(this.mContext, toast, Toast.LENGTH_SHORT).show();
    }
}
