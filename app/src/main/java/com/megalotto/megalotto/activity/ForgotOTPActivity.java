package com.megalotto.megalotto.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.Preferences;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class ForgotOTPActivity extends Activity {
    private Context context;
    public TextView hintTxt;
    private FirebaseAuth mAuth;
    public Pinview otpView;
    public TextView reEnterTv;
    public TextView submitTv;
    public TextView timerTv;
    private String verificationId;
    public int counter = 60;
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            ForgotOTPActivity.this.verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                ForgotOTPActivity.this.otpView.setValue(code);
                ForgotOTPActivity.this.verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(ForgotOTPActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        this.mAuth = FirebaseAuth.getInstance();
        this.context = this;
        this.otpView = findViewById(R.id.otpView);
        this.hintTxt = findViewById(R.id.hintTxt);
        this.timerTv = findViewById(R.id.timerTv);
        this.reEnterTv = findViewById(R.id.reEnterTv);
        this.submitTv = findViewById(R.id.submitTv);
        this.hintTxt.setText("Please enter the verification code sent to " + AppConstant.COUNTRY_CODE + Preferences.getInstance(this.context).getString(Preferences.KEY_MOBILE));
        sendVerificationCode(AppConstant.COUNTRY_CODE + Preferences.getInstance(this.context).getString(Preferences.KEY_MOBILE));
        new CountDownTimer(60000L, 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                ForgotOTPActivity.this.timerTv.setText(String.format("Resend in %ds", Integer.valueOf(ForgotOTPActivity.this.counter)));
                ForgotOTPActivity.this.timerTv.setVisibility(View.VISIBLE);
                ForgotOTPActivity.this.reEnterTv.setVisibility(View.GONE);
                ForgotOTPActivity.this.counter--;
            }

            @Override
            public void onFinish() {
                ForgotOTPActivity.this.counter = 60;
                ForgotOTPActivity.this.reEnterTv.setText("Resend");
                ForgotOTPActivity.this.reEnterTv.setVisibility(View.VISIBLE);
                ForgotOTPActivity.this.timerTv.setVisibility(View.GONE);
            }
        }.start();
        this.submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotOTPActivity.this.m249x61211fb9(view);
            }
        });
        this.reEnterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotOTPActivity.this.m250x62577298(view);
            }
        });
    }

    public void m249x61211fb9(View v) {
        try {
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.otpView.getValue().equals("")) {
            Toast.makeText(this.context, "Enter OTP", Toast.LENGTH_SHORT).show();
        } else {
            verifyCode(this.otpView.getValue());
        }
    }


    public void m250x62577298(View v) {
        try {
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.reEnterTv.getText().toString().equals("Resend")) {
            sendVerificationCode(AppConstant.COUNTRY_CODE + Preferences.getInstance(this.context).getString(Preferences.KEY_MOBILE));
            new CountDownTimer(60000L, 1000L) {
                @Override
                public void onTick(long millisUntilFinished) {
                    ForgotOTPActivity.this.timerTv.setText("Resend in " + ForgotOTPActivity.this.counter + "s");
                    ForgotOTPActivity.this.timerTv.setVisibility(View.VISIBLE);
                    ForgotOTPActivity.this.reEnterTv.setVisibility(View.GONE);
                    ForgotOTPActivity forgotOTPActivity = ForgotOTPActivity.this;
                    forgotOTPActivity.counter--;
                }

                @Override
                public void onFinish() {
                    ForgotOTPActivity.this.counter = 60;
                    ForgotOTPActivity.this.reEnterTv.setText("Resend");
                    ForgotOTPActivity.this.reEnterTv.setVisibility(View.VISIBLE);
                    ForgotOTPActivity.this.timerTv.setVisibility(View.GONE);
                }
            }.start();
        }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        this.mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(Task task) {
                ForgotOTPActivity.this.m251x66afd713(task);
            }
        });
    }


    public void m251x66afd713(Task task) {
        if (task.isSuccessful()) {
            Function.fireIntent(this, ResetActivity.class);
            finish();
            return;
        }
        Function.showToast(this, "Invalid OTP.");
    }

    private void sendVerificationCode(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60L, TimeUnit.SECONDS, this, this.mCallBack);
    }

    public void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(this.verificationId, code);
        signInWithCredential(credential);
    }
}
