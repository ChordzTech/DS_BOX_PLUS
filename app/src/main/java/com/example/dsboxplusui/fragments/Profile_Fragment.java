package com.example.dsboxplusui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dsboxplusui.R;
import com.example.dsboxplusui.settings.BusinessDetails;
import com.example.dsboxplusui.settings.DefaultPaperSettings;
import com.example.dsboxplusui.settings.DefaultRateSettings;
import com.example.dsboxplusui.settings.Help;
import com.example.dsboxplusui.settings.QuotationTerms;
import com.example.dsboxplusui.settings.UserDetailsInProfile;


public class Profile_Fragment extends Fragment {
    CardView cvBusiness, cvDefaultPaper, cvDefaultRate, cvQuotationTerms, cvHelp, cvProfileName;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profile_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Profile_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile_Fragment newInstance() {
        Profile_Fragment fragment = new Profile_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_, container, false);
        cvBusiness = v.findViewById(R.id.businessSettings);
        cvDefaultPaper = v.findViewById(R.id.cvDefaultPaperSettings);
        cvDefaultRate = v.findViewById(R.id.cvDefaultRateSettings);
        cvQuotationTerms = v.findViewById(R.id.cvQuotationTerms);
        cvProfileName = v.findViewById(R.id.cvProfileName);
        cvHelp=v.findViewById(R.id.cvHelp);
        cvBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BusinessDetails.class);
                startActivity(intent);
            }
        });
        cvDefaultPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DefaultPaperSettings.class);
                startActivity(intent);
            }
        });
        cvDefaultRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DefaultRateSettings.class);
                startActivity(intent);
            }
        });
        cvQuotationTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QuotationTerms.class);
                startActivity(intent);
            }
        });
        cvProfileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserDetailsInProfile.class);
                startActivity(intent);
            }
        });
        cvHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), Help.class);
                startActivity(intent);
            }
        });
        return v;
    }
}