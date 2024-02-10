package com.megalotto.megalotto.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.megalotto.megalotto.MyApplication;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.api.ApiCalling;
import com.megalotto.megalotto.helper.ProgressBarHelper;

import java.util.Objects;


public class ForgotActivity extends Activity {
    private ApiCalling api;
    public CountryCodePicker cntrNoEt;
    private Context context;
    public EditText mobileEdt;
    public TextView otpTxt;
    private ProgressBarHelper progressBarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.progressBarHelper = new ProgressBarHelper(this.context, false);
        this.cntrNoEt = findViewById(R.id.cntrno);
        this.mobileEdt = findViewById(R.id.mobileEdt);
        TextView textView = findViewById(R.id.otpTxt);
        this.otpTxt = textView;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotActivity.this.m248xddb56f10(view);
            }
        });
    }


    public void m248xddb56f10(View v) {
        try {
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService("input_method");
            imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.mobileEdt.getText().toString().equals("")) {
            this.mobileEdt.setError("Please enter mobile no");
        } else if (this.mobileEdt.getText().length() < 6 || this.mobileEdt.getText().length() > 15) {
            this.mobileEdt.setError("Please enter valid mobile no");
        } else {
            verifyUserMobile();
        }
    }

    private void verifyUserMobile() {
        this.progressBarHelper.showProgressDialog();
//        Call<CustomerModel> call = this.api.verifyUserMobile(Preferences.getInstance(this).getString(Preferences.KEY_AUTH_KEY), this.mobileEdt.getText().toString());
//        call.enqueue(new Callback<CustomerModel>() {
//            @Override
//            public void onResponse(Call<CustomerModel> call2, Response<CustomerModel> response) {
//                CustomerModel legalData;
//                ForgotActivity.this.progressBarHelper.hideProgressDialog();
//                Log.e("TAG", "onResponse: "+ new Gson().toJson(response.body()));
//                if (response.isSuccessful() && (legalData = response.body()) != null) {
//                    Function.showToast(ForgotActivity.this, "" + legalData.message);
//                    if (legalData.status) {
//                        Preferences.getInstance(ForgotActivity.this).setString(Preferences.KEY_MOBILE, emailEt.getText().toString());
//                        Function.fireIntent(ForgotActivity.this, SigninActivity.class);
//                        return;
//                    }
//                    ForgotActivity.this.emailEt.setError("Please recheck your mobile no");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CustomerModel> call2, Throwable t) {
//                ForgotActivity.this.progressBarHelper.hideProgressDialog();
//                Function.showToast(ForgotActivity.this, "" + t.getMessage());
//            }
//        });
    }
}
