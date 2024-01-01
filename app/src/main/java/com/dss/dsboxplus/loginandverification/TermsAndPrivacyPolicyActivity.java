package com.dss.dsboxplus.loginandverification;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;

public class TermsAndPrivacyPolicyActivity extends BaseActivity {
    WebView wvTermsAndPrivacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_privacy_policy);
        wvTermsAndPrivacy = findViewById(R.id.wvTermsAndPrivacy);
        Intent intent = getIntent();
        String wenLink = intent.getStringExtra("termsandprivacylink");
        wvTermsAndPrivacy.loadUrl(wenLink);
        wvTermsAndPrivacy.getSettings().setJavaScriptEnabled(true);
        wvTermsAndPrivacy.setWebChromeClient(new WebChromeClient());
    }
}