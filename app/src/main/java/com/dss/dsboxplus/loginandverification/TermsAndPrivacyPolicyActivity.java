package com.dss.dsboxplus.loginandverification;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;

public class TermsAndPrivacyPolicyActivity extends BaseActivity {
    WebView wvTermsAndPrivacy;
    String url = "https://dsbox.dishaswaraj.in/Terms.aspx";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_privacy_policy);

        wvTermsAndPrivacy = findViewById(R.id.wvTermsAndPrivacy);
        wvTermsAndPrivacy.loadUrl(url);
        wvTermsAndPrivacy.getSettings().setJavaScriptEnabled(true);
        wvTermsAndPrivacy.setWebViewClient(new HelpWebClient());
    }
    class  HelpWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showLoader();
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            hideLoader();
        }
    }
}