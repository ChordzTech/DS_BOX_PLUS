package com.dss.dsboxpro.profile;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.databinding.DataBindingUtil;

import com.dss.dsboxpro.R;
import com.dss.dsboxpro.baseview.BaseActivity;
import com.dss.dsboxpro.data.configdata.ConfigDataProvider;
import com.dss.dsboxpro.data.repo.response.AppConfigDataItems;
import com.dss.dsboxpro.data.repo.response.AppConfigResponse;
import com.dss.dsboxpro.databinding.ActivityHelpBinding;

import java.util.ArrayList;


public class Help extends BaseActivity {
    ActivityHelpBinding helpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helpBinding = DataBindingUtil.setContentView(this, R.layout.activity_help);
        initView();

    }

    private void initView() {
        helpBinding.wvHelp.getSettings().setJavaScriptEnabled(true);
        helpBinding.wvHelp.setWebViewClient(new HelpWebClient());

        AppConfigResponse appConfigResponse = ConfigDataProvider.INSTANCE.getAppConfigResponse();
        if (appConfigResponse.getData() != null) {
            ArrayList<AppConfigDataItems> appConfigDataItems = appConfigResponse.getData();
            for (AppConfigDataItems appConfigDataItem : appConfigDataItems) {
                int configId = appConfigDataItem.getConfigid();
                String configValue = appConfigDataItem.getConfigvalue();
                if (configId == 8) {
                    helpBinding.wvHelp.loadUrl(configValue);
                }
            }

        }
    }

    class HelpWebClient extends WebViewClient {
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