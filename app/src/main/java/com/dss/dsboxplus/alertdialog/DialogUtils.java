package com.dss.dsboxplus.alertdialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.profile.SubscriptionActivity;

public class DialogUtils {
    public interface DialogListener{
        void onPositiveButtonClick();
        void onNegativeButtonClick();
    }
    public static void showCustomDialog(Context context,DialogListener listener){
        View view = LayoutInflater.from(context).inflate(R.layout.subscription_popup, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button positivebutton = view.findViewById(R.id.btUpgradeSub);
        Button negativeButton = view.findViewById(R.id.btContinue);
        positivebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onPositiveButtonClick();
                }
                alertDialog.dismiss();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onNegativeButtonClick();
                }
                alertDialog.dismiss();
            }
        });
    }
}
