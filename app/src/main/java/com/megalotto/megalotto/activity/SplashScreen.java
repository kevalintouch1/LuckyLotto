package com.megalotto.megalotto.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.megalotto.megalotto.MyApplication;
import com.megalotto.megalotto.R;
import com.megalotto.megalotto.api.ApiCalling;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.Preferences;
import com.megalotto.megalotto.model.AppModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashScreen extends Activity {
    private ApiCalling api;
    public Context context;
    private String forceUpdate;
    private String latestVersionCode;
    private String latestVersionName;
    private TextView statusTxt;
    private String updateDate;
    private String updateUrl;
    private String whatsNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.context = this;
        this.api = MyApplication.getRetrofit().create(ApiCalling.class);
        this.statusTxt = findViewById(R.id.statusTxt);
        if (Function.checkNetworkConnection(this)) {
            m282x7ddb37c3();
        } else {
            this.statusTxt.setText("No internet Connection, please try again later.");
        }
    }

    
    public class AnonymousClass1 implements Callback<AppModel> {
        AnonymousClass1() {
        }

        @Override 
        public void onResponse(Call<AppModel> call, Response<AppModel> response) {
            AppModel legalData;
            if (response.isSuccessful() && (legalData = response.body()) != null) {
                List<AppModel.Result> res = legalData.getResult();
                if (res.get(0).getSuccess() != 1) {
                    SplashScreen.this.statusTxt.setText("App is under maintenance, please try again later.");
                    return;
                }
                AppConstant.CHAT_URL = res.get(0).getTawkto_chat_link();
                AppConstant.CURRENCY_SIGN = res.get(0).getCurrency_sign();
                AppConstant.COUNTRY_CODE = res.get(0).getCountry_code();
                AppConstant.MODE_OF_PAYMENT = res.get(0).getMop();
                AppConstant.WALLET_MODE = res.get(0).getWallet_mode();
                AppConstant.MAINTENANCE_MODE = res.get(0).getMaintenance_mode();
                AppConstant.PAYTM_M_ID = res.get(0).getPaytm_mer_id();
                AppConstant.RAZORPAY_API_KEY = res.get(0).getRazorpay_api_key();
                AppConstant.UPI_PA = res.get(0).getUpi();
                AppConstant.UPI_MC = res.get(0).getUpi_mc();
                AppConstant.UPI_TN = res.get(0).getUpi_tn();
                AppConstant.UPI_PN = res.get(0).getUpi_pn();
                AppConstant.TICKET_BONUS_USED = res.get(0).getBonus_used();
                AppConstant.APP_SHARE_PRIZE = res.get(0).getShare_prize();
                AppConstant.APP_DOWNLOAD_PRIZE = res.get(0).getDownload_prize();
                AppConstant.MIN_WITHDRAW_LIMIT = res.get(0).getMin_withdraw();
                AppConstant.MAX_WITHDRAW_LIMIT = res.get(0).getMax_withdraw();
                AppConstant.MIN_DEPOSIT_LIMIT = res.get(0).getMin_deposit();
                AppConstant.MAX_DEPOSIT_LIMIT = res.get(0).getMax_deposit();
                SplashScreen.this.forceUpdate = res.get(0).getForce_update();
                SplashScreen.this.whatsNew = res.get(0).getWhats_new();
                SplashScreen.this.updateDate = res.get(0).getUpdate_date();
                SplashScreen.this.latestVersionName = res.get(0).getLatest_version_name();
                SplashScreen.this.latestVersionCode = res.get(0).getLatest_version_code();
                SplashScreen.this.updateUrl = res.get(0).getUpdate_url();
                try {
                    if (5 < Integer.parseInt(SplashScreen.this.latestVersionCode)) {
                        if (!SplashScreen.this.forceUpdate.equals("1")) {
                            if (SplashScreen.this.forceUpdate.equals("0")) {
                                Intent intent = new Intent(SplashScreen.this, UpdateAppActivity.class);
                                intent.putExtra("forceUpdate", SplashScreen.this.forceUpdate);
                                intent.putExtra("whatsNew", SplashScreen.this.whatsNew);
                                intent.putExtra("updateDate", SplashScreen.this.updateDate);
                                intent.putExtra("latestVersionName", SplashScreen.this.latestVersionName);
                                intent.putExtra("updateURL", SplashScreen.this.updateUrl);
                                intent.setFlags(335577088);
                                SplashScreen.this.startActivity(intent);
                            }
                        } else {
                            Intent intent2 = new Intent(SplashScreen.this, UpdateAppActivity.class);
                            intent2.putExtra("forceUpdate", SplashScreen.this.forceUpdate);
                            intent2.putExtra("whatsNew", SplashScreen.this.whatsNew);
                            intent2.putExtra("updateDate", SplashScreen.this.updateDate);
                            intent2.putExtra("latestVersionName", SplashScreen.this.latestVersionName);
                            intent2.putExtra("updateURL", SplashScreen.this.updateUrl);
                            intent2.setFlags(335577088);
                            SplashScreen.this.startActivity(intent2);
                        }
                    } else if (AppConstant.MAINTENANCE_MODE != 0) {
                        SplashScreen.this.statusTxt.setText("App is under maintenance, please try again later.");
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                m282x7ddb37c3();
                            }
                        }, 1000L);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<AppModel> call, Throwable t) {
            SplashScreen.this.statusTxt.setText("App is under maintenance, please try again later.");
        }
    }

    public void m282x7ddb37c3() {
        if (Preferences.getInstance(SplashScreen.this).getString(Preferences.KEY_IS_AUTO_LOGIN).equals("1")) {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            intent.putExtra("finish", true);
            intent.setFlags(335577088);
            SplashScreen.this.startActivity(intent);
        } else {
            Intent intent2 = new Intent(SplashScreen.this, SigninActivity.class);
            intent2.putExtra("finish", true);
            intent2.setFlags(335577088);
            SplashScreen.this.startActivity(intent2);
        }
        SplashScreen.this.finish();
    }

    private void getAppDetails() {
        Call<AppModel> call = this.api.getAppDetails(AppConstant.PURCHASE_KEY);
        call.enqueue(new AnonymousClass1());
    }
}
