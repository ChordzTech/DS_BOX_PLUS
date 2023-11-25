package com.dss.dsboxplus.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dss.dsboxplus.R;

public class SubscriptionActivity extends AppCompatActivity {
    TextView tvCopyright;
    String url="https://dishaswarajsoft.in/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        tvCopyright=findViewById(R.id.tvCopyright);
        tvCopyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SubscriptionActivity.this, WebViewActivity.class);
                intent.putExtra("link",url);
                startActivity(intent);
            }
        });
    }
}