package com.dss.dsboxplus.profile.subUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.baseview.BaseActivity;
import com.dss.dsboxplus.databinding.ActivityAddSubUserBinding;
import com.dss.dsboxplus.model.AddSubNewUserDataModel;

import java.util.ArrayList;

public class AddSubUserActivity extends BaseActivity {
    ActivityAddSubUserBinding addSubUserBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSubUserBinding= DataBindingUtil.setContentView(this,R.layout.activity_add_sub_user);
        initView();
    }

    private void initView() {

        addSubUserBinding.btSubmitInAddSubUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = addSubUserBinding.tietUserNameInSubUser.getText().toString();
                String contact = addSubUserBinding.tietContactNoInSubUser.getText().toString();



                Intent resultIntent = new Intent();
                resultIntent.putExtra("username", username);
                resultIntent.putExtra("contact", contact);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

}