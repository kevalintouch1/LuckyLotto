package com.megalotto.megalotto.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.megalotto.megalotto.MyApplication;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.api.ApiCalling;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.Constants;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.Preferences;
import com.megalotto.megalotto.helper.ProgressBarHelper;
import com.megalotto.megalotto.model.CustomerModel;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignupOTPActivity extends Activity {
    private String androidId;
    private ApiCalling api;
    private Context context;
    private String fcmToken;
    public TextView hintTxt;
    private FirebaseAuth mAuth;
    public Pinview otpView;
    private ProgressBarHelper progressBarHelper;
    public TextView reEnterTv;
    public TextView submitTv;
    public TextView timerTv;
    private String verificationId;
    public int counter = 60;
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            SignupOTPActivity.this.verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                SignupOTPActivity.this.otpView.setValue(code);
                SignupOTPActivity.this.verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.e("TAG", "onVerificationFailed: "+ e.getMessage());
            Toast.makeText(SignupOTPActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        this.mAuth = FirebaseAuth.getInstance();
        this.context = this;
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.progressBarHelper = new ProgressBarHelper(this.context, false);
        this.androidId = Settings.Secure.getString(getContentResolver(), "android_id");
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_GLOBAL);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener((OnCompleteListener) task -> SignupOTPActivity.this.m278xf944b6a4(task));
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
                SignupOTPActivity.this.timerTv.setText("Resend in " + SignupOTPActivity.this.counter + "s");
                SignupOTPActivity.this.timerTv.setVisibility(View.VISIBLE);
                SignupOTPActivity.this.reEnterTv.setVisibility(View.GONE);
                SignupOTPActivity signupOTPActivity = SignupOTPActivity.this;
                signupOTPActivity.counter--;
            }

            @Override
            public void onFinish() {
                SignupOTPActivity.this.counter = 60;
                SignupOTPActivity.this.reEnterTv.setText("Resend");
                SignupOTPActivity.this.reEnterTv.setVisibility(View.VISIBLE);
                SignupOTPActivity.this.timerTv.setVisibility(View.GONE);
            }
        }.start();
        this.submitTv.setOnClickListener(view -> SignupOTPActivity.this.m279xfa7b0983(view));
        this.reEnterTv.setOnClickListener(view -> SignupOTPActivity.this.m280xfbb15c62(view));
    }

    
    public void m278xf944b6a4(Task task) {
        if (task.isSuccessful()) {
            this.fcmToken = (String) task.getResult();
        }
    }

    
    public void m279xfa7b0983(View v) {
        try {
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService("input_method");
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

    
    public void m280xfbb15c62(View v) {
        sendVerificationCode(AppConstant.COUNTRY_CODE + Preferences.getInstance(this.context).getString(Preferences.KEY_MOBILE));
        new CountDownTimer(60000L, 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                SignupOTPActivity.this.timerTv.setText("Resend in " + SignupOTPActivity.this.counter + "s");
                SignupOTPActivity.this.timerTv.setVisibility(View.VISIBLE);
                SignupOTPActivity.this.reEnterTv.setVisibility(View.GONE);
                SignupOTPActivity signupOTPActivity = SignupOTPActivity.this;
                signupOTPActivity.counter--;
            }

            @Override
            public void onFinish() {
                SignupOTPActivity.this.counter = 60;
                SignupOTPActivity.this.reEnterTv.setText("Resend");
                SignupOTPActivity.this.reEnterTv.setVisibility(View.VISIBLE);
                SignupOTPActivity.this.timerTv.setVisibility(View.GONE);
            }
        }.start();
    }

    public void verifyCode(String code) {
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(this.verificationId, code);
            signInWithCredential(credential);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        this.mAuth.signInWithCredential(credential).addOnCompleteListener((OnCompleteListener) task -> SignupOTPActivity.this.m281x9c0dd(task));
    }


    public void m281x9c0dd(Task task) {
        if (task.isSuccessful()) {
            if (Function.checkNetworkConnection(this.context)) {
                if (Preferences.getInstance(this.context).getString(Preferences.KEY_REFER_CODE).equals("")) {
                    customerRegistrationWithoutRefer();
                } else {
                    customerRegistrationWithRefer();
                }
                return;
            }
            return;
        }
        Function.showToast(this, "Invalid OTP.");
    }

    private void sendVerificationCode(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60L, TimeUnit.SECONDS, this, this.mCallBack);
    }

    private void customerRegistrationWithRefer() {
        this.progressBarHelper.showProgressDialog();
        String email = Preferences.getInstance(this.context).getString(Preferences.KEY_EMAIL);
        String[] split = email.split("@");
        final String username = split[0];
        Call<CustomerModel> call = this.api.customerRegistrationWithRefer(AppConstant.PURCHASE_KEY, username, email, this.androidId, username, Preferences.getInstance(this.context).getString(Preferences.KEY_PASSWORD), AppConstant.COUNTRY_CODE, Preferences.getInstance(this.context).getString(Preferences.KEY_MOBILE), Preferences.getInstance(this.context).getString(Preferences.KEY_REFER_CODE), this.fcmToken);
        call.enqueue(new Callback<CustomerModel>() {
            @Override
            public void onResponse(Call<CustomerModel> call2, Response<CustomerModel> response) {
                if (!response.isSuccessful()) {
                    SignupOTPActivity.this.progressBarHelper.hideProgressDialog();
                    return;
                }
//                CustomerModel legalData = response.body();
//                if (legalData != null) {
//                    List<CustomerModel.Result> res = legalData.getResult();
//                    if (res.get(0).getSuccess() == 1) {
//                        Preferences.getInstance(SignupOTPActivity.this.context).setString(Preferences.KEY_IS_AUTO_LOGIN, "1");
//                        Preferences.getInstance(SignupOTPActivity.this.context).setString(Preferences.KEY_USER_ID, res.get(0).getId());
//                        Preferences.getInstance(SignupOTPActivity.this.context).setString(Preferences.KEY_USER_NAME, username);
//                        Function.fireIntent(SignupOTPActivity.this.getApplicationContext(), MainActivity.class);
//                        return;
//                    }
//                    SignupOTPActivity.this.progressBarHelper.hideProgressDialog();
//                    Function.showToast(SignupOTPActivity.this.context, res.get(0).getMsg());
//                    Log.e("TAG", "customerRegistrationWithRefer: "+ res.get(0).getMsg());
//                }
            }

            @Override 
            public void onFailure(Call<CustomerModel> call2, Throwable t) {
                SignupOTPActivity.this.progressBarHelper.hideProgressDialog();
            }
        });
    }

    private void customerRegistrationWithoutRefer() {
//        this.progressBarHelper.showProgressDialog();
//        String email = Preferences.getInstance(this.context).getString(Preferences.KEY_EMAIL);
//        String[] split = email.split("@");
//        final String username = split[0];
//        Call<CustomerModel> call = this.api.customerRegistrationWithoutRefer(AppConstant.PURCHASE_KEY, username, email, this.androidId, username, Preferences.getInstance(this.context).getString(Preferences.KEY_PASSWORD), AppConstant.COUNTRY_CODE, Preferences.getInstance(this.context).getString(Preferences.KEY_MOBILE), this.fcmToken);
//        call.enqueue(new Callback<CustomerModel>() {
//            @Override
//            public void onResponse(Call<CustomerModel> call2, Response<CustomerModel> response) {
//                if (!response.isSuccessful()) {
//                    SignupOTPActivity.this.progressBarHelper.hideProgressDialog();
//                    return;
//                }
//                CustomerModel legalData = response.body();
//                if (legalData != null) {
//                    List<CustomerModel.Result> res = legalData.getResult();
//                    if (res.get(0).getSuccess() == 1) {
//                        Preferences.getInstance(SignupOTPActivity.this.context).setString(Preferences.KEY_IS_AUTO_LOGIN, "1");
//                        Preferences.getInstance(SignupOTPActivity.this.context).setString(Preferences.KEY_USER_ID, res.get(0).getId());
//                        Preferences.getInstance(SignupOTPActivity.this.context).setString(Preferences.KEY_USER_NAME, username);
//                        Function.fireIntent(SignupOTPActivity.this.getApplicationContext(), MainActivity.class);
//                        return;
//                    }
//                    SignupOTPActivity.this.progressBarHelper.hideProgressDialog();
//                    Function.showToast(SignupOTPActivity.this.context, res.get(0).getMsg());
//                    Log.e("TAG", "customerRegistrationWithoutRefer: "+ res.get(0).getMsg());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CustomerModel> call2, Throwable t) {
//                SignupOTPActivity.this.progressBarHelper.hideProgressDialog();
//            }
//        });
    }
}
