package com.dss.dsboxplus.estimates;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.DataItem;
import com.dss.dsboxplus.data.repo.response.EstimateListResponse;
import com.dss.dsboxplus.databinding.ActivityBoxEstimatesDetailsBinding;
import com.dss.dsboxplus.model.EstimatesDataModel;

import java.util.ArrayList;

public class BoxEstimatesDetailsActivity extends AppCompatActivity {
    ActivityBoxEstimatesDetailsBinding boxEstimatesDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boxEstimatesDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_box_estimates_details);
        initView();
        EstimateListResponse estimateListResponse =ConfigDataProvider.INSTANCE.getEstimateListResponse();

    }

    private void initView() {
        //Quotation
        boxEstimatesDetailsBinding.btQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QuotationInBoxEstimateDetailsActivity.class);
//                Bundle bundle=new Bundle();
//                bundle.putParcelable("EstimateDetails",estimatesDataModel);
//                intent.putExtra("EstimateDetails_Bundle",bundle);
                startActivity(intent);
            }
        });
        //Production
        boxEstimatesDetailsBinding.btProduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductionInBoxEstimatesDetailsActivity.class);
                startActivity(intent);

            }
        });
        //Delete
        boxEstimatesDetailsBinding.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog(view);
            }
        });
        boxEstimatesDetailsBinding.btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void dialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are You Sure?");
        builder.setMessage("You won't be able to recover this file!");
        builder.setCancelable(false);

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}