package com.dss.dsboxplus.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.estimates.PdfGeneration;
import com.google.android.material.button.MaterialButton;

public class BottommSheetDialogFragment extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {

    MaterialButton btCreatePDFInBottomSheet;
    public static BottommSheetDialogFragment newInstance() {
        return new BottommSheetDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);

        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btCreatePDFInBottomSheet=view.findViewById(R.id.btCreatePDFInBottomSheet);
        btCreatePDFInBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PdfGeneration pdfGeneration=new PdfGeneration();
//                pdfGeneration.generatePdf();
                Toast.makeText(getActivity(), "hiii", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
