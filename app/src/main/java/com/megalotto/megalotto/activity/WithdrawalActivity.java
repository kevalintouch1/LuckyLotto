package com.megalotto.megalotto.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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


public class WithdrawalActivity extends AppCompatActivity {
    public TextView alertTv;
    private EditText amountEt;
    private String amountSt;
    private ApiCalling api;
    public TextView balanceTv;
    private Context context;
    private RadioButton googlePayRb;
    private EditText idEt;
    private String mopSt;
    private EditText nameEt;
    private String nameSt;
    public TextView noteTv;
    private String numberSt;
    private RadioButton payTmRb;
    private RadioButton phonePeRb;
    private ProgressBarHelper progressBarHelper;
    public TextView signTv;
    public TextView withdrawTv;
    private int wonBalance;

    
    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        this.context = this;
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.progressBarHelper = new ProgressBarHelper(this.context, false);
        initToolbar();
        initView();
        initPreference();
        initListener();
        this.signTv.setText(AppConstant.CURRENCY_SIGN);
        this.alertTv.setText(String.format("Minimum Redeem Amount is %s%d", AppConstant.CURRENCY_SIGN, Integer.valueOf(AppConstant.MIN_WITHDRAW_LIMIT)));
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Withdraw");
        getSupportActionBar().setElevation(0.0f);
    }

    private void initView() {
        this.balanceTv = findViewById(R.id.balanceTv);
        this.payTmRb = findViewById(R.id.payTmRb);
        this.googlePayRb = findViewById(R.id.googlePayRb);
        this.phonePeRb = findViewById(R.id.phonePeRb);
        this.nameEt = findViewById(R.id.nameEt);
        this.idEt = findViewById(R.id.idEt);
        this.amountEt = findViewById(R.id.amountEt);
        this.noteTv = findViewById(R.id.noteTv);
        this.alertTv = findViewById(R.id.alertTv);
        this.signTv = findViewById(R.id.signTv);
        this.withdrawTv = findViewById(R.id.withdrawTv);
    }

    private void initPreference() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.wonBalance = extras.getInt("won_amount", 0);
            this.balanceTv.setText(AppConstant.CURRENCY_SIGN + this.wonBalance);
            return;
        }
        this.balanceTv.setText(AppConstant.CURRENCY_SIGN + "0");
    }

    private void initListener() {
        WithdrawalActivity.this.m293xd5eb520b();
        this.payTmRb.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                WithdrawalActivity.this.m293xd5eb520b();
            }
        });
        this.googlePayRb.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                WithdrawalActivity.this.m294xfb7f5b0c();
            }
        });
        this.phonePeRb.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                WithdrawalActivity.this.m295x2113640d();
            }
        });
        this.withdrawTv.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                WithdrawalActivity.this.m296x46a76d0e(view);
            }
        });
    }


    public void m293xd5eb520b() {
        this.nameEt.setHint("Register name");
        this.idEt.setHint("PayTm Number/UPI Id");
        this.mopSt = "PayTm";
    }


    public void m294xfb7f5b0c() {
        this.nameEt.setHint("Register name");
        this.idEt.setHint("GooglePay Number/UPI Id");
        this.mopSt = "GooglePay";
    }


    public void m295x2113640d() {
        this.nameEt.setHint("Register name");
        this.idEt.setHint("PhonePe Number/UPI Id");
        this.mopSt = "PhonePe";
    }


    public void m296x46a76d0e(View v) {
        this.withdrawTv.setEnabled(false);
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.payTmRb.isChecked()) {
            this.mopSt = "PayTm";
            this.alertTv.setText("Enter Valid PayTm Number");
        } else if (this.googlePayRb.isChecked()) {
            this.mopSt = "GooglePay";
            this.alertTv.setText("Enter Valid GooglePay Number");
        } else if (this.phonePeRb.isChecked()) {
            this.mopSt = "PhonePe";
            this.alertTv.setText("Enter Valid PhonePe Number");
        }
        this.nameSt = Objects.requireNonNull(this.nameEt.getText()).toString().trim();
        this.numberSt = Objects.requireNonNull(this.idEt.getText()).toString().trim();
        this.amountSt = Objects.requireNonNull(this.amountEt.getText()).toString().trim();
        if (!this.nameSt.isEmpty() && !this.numberSt.isEmpty() && !this.amountSt.isEmpty()) {
            int amount = Integer.parseInt(this.amountEt.getText().toString());
            if (amount < AppConstant.MIN_WITHDRAW_LIMIT) {
                this.withdrawTv.setEnabled(true);
                this.alertTv.setVisibility(View.VISIBLE);
                this.alertTv.setText("Minimum Redeem Amount is " + AppConstant.CURRENCY_SIGN + AppConstant.MIN_WITHDRAW_LIMIT);
                this.alertTv.setTextColor(Color.parseColor("#ff0000"));
                return;
            } else if (amount > AppConstant.MAX_WITHDRAW_LIMIT) {
                this.withdrawTv.setEnabled(true);
                this.alertTv.setVisibility(View.VISIBLE);
                this.alertTv.setText("Maximum Redeem Amount is " + AppConstant.CURRENCY_SIGN + AppConstant.MAX_WITHDRAW_LIMIT);
                this.alertTv.setTextColor(Color.parseColor("#ff0000"));
                return;
            } else if (this.wonBalance >= amount) {
                this.alertTv.setVisibility(View.GONE);
                if (Function.checkNetworkConnection(this.context)) {
                    addWithdrawTransaction();
                    return;
                }
                return;
            } else {
                this.withdrawTv.setEnabled(true);
                this.alertTv.setVisibility(View.VISIBLE);
                this.alertTv.setText("You don't have enough won amount to redeem.");
                this.alertTv.setTextColor(Color.parseColor("#ff0000"));
                return;
            }
        }
        this.withdrawTv.setEnabled(true);
        this.alertTv.setVisibility(View.VISIBLE);
        this.alertTv.setText("Please enter valid information.");
        this.alertTv.setTextColor(Color.parseColor("#ff0000"));
    }

    private void addWithdrawTransaction() {
        this.progressBarHelper.showProgressDialog();
        Call<CustomerModel> call = this.api.addWithdrawTransaction(AppConstant.PURCHASE_KEY, Preferences.getInstance(this.context).getString(Preferences.KEY_USER_ID),
                this.nameSt, this.numberSt, this.amountSt, this.mopSt);
        call.enqueue(new Callback<CustomerModel>() {
            @Override
            public void onResponse(Call<CustomerModel> call2, Response<CustomerModel> response) {
                WithdrawalActivity.this.progressBarHelper.hideProgressDialog();
                if (response.isSuccessful()) {
//                    CustomerModel legalData = response.body();
//                    if (legalData != null) {
//                        List<CustomerModel.Result> res = legalData.getResult();
//                        Function.showToast(WithdrawalActivity.this.context, res.get(0).getMsg());
//                        WithdrawalActivity.this.alertTv.setText(res.get(0).getMsg());
//                        WithdrawalActivity.this.onBackPressed();
//                        return;
//                    }
                    WithdrawalActivity.this.alertTv.setText("");
                }
            }

            @Override 
            public void onFailure(Call<CustomerModel> call2, Throwable t) {
                WithdrawalActivity.this.progressBarHelper.hideProgressDialog();
            }
        });
    }
}
