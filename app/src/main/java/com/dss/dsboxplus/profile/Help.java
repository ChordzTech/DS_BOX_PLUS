package com.dss.dsboxplus.profile;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.AppConfigDataItems;
import com.dss.dsboxplus.data.repo.response.AppConfigResponse;
import com.dss.dsboxplus.databinding.ActivityHelpBinding;

import java.util.ArrayList;


public class Help extends AppCompatActivity {
    ActivityHelpBinding helpBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helpBinding= DataBindingUtil. setContentView(this,R.layout.activity_help);
        initView();

    }

    private void initView() {
//        AppConfigResponse appConfigResponse = ConfigDataProvider.INSTANCE.getAppConfigResponse();
//        if (appConfigResponse.getData() != null) {
//            ArrayList<AppConfigDataItems> appConfigDataItems = appConfigResponse.getData();
//            for (AppConfigDataItems appConfigDataItem : appConfigDataItems) {
//                int configId = appConfigDataItem.getConfigid();
//                String configName = appConfigDataItem.getConfigname();
//                String configValue = appConfigDataItem.getConfigvalue();
//                if (configId == 8) {
//
//                }
//            }
//
//        }
    }
}