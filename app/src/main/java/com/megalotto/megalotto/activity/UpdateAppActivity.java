package com.megalotto.megalotto.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.megalotto.megalotto.R;
import com.megalotto.megalotto.helper.Preferences;


public class UpdateAppActivity extends AppCompatActivity {
    public TextView dateTxt;
    public TextView laterTxt;
    public String latestVersion;
    public TextView updateTxt;
    private String updateURL;
    public String updatedOn;
    public TextView versionTxt;
    public WebView webView;
    public String whatsNewData;
    public String isForceUpdate = "0";
    public int code = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_app);
        initView();
        initListener();
        initPreference();
        this.dateTxt.setText(String.format("Updated On: %s", this.updatedOn));
        this.versionTxt.setText(String.format("New Version: v%s", this.latestVersion));
        this.webView.setBackgroundColor(0);
        if (this.whatsNewData != null) {
            String htmlData = "<font color='white'>" + this.whatsNewData + "</font>";
            this.webView.loadData(Base64.encodeToString(htmlData.getBytes(), 1), "text/html", "base64");
        }
        String htmlData2 = this.isForceUpdate;
        if (htmlData2.equals("1")) {
            this.laterTxt.setVisibility(View.GONE);
            this.updateTxt.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        this.versionTxt = findViewById(R.id.versionTxt);
        this.dateTxt = findViewById(R.id.dateTxt);
        this.webView = findViewById(R.id.webView);
        this.laterTxt = findViewById(R.id.laterTxt);
        this.updateTxt = findViewById(R.id.updateTxt);
    }

    private void initListener() {
        this.webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateAppActivity.this.m288xb220e3c0(view);
            }
        });
        this.updateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateAppActivity.this.m289xb357369f(view);
            }
        });
        this.laterTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateAppActivity.this.m290xb48d897e(view);
            }
        });
    }


    public void m288xb220e3c0(View v) {
        openWebPage(this.updateURL);
    }


    public void m289xb357369f(View v) {
        openWebPage(this.updateURL);
    }


    public void m290xb48d897e(View v) {
        if (Preferences.getInstance(this).getString(Preferences.KEY_IS_AUTO_LOGIN).equals("1")) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(67108864);
            startActivity(intent);
            finish();
            return;
        }
        startActivity(new Intent(getApplicationContext(), SigninActivity.class));
        finish();
    }

    private void initPreference() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.isForceUpdate = extras.getString("forceUpdate");
            this.updateURL = extras.getString("updateURL");
            this.updatedOn = extras.getString("updateDate");
            this.whatsNewData = extras.getString("whatsNew");
            this.latestVersion = extras.getString("latestVersionName");
        }
    }

    public void openWebPage(String url) {
        try {
            Uri webpage = Uri.parse(url);
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                webpage = Uri.parse("http://" + url);
            }
            Intent myIntent = new Intent("android.intent.action.VIEW", webpage);
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request. Please install verifyDownload web browser or check your URL.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
