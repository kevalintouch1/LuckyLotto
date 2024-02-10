package com.megalotto.megalotto.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.megalotto.megalotto.MyApplication;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.api.ApiCalling;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.Preferences;
import com.megalotto.megalotto.helper.ProgressBarHelper;
import com.megalotto.megalotto.model.CustomerModel;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResetActivity extends Activity {
    private ApiCalling api;
    public EditText confirmPasswordEt;
    private Context context;
    public EditText passwordEt;
    private ProgressBarHelper progressBarHelper;
    public TextView submitTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        this.context = this;
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.progressBarHelper = new ProgressBarHelper(this.context, false);
        this.passwordEt = findViewById(R.id.passwordEt);
        this.confirmPasswordEt = findViewById(R.id.confirmPasswordEt);
        this.submitTxt = findViewById(R.id.submitTxt);
        this.submitTxt.setOnClickListener(view -> ResetActivity.this.m271xeb27be00(view));
    }

    public void m271xeb27be00(View v) {
        try {
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.passwordEt.getText().toString().equals("") && this.confirmPasswordEt.getText().toString().equals("")) {
            this.passwordEt.setError("All fields are mandatory");
        } else if (this.passwordEt.getText().toString().equals("")) {
            this.passwordEt.setError("Please enter password");
        } else if (this.passwordEt.getText().toString().length() < 8) {
            this.passwordEt.setError("Password must be 8 characters");
        } else if (!this.passwordEt.getText().toString().equals(this.confirmPasswordEt.getText().toString())) {
            this.confirmPasswordEt.setError("Password mismatch");
        } else if (Function.checkNetworkConnection(this.context)) {
            userForgotPassword();
        }
    }

    private void userForgotPassword() {
        this.progressBarHelper.showProgressDialog();
        Call<CustomerModel> call = this.api.userForgotPassword(AppConstant.PURCHASE_KEY, Preferences.getInstance(this.context).getString(Preferences.KEY_MOBILE), this.confirmPasswordEt.getText().toString());
        call.enqueue(new Callback<CustomerModel>() {
            @Override
            public void onResponse(Call<CustomerModel> call2, Response<CustomerModel> response) {
//                ResetActivity.this.progressBarHelper.hideProgressDialog();
//                CustomerModel legalData = response.body();
//                if (response.isSuccessful() && legalData != null) {
//                    List<CustomerModel.Result> res = legalData.getResult();
//                    if (res.get(0).getSuccess() != 1) {
//                        Function.showToast(ResetActivity.this.context, res.get(0).getMsg());
//                    } else {
//                        Function.fireIntent(ResetActivity.this.getApplicationContext(), SigninActivity.class);
//                    }
//                }
            }

            @Override 
            public void onFailure(Call<CustomerModel> call2, Throwable t) {
                ResetActivity.this.progressBarHelper.hideProgressDialog();
            }
        });
    }
}
