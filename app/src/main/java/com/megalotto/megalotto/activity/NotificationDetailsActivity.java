package com.megalotto.megalotto.activity;

import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.internal.ImagesContract;
import com.paytm.pgsdk.Constants;
import com.megalotto.megalotto.R;

import java.util.Objects;


public class NotificationDetailsActivity extends AppCompatActivity {
    public ImageView coverImg;
    public String created;
    public TextView dateTxt;
    public String description;
    public String image;
    public Button moreBtn;
    public TextView subtitleTxt;
    public String title;
    public String url;
    public WebView webView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        initPreference();
        initToolbar();
        initView();
        initListener();
        this.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationDetailsActivity.this.m259x6c47c008(view);
            }
        });
    }

    public void m259x6c47c008(View v) {
        openWebPage(this.url);
    }

    private void initPreference() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.title = extras.getString("title");
            this.description = extras.getString("description");
            this.image = extras.getString("image");
            this.url = extras.getString(ImagesContract.URL);
            this.created = extras.getString("created");
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(this.title);
        getSupportActionBar().setElevation(0.0f);
    }

    private void initView() {
        this.coverImg = findViewById(R.id.coverImg);
        this.subtitleTxt = findViewById(R.id.subtitleTxt);
        this.dateTxt = findViewById(R.id.dateTxt);
        this.moreBtn = findViewById(R.id.moreBtn);
        this.webView = findViewById(R.id.webView);
        this.subtitleTxt.setText(this.title);
        this.dateTxt.setText(this.created);
        this.webView.setBackgroundColor(0);
        this.webView.loadData(this.description, "text/html", Key.STRING_CHARSET_NAME);
        try {
            if (!this.image.equals("null")) {
                Glide.with(getApplicationContext()).load("https://megalotto.ratechnoworld.com/admin/" + this.image).apply(new RequestOptions().override(720, 540)).apply(new RequestOptions().placeholder(R.drawable.app_icon).error(R.drawable.app_icon)).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).apply(RequestOptions.skipMemoryCacheOf(true)).into(this.coverImg);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            if (this.url.isEmpty()) {
                this.moreBtn.setVisibility(View.GONE);
            } else if (this.url.equals(Constants.EVENT_LABEL_FALSE)) {
                this.moreBtn.setVisibility(View.GONE);
            } else {
                this.moreBtn.setVisibility(View.VISIBLE);
            }
        } catch (NullPointerException e2) {
            this.moreBtn.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        this.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationDetailsActivity.this.m258x3922e3de(view);
            }
        });
    }

    public void m258x3922e3de(View v) {
        openWebPage(this.url);
    }

    public void openWebPage(String url) {
        try {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(this, Uri.parse(url));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request. Please install link web browser or check your URL.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
