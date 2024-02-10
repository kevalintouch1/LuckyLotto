package com.megalotto.megalotto.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hbb20.CountryCodePicker;
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
import com.megalotto.megalotto.model.CustomerModel;

import org.json.JSONObject;

import java.util.Objects;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;


public class SignupActivity extends Activity {
    public CountryCodePicker cntrNoEt;
    public EditText confirmPasswordEt;
    private Context context;
    public EditText emailEt;
    public TextView loginTxt;
    public EditText mobileNoEt;
    public EditText passwordEt;
    public EditText referEt;
    public TextView signupTxt;
    private ProgressBarHelper progressBarHelper;
    private ApiCalling api;
    private String fcmToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.context = this;
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.progressBarHelper = new ProgressBarHelper(this.context, false);
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_GLOBAL);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener((OnCompleteListener) task -> m278xf944b6a4(task));
        this.emailEt = findViewById(R.id.emailEt);
        this.cntrNoEt = findViewById(R.id.cntrno);
        this.mobileNoEt = findViewById(R.id.mobileEdt);
        this.passwordEt = findViewById(R.id.passwordEdt);
        this.confirmPasswordEt = findViewById(R.id.confirmPasswordEdt);
        this.referEt = findViewById(R.id.referEdt);
        this.signupTxt = findViewById(R.id.signupTxt);
        TextView textView = findViewById(R.id.loginTxt);
        this.loginTxt = textView;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignupActivity.this.m276xe52c3805(view);
            }
        });
        this.signupTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignupActivity.this.m277x7266e986(view);
            }
        });

        CheckBox ivVisible1 = findViewById(R.id.ivVisible1);
        CheckBox ivVisible2 = findViewById(R.id.ivVisible2);

        ivVisible1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!TextUtils.isEmpty(passwordEt.getText().toString())) {
                    if (isChecked) {
                        passwordEt.setTransformationMethod(null); // Passing null removes the transformation method
                    } else {
                        passwordEt.setTransformationMethod(new PasswordTransformationMethod());
                    }
                    ivVisible1.setChecked(isChecked);
                }
            }
        });

        ivVisible2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!TextUtils.isEmpty(confirmPasswordEt.getText().toString())) {
                    if (isChecked) {
                        confirmPasswordEt.setTransformationMethod(null); // Passing null removes the transformation method
                    } else {
                        confirmPasswordEt.setTransformationMethod(new PasswordTransformationMethod());
                    }
                    ivVisible2.setChecked(isChecked);
                }
            }
        });

//        ivVisible2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("TAG", "onClick: " + confirmPasswordEt.getInputType());
//                if (!TextUtils.isEmpty(confirmPasswordEt.getText().toString())) {
//                    if (confirmPasswordEt.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD || confirmPasswordEt.getInputType() == 129) {
//                        confirmPasswordEt.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
//                        ivVisible2.setButtonDrawable(R.drawable.ic_visible);
//                    }
//                }
//            }
//        });
    }

    public void m278xf944b6a4(Task task) {
        if (task.isSuccessful()) {
            this.fcmToken = (String) task.getResult();
        }
    }


    public void m276xe52c3805(View v) {
        onBackPressed();
    }


    public void m277x7266e986(View v) {
        try {
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService("input_method");
            imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.emailEt.getText().toString().equals("") && this.mobileNoEt.getText().toString().equals("") && this.passwordEt.getText().toString().equals("") && this.confirmPasswordEt.getText().toString().equals("")) {
            Function.showToast(context, "All fields are mandatory");
        } else if (!this.emailEt.getText().toString().trim().matches("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            this.emailEt.setError("Enter valid Email Id");
        } else if (this.mobileNoEt.getText().toString().equals("")) {
            this.mobileNoEt.setError("Please enter mobile no");
        } else if (this.mobileNoEt.getText().length() < 6 || this.mobileNoEt.getText().length() > 15) {
            this.mobileNoEt.setError("Please enter valid mobile no");
        } else if (this.passwordEt.getText().toString().equals("")) {
            this.passwordEt.setError("Please enter password");
        } else {
            Preferences.getInstance(this.context).setString(Preferences.KEY_EMAIL, this.emailEt.getText().toString());
            AppConstant.COUNTRY_CODE = "+" + this.cntrNoEt.getSelectedCountryCode().trim();
            Preferences.getInstance(this.context).setString(Preferences.KEY_MOBILE, this.mobileNoEt.getText().toString());
            Preferences.getInstance(this.context).setString(Preferences.KEY_PASSWORD, this.passwordEt.getText().toString());
            Preferences.getInstance(this.context).setString(Preferences.KEY_REFER_CODE, this.referEt.getText().toString());
            customerRegistrationWithoutRefer();
        }
    }

    private void customerRegistrationWithoutRefer() {
        this.progressBarHelper.showProgressDialog();
        String email = Preferences.getInstance(this.context).getString(Preferences.KEY_EMAIL);
        String[] split = email.split("@");
        final String username = split[0];
        Log.e("TAG", "customer: " + Preferences.getInstance(this.context).getString(Preferences.KEY_MOBILE) + ">" +
                email + ">" +
                Preferences.getInstance(this.context).getString(Preferences.KEY_PASSWORD) + ">" +
                Preferences.getInstance(this.context).getString(Preferences.KEY_REFER_CODE) + ">" +
                this.fcmToken);


        Call<CustomerModel> call = this.api.customerRegistrationWithoutRefer(
                AppConstant.COUNTRY_CODE + Preferences.getInstance(this.context).getString(Preferences.KEY_MOBILE),
                email
                , Preferences.getInstance(this.context).getString(Preferences.KEY_PASSWORD),
                Preferences.getInstance(this.context).getString(Preferences.KEY_REFER_CODE),
                this.fcmToken);

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("mobile", AppConstant.COUNTRY_CODE + Preferences.getInstance(this.context).getString(Preferences.KEY_MOBILE));
        params.add("email", email);
        params.add("password", Preferences.getInstance(this.context).getString(Preferences.KEY_PASSWORD));
        params.add("referral", Preferences.getInstance(this.context).getString(Preferences.KEY_REFER_CODE));
        params.add("fcmToken", this.fcmToken);
        String getdata_url = AppConstant.API_PATH + ApiConstant.REGISTRATION_USER;
        client.post(getdata_url, params, new AsyncHttpResponseHandler() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("TAG", statusCode + " > " + new String(responseBody));
                try {
                    JSONObject jsonObject2 = new JSONObject(new String(responseBody));
                    String message = jsonObject2.getString("message");
                    Function.showToast(context, "" + message);
                    JSONObject usersData = jsonObject2.getJSONObject("usersData");
                    Preferences.getInstance(context).setString(Preferences.KEY_USER_ID, usersData.getString("_id"));
                    Preferences.getInstance(context).setString(Preferences.KEY_USER_NAME, username);
                    Preferences.getInstance(context).setString(Preferences.KEY_USER_EMAIL, usersData.getString("email"));
                    Preferences.getInstance(context).setString(Preferences.KEY_USER_MOBILE, usersData.getString("mobile"));
                    Preferences.getInstance(context).setString(Preferences.KEY_USER_REF, usersData.getString("referral"));
                    Preferences.getInstance(context).setString(Preferences.KEY_USER_TOTALCOIN, usersData.getString("totalCoin"));
                    Preferences.getInstance(context).setString(Preferences.KEY_USER_WONCOIN, usersData.getString("wonCoin"));
                    Preferences.getInstance(context).setString(Preferences.KEY_USER_BONUSCOIN, usersData.getString("bonusCoin"));
                    Preferences.getInstance(context).setString(Preferences.KEY_USER_TOKEN, usersData.getString("fcmToken"));
                    Function.fireIntent(getApplicationContext(), SigninActivity.class);
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
