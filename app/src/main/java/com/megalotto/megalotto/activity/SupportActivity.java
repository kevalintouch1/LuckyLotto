package com.megalotto.megalotto.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.megalotto.megalotto.R;
import com.megalotto.megalotto.helper.AppConstant;
import com.megalotto.megalotto.utils.WebAppInterface;

import java.util.Objects;


public class SupportActivity extends AppCompatActivity {
    private ValueCallback<Uri[]> fileChooserCallback;
    private ProgressDialog progDailog;
    public WebView webView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        initToolbar();
        initWebView();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.menu_support));
        getSupportActionBar().setElevation(0.0f);
    }

    private void initWebView() {
        ProgressDialog show = ProgressDialog.show(this, "Loading", "Please wait...", true);
        this.progDailog = show;
        show.setCancelable(false);
        WebView webView = (WebView) findViewById(R.id.webView);
        this.webView = webView;
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setSupportMultipleWindows(true);
        this.webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        this.webView.setWebViewClient(new WebViewClient() { // from class: com.ratechnoworld.megalotto.activity.SupportActivity.1
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                SupportActivity.this.progDailog.show();
                if (request.getUrl().toString().contains(Uri.parse(AppConstant.CHAT_URL).getHost())) {
                    view.loadUrl(request.getUrl().toString());
                    return true;
                }
                Intent intent = new Intent("android.intent.action.VIEW", request.getUrl());
                view.getContext().startActivity(intent);
                return true;
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView view, String url) {
                SupportActivity.this.progDailog.dismiss();
                Log.e("loading", "finished");
            }
        });
        this.webView.setWebChromeClient(new WebChromeClient() { // from class: com.ratechnoworld.megalotto.activity.SupportActivity.2
            @Override // android.webkit.WebChromeClient
            public void onPermissionRequest(PermissionRequest request) {
                request.grant(request.getResources());
            }

            @Override // android.webkit.WebChromeClient
            public boolean onShowFileChooser(WebView vw, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (SupportActivity.this.fileChooserCallback != null) {
                    SupportActivity.this.fileChooserCallback.onReceiveValue(null);
                }
                SupportActivity.this.fileChooserCallback = filePathCallback;
                Intent selectionIntent = new Intent("android.intent.action.GET_CONTENT");
                selectionIntent.addCategory("android.intent.category.OPENABLE");
                selectionIntent.setType("*/*");
                Intent chooserIntent = new Intent("android.intent.action.CHOOSER");
                chooserIntent.putExtra("android.intent.extra.INTENT", selectionIntent);
                SupportActivity.this.startActivityForResult(chooserIntent, 0);
                return true;
            }
        });
        this.webView.setOnKeyListener(new View.OnKeyListener() { // from class: com.ratechnoworld.megalotto.activity.SupportActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                return SupportActivity.lambda$initWebView$0(view, i, keyEvent);
            }
        });
        this.webView.setDownloadListener(new DownloadListener() { // from class: com.ratechnoworld.megalotto.activity.SupportActivity$$ExternalSyntheticLambda1
            @Override // android.webkit.DownloadListener
            public final void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                SupportActivity.this.m283xf0f96c8b(str, str2, str3, str4, j);
            }
        });
        this.webView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.ratechnoworld.megalotto.activity.SupportActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return SupportActivity.this.m284xb14eb2a(view);
            }
        });
        this.webView.loadUrl(AppConstant.CHAT_URL);
    }

    
    public static boolean lambda$initWebView$0(View v, int keyCode, KeyEvent event) {
        WebView vw = (WebView) v;
        if (event.getAction() == 0 && keyCode == 4 && vw.canGoBack()) {
            vw.goBack();
            return true;
        }
        return false;
    }

    
    /* renamed from: lambda$initWebView$1$com-ratechnoworld-megalotto-activity-SupportActivity  reason: not valid java name */
    public void m283xf0f96c8b(String uri, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        handleURI(uri);
    }

    
    /* renamed from: lambda$initWebView$2$com-ratechnoworld-megalotto-activity-SupportActivity  reason: not valid java name */
    public boolean m284xb14eb2a(View v) {
        handleURI(((WebView) v).getHitTestResult().getExtra());
        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        this.fileChooserCallback.onReceiveValue(new Uri[]{Uri.parse(intent.getDataString())});
        this.fileChooserCallback = null;
    }

    private void handleURI(String uri) {
        if (uri != null) {
            Intent i = new Intent("android.intent.action.VIEW");
            i.setData(Uri.parse(uri.replaceFirst("^blob:", "")));
            startActivity(i);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
