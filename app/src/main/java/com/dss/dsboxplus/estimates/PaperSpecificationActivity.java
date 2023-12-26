package com.dss.dsboxplus.estimates;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.databinding.ActivityPaperSpecificationBinding;

public class PaperSpecificationActivity extends AppCompatActivity {
    ActivityPaperSpecificationBinding paperSpecificationBinding;
    String noOfPly = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paperSpecificationBinding = DataBindingUtil.setContentView(this, R.layout.activity_paper_specification);
        initView();

        noOfPly = this.getIntent().getStringExtra("noOfPly");

        switch (noOfPly) {
            case "1Ply":
                paperSpecificationBinding.cvTopPaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvFluteOnePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvMiddleOnePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvFluteTwoPaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvMiddleTwoPaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvFluteThreePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvBottomPaper.setVisibility(View.GONE);
                break;
            case "2Ply":
                paperSpecificationBinding.cvTopPaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvFluteOnePaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvMiddleOnePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvFluteTwoPaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvMiddleTwoPaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvFluteThreePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvBottomPaper.setVisibility(View.GONE);
                break;
            case "3Ply":
                paperSpecificationBinding.cvTopPaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvFluteOnePaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvMiddleOnePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvFluteTwoPaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvMiddleTwoPaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvFluteThreePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvBottomPaper.setVisibility(View.VISIBLE);
                break;
            case "5Ply":
                paperSpecificationBinding.cvTopPaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvFluteOnePaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvMiddleOnePaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.tvMiddleOnePaper.setText("Middle Paper");
                paperSpecificationBinding.cvFluteTwoPaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.tvFlutePaper.setText("Flute One Paper");
                paperSpecificationBinding.cvMiddleTwoPaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvFluteThreePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvBottomPaper.setVisibility(View.VISIBLE);
                break;
            case "7Ply":
                paperSpecificationBinding.cvTopPaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvFluteOnePaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.tvFlutePaper.setText("Flute One Paper");
                paperSpecificationBinding.cvMiddleOnePaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvFluteTwoPaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvMiddleTwoPaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvFluteThreePaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvBottomPaper.setVisibility(View.VISIBLE);
                break;
            case "2Ply(KG)":
                paperSpecificationBinding.cvTopPaper.setVisibility(View.VISIBLE);
                paperSpecificationBinding.cvFluteOnePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvMiddleOnePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvFluteTwoPaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvMiddleTwoPaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvFluteThreePaper.setVisibility(View.GONE);
                paperSpecificationBinding.cvBottomPaper.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initView() {

        paperSpecificationBinding.bfInTopPaper.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String enteredText = s.toString();
                paperSpecificationBinding.bfInFlutePaper.setText(enteredText);
                paperSpecificationBinding.bfInMiddleOnePaper.setText(enteredText);
                paperSpecificationBinding.bfInFluteTwoPaper.setText(enteredText);
                paperSpecificationBinding.bfInMiddleTwoPaper.setText(enteredText);
                paperSpecificationBinding.bfInFluteThreePaper.setText(enteredText);
                paperSpecificationBinding.bfInBottomPaper.setText(enteredText);
            }
        });
        paperSpecificationBinding.gsmInTopPaper.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String enteredText = s.toString();
                paperSpecificationBinding.gsmInFlutePaper.setText(enteredText);
                paperSpecificationBinding.gsmInMiddleOnePaper.setText(enteredText);
                paperSpecificationBinding.gsmInFluteTwoPaper.setText(enteredText);
                paperSpecificationBinding.gsmInMiddleTwoPaper.setText(enteredText);
                paperSpecificationBinding.gsmInFluteThreePaper.setText(enteredText);
                paperSpecificationBinding.gsmInBottomPaper.setText(enteredText);
            }
        });
        paperSpecificationBinding.rateKgInTopPaper.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String enteredText = s.toString();
                paperSpecificationBinding.rateKgInFlutePaper.setText(enteredText);
                paperSpecificationBinding.rateKgInMiddleOnePaper.setText(enteredText);
                paperSpecificationBinding.rateKgInFluteTwoPaper.setText(enteredText);
                paperSpecificationBinding.rateKgInMiddleTwoPaper.setText(enteredText);
                paperSpecificationBinding.rateKgInFluteThreePaper.setText(enteredText);
                paperSpecificationBinding.rateKgInBottomPaper.setText(enteredText);
            }
        });

        paperSpecificationBinding.btNextInPaperSpecfication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noOfPly=getIntent().getStringExtra("noOfPly");
                String cuttingL = getIntent().getStringExtra("cuttingLength");
                String decalM = getIntent().getStringExtra("decalSize");
                String bfInTopPaper = paperSpecificationBinding.bfInTopPaper.getText().toString();
                String bfInFlutePaper = paperSpecificationBinding.bfInFlutePaper.getText().toString();
                String bfInMiddleOnePaper = paperSpecificationBinding.bfInMiddleOnePaper.getText().toString();
                String bfInFluteTwoPaper = paperSpecificationBinding.bfInFluteTwoPaper.getText().toString();
                String bfInMiddleTwoPaper = paperSpecificationBinding.bfInMiddleTwoPaper.getText().toString();
                String bfInFluteThreePaper = paperSpecificationBinding.bfInFluteThreePaper.getText().toString();
                String bfInBottomPaper = paperSpecificationBinding.bfInBottomPaper.getText().toString();
                String gsmInTop = paperSpecificationBinding.gsmInTopPaper.getText().toString();
                String gsmInFlutePaper = paperSpecificationBinding.gsmInFlutePaper.getText().toString();
                String gsmInMiddleOnePaper = paperSpecificationBinding.gsmInMiddleOnePaper.getText().toString();
                String gsmInFluteTwoPaper = paperSpecificationBinding.gsmInFluteTwoPaper.getText().toString();
                String gsmInMiddleTwoPaper = paperSpecificationBinding.gsmInMiddleTwoPaper.getText().toString();
                String gsmInFluteThreePaper = paperSpecificationBinding.gsmInFluteThreePaper.getText().toString();
                String gsmInBottomPaper = paperSpecificationBinding.gsmInBottomPaper.getText().toString();
                String rateKgInTop = paperSpecificationBinding.rateKgInTopPaper.getText().toString();
                String rateKgInFlutePaper = paperSpecificationBinding.rateKgInFlutePaper.getText().toString();
                String rateKgInMiddleOnePaper = paperSpecificationBinding.rateKgInMiddleOnePaper.getText().toString();
                String rateKgInFluteTwoPaper = paperSpecificationBinding.rateKgInFluteTwoPaper.getText().toString();
                String rateKgInMiddleTwoPaper = paperSpecificationBinding.rateKgInMiddleTwoPaper.getText().toString();
                String rateKgInFluteThreePaper = paperSpecificationBinding.rateKgInFluteThreePaper.getText().toString();
                String rateKgInBottomPaper = paperSpecificationBinding.rateKgInBottomPaper.getText().toString();
                String ffInFluteOnePaper = paperSpecificationBinding.ffInFlutePaper.getText().toString();
                String ffInFluteTwoPaper = paperSpecificationBinding.ffInFluteTwoPaper.getText().toString();
                String ffInFluteThreePaper = paperSpecificationBinding.ffInFluteThreePaper.getText().toString();

                Intent intent = new Intent(getApplicationContext(), BoxSpecificationAndCostActivity.class);
                intent.putExtra("bfInTopPaper", bfInTopPaper);
                intent.putExtra("bfInFlutePaper", bfInFlutePaper);
                intent.putExtra("bfInMiddleOnePaper", bfInMiddleOnePaper);
                intent.putExtra("bfInFluteTwoPaper", bfInFluteTwoPaper);
                intent.putExtra("bfInMiddleTwoPaper", bfInMiddleTwoPaper);
                intent.putExtra("bfInFluteThreePaper", bfInFluteThreePaper);
                intent.putExtra("bfInBottomPaper", bfInBottomPaper);
                intent.putExtra("gsmInTop", gsmInTop);
                intent.putExtra("gsmInFlutePaper", gsmInFlutePaper);
                intent.putExtra("gsmInMiddleOnePaper", gsmInMiddleOnePaper);
                intent.putExtra("gsmInFluteTwoPaper", gsmInFluteTwoPaper);
                intent.putExtra("gsmInMiddleTwoPaper", gsmInMiddleTwoPaper);
                intent.putExtra("gsmInFluteThreePaper", gsmInFluteThreePaper);
                intent.putExtra("gsmInBottomPaper", gsmInBottomPaper);
                intent.putExtra("rateKgInTop", rateKgInTop);
                intent.putExtra("rateKgInFlutePaper", rateKgInFlutePaper);
                intent.putExtra("rateKgInMiddleOnePaper", rateKgInMiddleOnePaper);
                intent.putExtra("rateKgInFluteTwoPaper", rateKgInFluteTwoPaper);
                intent.putExtra("rateKgInMiddleTwoPaper", rateKgInMiddleTwoPaper);
                intent.putExtra("rateKgInFluteThreePaper", rateKgInFluteThreePaper);
                intent.putExtra("rateKgInBottomPaper", rateKgInBottomPaper);
                intent.putExtra("ffInFluteOnePaper", ffInFluteOnePaper);
                intent.putExtra("ffInFluteTwoPaper", ffInFluteTwoPaper);
                intent.putExtra("ffInFluteThreePaper", ffInFluteThreePaper);
                intent.putExtra("cuttingLengthResult", cuttingL);
                intent.putExtra("decalSizeResult", decalM);
                intent.putExtra("noOfPly",noOfPly);
                startActivity(intent);
            }
        });
        paperSpecificationBinding.btBackInPaperSpecification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}




