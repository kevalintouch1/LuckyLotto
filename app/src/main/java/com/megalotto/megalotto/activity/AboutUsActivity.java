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


public class AboutUsActivity extends AppCompatActivity {
    private ApiCalling api;
    public Context context;
    private ProgressBarHelper progressBarHelper;
    private WebView webView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        this.context = this;
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.progressBarHelper = new ProgressBarHelper(this.context, false);
        initToolbar();
        getAboutUs();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.menu_about));
        getSupportActionBar().setElevation(0.0f);
    }

    private void getAboutUs() {
        this.webView = findViewById(R.id.webView);
        this.progressBarHelper.showProgressDialog();
        Call<ConfigurationModel> call = this.api.getAboutUs(AppConstant.PURCHASE_KEY);
        call.enqueue(new Callback<ConfigurationModel>() {
            @Override 
            public void onResponse(Call<ConfigurationModel> call2, Response<ConfigurationModel> response) {
                ConfigurationModel legalData;
                AboutUsActivity.this.progressBarHelper.hideProgressDialog();
                if (response.isSuccessful() && (legalData = response.body()) != null) {
                    List<ConfigurationModel.Result> res = legalData.getResult();
                    if (res.get(0).getSuccess() == 1) {
                        AboutUsActivity.this.webView.loadDataWithBaseURL(null, res.get(0).getAbout(), "text/html", Key.STRING_CHARSET_NAME, null);
                    }
                }
            }

            @Override 
            public void onFailure(Call<ConfigurationModel> call2, Throwable t) {
                AboutUsActivity.this.progressBarHelper.hideProgressDialog();
            }
        });
    }
}
