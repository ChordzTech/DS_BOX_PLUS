package com.dss.dsboxplus.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.AppConfigDataItems;
import com.dss.dsboxplus.data.repo.response.AppConfigResponse;
import com.dss.dsboxplus.data.repo.response.SubscriptionDataItem;
import com.dss.dsboxplus.data.repo.response.SubscriptionDetailsResponse;
import com.dss.dsboxplus.databinding.ActivitySubscriptionBinding;

import java.util.ArrayList;

public class SubscriptionActivity extends AppCompatActivity {
    TextView tvCopyright;
    ActivitySubscriptionBinding subscriptionBinding;
    String url = "https://dishaswarajsoft.in/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscriptionBinding = DataBindingUtil.setContentView(this, R.layout.activity_subscription);
        initView();
    }

    private void initView() {
        AppConfigResponse appConfigResponse = ConfigDataProvider.INSTANCE.getAppConfigResponse();
        if (appConfigResponse.getData() != null) {
            ArrayList<AppConfigDataItems> appConfigDataItems = appConfigResponse.getData();
            for (AppConfigDataItems appConfigDataItem : appConfigDataItems) {
                // Access individual properties of AppConfigDataItems
                int configId = appConfigDataItem.getConfigid();
                String configName = appConfigDataItem.getConfigname();
                String configValue = appConfigDataItem.getConfigvalue();
                if (configId == 7) {
                    subscriptionBinding.tvContactNumber.setText(configValue);
                } else if (configId == 13) {
                    subscriptionBinding.tvaccountName.setText(configValue);
                } else if (configId == 14) {
                    subscriptionBinding.tvAccountNumber.setText(configValue);
                } else if (configId == 16) {
                    subscriptionBinding.tvBankName.setText(configValue);
                } else if (configId == 15) {
                    subscriptionBinding.tvIfscCode.setText(configValue);
                } else if (configId == 17) {
                    subscriptionBinding.tvBranch.setText(configValue);
                } else if (configId == 18) {
//                    subscriptionBinding.ivQRCode.setImageBitmap();
                }
            }


        }
        SubscriptionDetailsResponse subscriptionDetailsResponse = ConfigDataProvider.INSTANCE.getSubResponse();
        if (subscriptionDetailsResponse.getData() != null) {
            ArrayList<SubscriptionDataItem> subscriptionDataItems = subscriptionDetailsResponse.getData();
            for (SubscriptionDataItem subscriptionDataItem : subscriptionDataItems) {
                Integer duration = subscriptionDataItem.getDuration();
                int amount = subscriptionDataItem.getAmount();
                String subscription = subscriptionDataItem.getSubscription();
                int subscriptionId = subscriptionDataItem.getSubscriptionid();
                String status = subscriptionDataItem.getStatus();
                if (subscriptionId == 1) {
                    String subAmountMonthly = "₹ " + amount;
                    subscriptionBinding.tvMonthly.setText(subAmountMonthly);
                } else if (subscriptionId == 2) {
                    String subAmountYearly = "₹ " + amount;
                    subscriptionBinding.tvYearlySingle.setText(subAmountYearly);
                } else if (subscriptionId == 3) {
                    String subAmountYearlyMulti = "₹ " + amount;
                    subscriptionBinding.tvYearlyMultiUser.setText(subAmountYearlyMulti);
                }
            }
        }
        subscriptionBinding.ivQRCode.setImageBitmap(decodeBase64ToBitmap(ConfigDataProvider.INSTANCE.getQrCodeBase64()));

        subscriptionBinding.tvCopyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubscriptionActivity.this, WebViewActivity.class);
                intent.putExtra("link", url);
                startActivity(intent);
            }
        });
    }

    private Bitmap decodeBase64ToBitmap(String base64Code) {
        String prefix = "data:image/png;base64,";
        if (base64Code.startsWith(prefix)) {
            // Remove the prefix before decoding
            base64Code = base64Code.substring(prefix.length());
        }
        byte[] decodedBytes = android.util.Base64.decode(base64Code, android.util.Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
