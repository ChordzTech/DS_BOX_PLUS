package com.dss.dsboxplus.profile;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.dss.dsboxplus.R;


public class Help extends AppCompatActivity {
    WebView wvyt;
    private ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        initView();

    }

    private void initView() {
        loadingSpinner = findViewById(R.id.loadingSpinner);
        wvyt = findViewById(R.id.wvYT);
        String ytVideo = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/rTqrdLBdIwA?si=rRkHygwNqSCcp9x9\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        wvyt.loadData(ytVideo, "text/html", "utf-8");
        wvyt.getSettings().setJavaScriptEnabled(true);
        wvyt.setWebChromeClient(new WebChromeClient());
        wvyt.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // Hide the loading spinner when the page is finished loading
                loadingSpinner.setVisibility(android.view.View.GONE);
            }
        });
    }

}