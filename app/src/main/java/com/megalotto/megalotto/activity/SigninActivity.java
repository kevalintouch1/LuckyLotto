package com.megalotto.megalotto.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.megalotto.megalotto.MyApplication;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.api.ApiCalling;
import com.megalotto.megalotto.api.ApiConstant;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.Constants;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.Preferences;
import com.megalotto.megalotto.helper.ProgressBarHelper;

import org.json.JSONObject;

import java.util.Objects;

import cz.msebera.android.httpclient.Header;


public class SigninActivity extends FragmentActivity {
    private ApiCalling api;
    private Context context;
    private String fcmToken;
    public TextView forgotTxt;
    public EditText mobileEdt;
    public EditText passwordEdt;
    private ProgressBarHelper progressBarHelper;
    public TextView signinTxt;
    public TextView signupTxt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        this.context = this;
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.progressBarHelper = new ProgressBarHelper(this.context, false);
        this.mobileEdt = findViewById(R.id.mobileEdt);
        this.passwordEdt = findViewById(R.id.passwordEdt);
        this.signinTxt = findViewById(R.id.signinTxt);
        this.signupTxt = findViewById(R.id.signupTxt);
        this.forgotTxt = findViewById(R.id.forgotTxt);
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_GLOBAL);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener() { // from class: com.ratechnoworld.megalotto.activity.SigninActivity$$ExternalSyntheticLambda0
            @Override
            public void onComplete(Task task) {
                SigninActivity.this.m272x49320c8f(task);
            }
        });
        this.signinTxt.setOnClickListener(new View.OnClickListener() { // from class: com.ratechnoworld.megalotto.activity.SigninActivity$$ExternalSyntheticLambda1
            @Override
            public void onClick(View view) {
                SigninActivity.this.m273xd66cbe10(view);
            }
        });
        this.signupTxt.setOnClickListener(new View.OnClickListener() { // from class: com.ratechnoworld.megalotto.activity.SigninActivity$$ExternalSyntheticLambda2
            @Override
            public void onClick(View view) {
                SigninActivity.this.m274x63a76f91(view);
            }
        });
        this.forgotTxt.setOnClickListener(new View.OnClickListener() { // from class: com.ratechnoworld.megalotto.activity.SigninActivity$$ExternalSyntheticLambda3
            @Override
            public void onClick(View view) {
                SigninActivity.this.m275xf0e22112(view);
            }
        });

        CheckBox ivVisible1 = findViewById(R.id.ivVisible1);
        ivVisible1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!TextUtils.isEmpty(passwordEdt.getText().toString())) {
                    if (isChecked) {
                        passwordEdt.setTransformationMethod(null);
                    } else {
                        passwordEdt.setTransformationMethod(new PasswordTransformationMethod());
                    }
                    ivVisible1.setChecked(isChecked);
                }
            }
        });
    }


    public void m272x49320c8f(Task task) {
        if (task.isSuccessful()) {
            this.fcmToken = (String) task.getResult();
        }
    }


    public void m273xd66cbe10(View v) {
        try {
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService("input_method");
            imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.mobileEdt.getText().toString().equals("") && this.passwordEdt.getText().toString().equals("")) {
            Function.showToast(context, "All fields are mandatory");
        } else if (this.mobileEdt.getText().length() < 6 || this.mobileEdt.getText().length() > 15) {
            this.mobileEdt.setError("Please enter valid mobile no");
        } else if (this.passwordEdt.getText().toString().length() < 8 || this.passwordEdt.getText().toString().length() > 20) {
            this.passwordEdt.setError("Password must be 8 to 20 characters");
        } else if (Function.checkNetworkConnection(this.context)) {
            loginUser();
        }
    }


    public void m274x63a76f91(View v) {
        Function.fireIntent(getApplicationContext(), SignupActivity.class);
    }


    public void m275xf0e22112(View v) {
        Function.fireIntent(getApplicationContext(), ForgotActivity.class);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        Intent a = new Intent("android.intent.action.MAIN");
        a.addCategory("android.intent.category.HOME");
        a.setFlags(268435456);
        startActivity(a);
    }

    private void loginUser() {
        this.progressBarHelper.showProgressDialog();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("mobile", AppConstant.COUNTRY_CODE + mobileEdt.getText().toString());
        params.add("password", passwordEdt.getText().toString());
        Log.e("TAG", " > " + params);

        String getdata_url = AppConstant.API_PATH + ApiConstant.LOGIN_USER;
        client.post(getdata_url, params, new AsyncHttpResponseHandler() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("TAG", statusCode + " > " + new String(responseBody));
                try {
                    JSONObject jsonObject2 = new JSONObject(new String(responseBody));
                    String message = jsonObject2.getString("message");
                    Function.showToast(context, "" + message);
                    Preferences.getInstance(context).setString(Preferences.KEY_IS_AUTO_LOGIN, "1");
                    JSONObject data = jsonObject2.getJSONObject("data");
                    Preferences.getInstance(context).setString(Preferences.KEY_USER_ID, data.getString("userId"));
                    Preferences.getInstance(context).setString(Preferences.KEY_AUTH_KEY, data.getString("token"));
                    Function.fireIntent(getApplicationContext(), MainActivity.class);
                } catch (Exception e) {
                    Log.e("TAG", "showAdIfAvailable: 111 " + e.getMessage());
                }
                progressBarHelper.hideProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("TAG1", statusCode + error.getMessage());
                Log.e("TAG2", new String(responseBody));
                try {
                    JSONObject jsonObject2 = new JSONObject(new String(responseBody));
                    String message = jsonObject2.getString("message");
                    Function.showToast(context, "" + message);
                } catch (Exception e) {
                    Function.showToast(context, "" + e.getMessage());
                }
                progressBarHelper.hideProgressDialog();
            }
        });
    }

}
