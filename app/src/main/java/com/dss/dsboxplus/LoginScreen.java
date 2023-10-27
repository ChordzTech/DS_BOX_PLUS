package com.dss.dsboxplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dsboxplusui.databinding.ActivityLoginScreenBinding;

public class LoginScreen extends AppCompatActivity {
    ActivityLoginScreenBinding loginScreenBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_screen);
        initView();
    }

    private void initView() {
        loginScreenBinding.btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
                startActivity(intent);
            }
        });
    }
}