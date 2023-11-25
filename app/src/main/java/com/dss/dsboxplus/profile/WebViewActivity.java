package com.dss.dsboxplus.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dss.dsboxplus.R;

public class WebViewActivity extends AppCompatActivity {
    WebView wvWebsite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        wvWebsite=findViewById(R.id.wvWebsite);
        Intent intent=getIntent();
        String wenLink=intent.getStringExtra("link");
        wvWebsite.loadUrl(wenLink);
        wvWebsite.getSettings().setJavaScriptEnabled(true);
        wvWebsite.setWebChromeClient(new WebChromeClient());
    }
}