package com.megalotto.megalotto.activity;

import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.load.Key;
import com.megalotto.megalotto.MyApplication;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.api.ApiCalling;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.ProgressBarHelper;
import com.megalotto.megalotto.model.ConfigurationModel;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PrivacyPolicyActivity extends AppCompatActivity {
    private ApiCalling api;
    public Context context;
    private ProgressBarHelper progressBarHelper;
    private WebView webView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        this.context = this;
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.progressBarHelper = new ProgressBarHelper(this.context, false);
        initToolbar();
        getPrivacyPolicy();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.menu_policy));
        getSupportActionBar().setElevation(0.0f);
    }

    private void getPrivacyPolicy() {
        this.webView = findViewById(R.id.webView);
        this.progressBarHelper.showProgressDialog();
        Call<ConfigurationModel> call = this.api.getPrivacyPolicy(AppConstant.PURCHASE_KEY);
        call.enqueue(new Callback<ConfigurationModel>() { // from class: com.ratechnoworld.megalotto.activity.PrivacyPolicyActivity.1
            @Override
            public void onResponse(Call<ConfigurationModel> call2, Response<ConfigurationModel> response) {
                ConfigurationModel legalData;
                PrivacyPolicyActivity.this.progressBarHelper.hideProgressDialog();
                if (response.isSuccessful() && (legalData = response.body()) != null) {
                    List<ConfigurationModel.Result> res = legalData.getResult();
                    if (res.get(0).getSuccess() == 1) {
                        PrivacyPolicyActivity.this.webView.loadDataWithBaseURL(null, res.get(0).getPrivacy(), "text/html", Key.STRING_CHARSET_NAME, null);
                    }
                }
            }

            @Override
            public void onFailure(Call<ConfigurationModel> call2, Throwable t) {
                PrivacyPolicyActivity.this.progressBarHelper.hideProgressDialog();
            }
        });
    }
}
