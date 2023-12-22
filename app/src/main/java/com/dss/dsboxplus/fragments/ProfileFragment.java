package com.dss.dsboxplus.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.alertdialog.DialogUtils;
import com.dss.dsboxplus.profile.BusinessDetails;
import com.dss.dsboxplus.profile.DefaultPaperSettings;
import com.dss.dsboxplus.profile.DefaultRateSettings;
import com.dss.dsboxplus.profile.Help;
import com.dss.dsboxplus.profile.QuotationTerms;
import com.dss.dsboxplus.profile.SubscriptionActivity;
import com.dss.dsboxplus.profile.SuperUserSetting;
import com.dss.dsboxplus.profile.UserDetailsInProfile;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;


public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ShapeableImageView ivProfile;
    SwitchCompat swMultiUser;
    FloatingActionButton fabAddImage;
    CardView cvBusiness, cvDefaultPaper, cvDefaultRate, cvQuotationTerms, cvHelp, cvProfileName, cvSubscription, cvsuperUserSettings;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Profile_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        cvSubscription = v.findViewById(R.id.cvSubscription);
        cvHelp = v.findViewById(R.id.cvHelp);
        ivProfile = v.findViewById(R.id.ivProfileInProfileFragment);
        fabAddImage = v.findViewById(R.id.fabAddImage);
        cvsuperUserSettings = v.findViewById(R.id.cvSuperUserSettings);
        swMultiUser=v.findViewById(R.id.swMultiUser);
        fabAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ProfileFragment.this)
                        .cropSquare()
                        .crop()//Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        swMultiUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
showDialog();
                }else {
                }
            }
        });
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
//        cvProfileName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), UserDetailsInProfile.class);
//                startActivity(intent);
//            }
//        });
        cvHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Help.class);
                startActivity(intent);
            }
        });
        cvSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SubscriptionActivity.class);
                startActivity(intent);
            }
        });
        cvsuperUserSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SuperUserSetting.class);
                startActivity(intent);
            }
        });
        return v;
    }

    private void showDialog() {
        DialogUtils.showCustomDialog(getContext(), new DialogUtils.DialogListener() {
            @Override
            public void onPositiveButtonClick() {
                Intent intent = new Intent(getActivity(), SubscriptionActivity.class);
                startActivity(intent);
            }

            @Override
            public void onNegativeButtonClick() {

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        ivProfile.setImageURI(uri);
    }
}