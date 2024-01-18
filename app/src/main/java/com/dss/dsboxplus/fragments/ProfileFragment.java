package com.dss.dsboxplus.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.alertdialog.DialogUtils;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.AppConfigDataItems;
import com.dss.dsboxplus.data.repo.response.BusinessDetails;
import com.dss.dsboxplus.data.repo.response.SubscriptionDataItem;
import com.dss.dsboxplus.data.repo.response.SubscriptionForBusiness;
import com.dss.dsboxplus.data.repo.response.UserData;
import com.dss.dsboxplus.profile.BusinessDetailsActivity;
import com.dss.dsboxplus.profile.DefaultPaperSettings;
import com.dss.dsboxplus.profile.DefaultRateSettings;
import com.dss.dsboxplus.profile.Help;
import com.dss.dsboxplus.profile.QuotationTerms;
import com.dss.dsboxplus.profile.SubscriptionActivity;
import com.dss.dsboxplus.profile.UserDetailsInProfile;
import com.dss.dsboxplus.profile.subUser.SuperUserSetting;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ShapeableImageView ivProfile;
    SwitchCompat swMultiUser;
    TextView tvTrialActive, tvSubDays, tvSubDate;
    FloatingActionButton fabAddImage;
    CardView cvBusiness, cvDefaultPaper, cvDefaultRate, cvQuotationTerms, cvHelp, cvProfileName, cvSubscription, cvsuperUserSettings;
    private ArrayList<AppConfigDataItems> appConfigList = new ArrayList<>();
    private String base64Code;
    private ArrayList<SubscriptionDataItem> subscriptionList = new ArrayList<>();
    private UserData userData;
    // TODO: Rename and change types of parameters

    private ArrayList<SubscriptionForBusiness> subscription = new ArrayList<>();

    private BusinessDetails businessDetails;
    private String mParam1;
    private String mParam2;
    private TextView name, contact, role;

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
        swMultiUser = v.findViewById(R.id.swMultiUser);
        name = v.findViewById(R.id.tvName);
        contact = v.findViewById(R.id.tvContactNumber);
        role = v.findViewById(R.id.tvRole);
        if (ConfigDataProvider.INSTANCE.getUserDetails() != null)
            userData = ConfigDataProvider.INSTANCE.getUserDetails().getData().get(0);
        name.setText(userData.getUsername());
        contact.setText(userData.getMobileno());
        role.setText(userData.getUserrole());
        tvTrialActive = v.findViewById(R.id.tvTrialActive);
        tvSubDays = v.findViewById(R.id.tvSubDays);
        tvSubDate = v.findViewById(R.id.tvSubDate);

        if (!subscription.isEmpty()) {
            SubscriptionForBusiness subForBusiness = subscription.get(0); // Assuming you want data from the first item
            // Assuming SubForBusiness has properties like subscriptionDays and subscriptionDate
            double remainingDaysDouble = (double) subForBusiness.getRemainingDays();
            int remainingDays = (int) remainingDaysDouble;
            String subscriptionDays = String.valueOf(remainingDays);

            String subscriptionDate = subForBusiness.getEndDate();
            // Trim the string to remove potential whitespaces
            subscriptionDate = subscriptionDate.trim();

            String formattedDate = formatDateFromApi(subscriptionDate);
            // Concatenate the message with the formatted date
            String subscriptionMessage = "Subscription Till " + formattedDate;

            // Concatenate the message with the remaining days and formatted date
            String subscriptionMessageForDays = subscriptionDays + " Days Remaining for Renewal.";


            tvSubDays.setText(subscriptionMessageForDays);
            tvSubDate.setText(subscriptionMessage);
        } else {
            tvSubDays.setText("No subscription data available");
            tvSubDate.setText("No subscription data available"); // You can set a default value or an empty string
        }


        fabAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ProfileFragment.this)
                        .cropSquare()
                        .crop()//Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
                // After user selects an image, update the profilePictureBitmap

            }
        });
        swMultiUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showDialog();
                } else {
                }
            }
        });
        cvBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BusinessDetailsActivity.class);
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

    private String formatDateFromApi(String subscriptionDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date = inputFormat.parse(subscriptionDate);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return subscriptionDate; // Return the original date string if parsing fails
        }
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


    public void setAppConfigList(ArrayList<AppConfigDataItems> appConfigList) {
        this.appConfigList = appConfigList;
    }

    public void setSubscriptionList(ArrayList<SubscriptionDataItem> subscriptionList) {
        this.subscriptionList = subscriptionList;
    }

    public void setQrCode(String base64Code) {
        this.base64Code = base64Code;
    }

    public void setSubscription(ArrayList<SubscriptionForBusiness> subscription) {
        this.subscription = subscription;
    }

    public void setBusinessdetails(BusinessDetails businessDetails) {
        this.businessDetails=businessDetails;
    }
}