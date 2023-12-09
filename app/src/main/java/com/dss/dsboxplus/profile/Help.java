package com.dss.dsboxplus.profile;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.dss.dsboxplus.R;


public class Help extends AppCompatActivity {
    WebView wvyt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        wvyt=findViewById(R.id.wvYT);
        String ytVideo="<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/rTqrdLBdIwA?si=rRkHygwNqSCcp9x9\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        wvyt.loadData(ytVideo,"text/html","utf-8");
        wvyt.getSettings().setJavaScriptEnabled(true);
        wvyt.setWebChromeClient(new WebChromeClient());
    }


}