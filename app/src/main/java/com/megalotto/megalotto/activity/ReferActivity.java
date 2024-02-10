package com.megalotto.megalotto.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.megalotto.megalotto.R;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.helper.Function;
import com.megalotto.megalotto.helper.Preferences;

import java.util.Objects;


public class ReferActivity extends AppCompatActivity {
    public TextView prizeTxt;
    public TextView referTxt;
    public TextView shareBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Refer & Earn");
        getSupportActionBar().setElevation(0.0f);

        initView();
        initListener();
        this.referTxt.setText(Preferences.getInstance(this).getString(Preferences.KEY_USER_NAME));
        this.prizeTxt.setText(String.format("%s%d and your friend gets %s%d as a", AppConstant.CURRENCY_SIGN, Integer.valueOf(AppConstant.APP_SHARE_PRIZE), AppConstant.CURRENCY_SIGN, Integer.valueOf(AppConstant.APP_DOWNLOAD_PRIZE)));
    }

    private void initView() {
        this.referTxt = findViewById(R.id.referTxt);
        this.prizeTxt = findViewById(R.id.prizeTxt);
        this.shareBtn = findViewById(R.id.shareBtn);
    }

    private void initListener() {
        this.referTxt.setOnClickListener(view -> ReferActivity.this.m269x42de2428(view));
        this.shareBtn.setOnClickListener(view -> ReferActivity.this.m270x70b6be87(view));
    }

    
    public void m269x42de2428(View v) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Refer Code", Preferences.getInstance(this).getString(Preferences.KEY_USER_NAME));
        clipboard.setPrimaryClip(clip);
    }


    public void m270x70b6be87(View v) {
        Function.shareApp(this, Preferences.getInstance(this).getString(Preferences.KEY_USER_NAME));
    }
}
