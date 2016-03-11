package com.licon.rssfeeds.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.licon.rssfeeds.R;
import com.licon.rssfeeds.data.constants.IntentData;
import com.licon.rssfeeds.util.AppUtil;

/**
 * Created by FRAMGIA\khairul.alam.licon on 11/03/16.
 */
public class WebViewActivity extends AppCompatActivity {

    private final static int PROGRESS_LEVEL_LOW = 0;
    private final static int PROGRESS_LEVEL_HIGH = 100;

    private Toolbar mToolbar;
    private ActionBar mActionbar;
    private ProgressBar mProgressBar;
    private ProgressBar mProgressBarWebView;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBarWebView = (ProgressBar) findViewById(R.id.progressBarWebView);
        mWebView = (WebView) findViewById(R.id.webview);

        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBarWebView = (ProgressBar) findViewById(R.id.progressBarWebView);
        mProgressBarWebView.setMax(PROGRESS_LEVEL_HIGH);
        mProgressBarWebView.setVisibility(View.GONE);

        setupActionBar();
        setupWebView();
        updateWebView(getIntent());
    }

    private void setupActionBar() {
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setHomeAsUpIndicator(R.drawable.ic_back_arrow);
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
    }

    private void setupWebView() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                WebViewActivity.this.mProgressBarWebView.setVisibility(View.VISIBLE);
                WebViewActivity.this.mProgressBarWebView.setProgress(PROGRESS_LEVEL_LOW);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                WebViewActivity.this.mProgressBarWebView.setVisibility(View.GONE);
                WebViewActivity.this.mProgressBarWebView.setProgress(PROGRESS_LEVEL_HIGH);
                super.onPageFinished(view, url);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                WebViewActivity.this.mProgressBarWebView.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });
        AppUtil.getAppWebViewSettings(mWebView, this);
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void goBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else
            finish();
    }

    public void updateWebView(Intent intent) {
        if (intent != null && intent.getExtras() != null
            && !TextUtils.isEmpty(intent.getExtras().getString(IntentData.WEBVIEW_DATA))) {
            String url = intent.getExtras().getString(IntentData.WEBVIEW_DATA);
            mWebView.loadUrl(url);
        }
        mProgressBar.setVisibility(View.GONE);
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBack();
                return true;
        }
        return false;
    }
}