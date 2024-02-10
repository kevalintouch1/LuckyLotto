package com.megalotto.megalotto.helper;

import android.app.ProgressDialog;
import android.content.Context;

import com.megalotto.megalotto.listner.ProgressListener;


public class ProgressBarHelper implements ProgressListener {
    private final ProgressDialog dialog;

    public ProgressBarHelper(Context context, boolean isCancelable) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        this.dialog = progressDialog;
        progressDialog.setCancelable(isCancelable);
        progressDialog.setCanceledOnTouchOutside(isCancelable);
        progressDialog.setMessage("Please wait...");
    }

    @Override
    public void showProgressDialog() {
        ProgressDialog progressDialog = this.dialog;
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    @Override
    public void hideProgressDialog() {
        ProgressDialog progressDialog = this.dialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
