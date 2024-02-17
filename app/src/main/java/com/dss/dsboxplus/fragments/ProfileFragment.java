package com.dss.dsboxplus.fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dss.dsboxplus.R;
import com.dss.dsboxplus.alertdialog.DialogUtils;
import com.dss.dsboxplus.alertdialog.SubscriptionViewModel;
import com.dss.dsboxplus.data.configdata.ConfigDataProvider;
import com.dss.dsboxplus.data.repo.response.AppConfigDataItems;
import com.dss.dsboxplus.data.repo.response.AppConfigResponse;
import com.dss.dsboxplus.data.repo.response.BusinessDetails;
import com.dss.dsboxplus.data.repo.response.BusinessDetailsResponse;
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
import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    //    FloatingActionButton fabAddImage;
    ShapeableImageView fabAddImage;
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
    private SubscriptionViewModel viewModel;
    private CardView cvEnableMultiUsers;


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

    private void saveProfilePictureToFile(Bitmap bitmap) {
        // Get the directory for the app's private files
        File filesDir = getActivity().getFilesDir();

        // Create a new file to save the profile picture
        File profilePictureFile = new File(filesDir, "profile_picture.jpg");

        try {
            // Create a FileOutputStream to write the bitmap to the file
            FileOutputStream fos = new FileOutputStream(profilePictureFile);

            // Compress the bitmap to JPEG format with 100% quality
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            // Close the FileOutputStream
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load the profile picture from a file
    public Bitmap loadProfilePictureFromFile() {
        // Get the directory for the app's private files
        File filesDir = getActivity().getFilesDir();

        // Create a file object for the profile picture
        File profilePictureFile = new File(filesDir, "profile_picture.jpg");

        // Check if the file exists
        if (profilePictureFile.exists()) {
            // If the file exists, load the bitmap from the file
            return BitmapFactory.decodeFile(profilePictureFile.getAbsolutePath());
        } else {
            // If the file does not exist, return null
            return null;
        }
    }

    public Bitmap getProfilePictureBitmap() {
        if (isAdded()) {
            // Get the directory for the app's private files
            File filesDir = getActivity().getFilesDir();

            // Create a file object for the profile picture
            File profilePictureFile = new File(filesDir, "profile_picture.jpg");

            // Check if the file exists before decoding into a Bitmap
            if (profilePictureFile.exists()) {
                // Obtain a Bitmap from the profile picture file
                Bitmap profileBitmap = BitmapFactory.decodeFile(profilePictureFile.getAbsolutePath());

                // Handle the case when the decoding fails
                if (profileBitmap == null) {
                    Log.e("ProfileFragment", "Failed to decode profile picture file");
                }

                return profileBitmap;
            } else {
                // Handle the case when the profile picture file does not exist
                Log.e("ProfileFragment", "Profile picture file does not exist");
                return null;
            }
        } else {
            // Handle the case when the fragment is not attached
            Log.e("ProfileFragment", "Fragment is not added");
            return null;
        }
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
        return inflater.inflate(R.layout.fragment_profile_, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        cvBusiness = v.findViewById(R.id.businessSettings);
        cvDefaultPaper = v.findViewById(R.id.cvDefaultPaperSettings);
        cvDefaultRate = v.findViewById(R.id.cvDefaultRateSettings);
        cvEnableMultiUsers = v.findViewById(R.id.cvEnableMultiUsers);
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
        if (userData.getUserrole().equalsIgnoreCase("Admin")) {
            cvsuperUserSettings.setVisibility(View.VISIBLE);
            cvEnableMultiUsers.setVisibility(View.VISIBLE);
        } else {
            cvsuperUserSettings.setVisibility(View.GONE);
            cvEnableMultiUsers.setVisibility(View.GONE);
        }
        viewModel = new ViewModelProvider(requireActivity()).get(SubscriptionViewModel.class);
        //Profile Pic
//        Bitmap savedProfilePicture = ConfigDataProvider.INSTANCE.getProfilePicture();
//        if (savedProfilePicture != null) {
//            ivProfile.setImageBitmap(savedProfilePicture);
//        }


        //new code
        Bitmap savedProfilePicture = loadProfilePictureFromFile();
        if (savedProfilePicture != null) {
            ivProfile.setImageBitmap(savedProfilePicture);
        }

        BusinessDetailsResponse businessDetailsResponse = ConfigDataProvider.INSTANCE.getBusinessDetailsResponse();

        if (businessDetailsResponse != null && businessDetailsResponse.getData() != null) {
            BusinessDetails businessDetails = businessDetailsResponse.getData();
            int multiUserStatus = businessDetails.getMultiuser();
            // Hide the super user setting if userAccess is 0
            if (multiUserStatus == 1) {
                swMultiUser.setChecked(true);
            } else {
                swMultiUser.setChecked(false);
            }
        }
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
            String subStatus = subForBusiness.getStatus();
            // Trim the string to remove potential whitespaces
            subscriptionDate = subscriptionDate.trim();

            String formattedDate = formatDateFromApi(subscriptionDate);
            // Concatenate the message with the formatted date
            String subscriptionMessage = "Subscription Till " + formattedDate;

            // Concatenate the message with the remaining days and formatted date
            String subscriptionMessageForDays = subscriptionDays + " Days Remaining for Renewal.";


            tvSubDays.setText(subscriptionMessageForDays);
            tvSubDate.setText(subscriptionMessage);
            tvTrialActive.setText("Subscription Status: " + subStatus);
            viewModel.setRemainingDays(remainingDays);
        } else {
            tvSubDays.setText("No subscription data available");
            tvSubDate.setText("No subscription data available"); // You can set a default value or an empty string


            //Subscription Status For New Business
            AppConfigResponse appConfigResponse = ConfigDataProvider.INSTANCE.getAppConfigResponse();
            if (appConfigResponse.getData() != null) {
                ArrayList<AppConfigDataItems> appConfigDataItems = appConfigResponse.getData();
                for (AppConfigDataItems appConfigDataItem : appConfigDataItems) {
                    int configId = appConfigDataItem.getConfigid();
                    String configValue = appConfigDataItem.getConfigvalue();
                    if (configId == 26) {
                        int trialDays = Integer.parseInt(configValue);

                        // Get today's date
                        LocalDate currentDate = LocalDate.now();

                        // Calculate trial end date
                        LocalDate trialEndDate = currentDate.plusDays(trialDays);

                        // Print trial end date
                        String suDate = "Subscription Till " + trialEndDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                        // Calculate remaining days
                        int remainingDays = trialDays;

                        // Print remaining days
                        String remainingDaysForSub = remainingDays + " Days Remaining for Renewal.";


                        tvSubDays.setText(remainingDaysForSub);
                        tvSubDate.setText(suDate);
                        viewModel.setRemainingDays(remainingDays);
                    }
                }
            }

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
                BusinessDetailsResponse businessDetailsResponse = ConfigDataProvider.INSTANCE.getBusinessDetailsResponse();

                if (businessDetailsResponse != null && businessDetailsResponse.getData() != null) {
                    BusinessDetails businessDetails = businessDetailsResponse.getData();
                    int multiUserStatus = businessDetails.getMultiuser();
                    // Hide the super user setting if userAccess is 0
                    if (isChecked && multiUserStatus == 1) {
                        swMultiUser.setChecked(true);
                        Toast.makeText(buttonView.getContext(), "Multi User feature is already enabled", Toast.LENGTH_SHORT).show();
                    } else if (isChecked && multiUserStatus != 1) {
                        showDialog();
                    } else if (!isChecked && multiUserStatus == 1) {
                        swMultiUser.setChecked(true);
                        Toast.makeText(buttonView.getContext(), "Please connect Admin to disable Multi User feature", Toast.LENGTH_SHORT).show();
                    } else if (!isChecked && multiUserStatus != 1) {
                        swMultiUser.setChecked(false);
                    }
                }
            }
        });
//        protected void onStop() {
//            super.onStop();
//            // Switch off the button when the activity is stopped
//            swMultiUser.setChecked(false);
//        }
//
//        @Override
//        protected void onPause() {
//            super.onPause();
//            // Switch off the button when the activity is paused
//            swMultiUser.setChecked(false);
//        }
//
//        // Add this method if you want to handle switch state when the activity is destroyed
//        @Override
//        protected void onDestroy() {
//            super.onDestroy();
//            // Switch off the button when the activity is destroyed
//            swMultiUser.setChecked(false);
//        }
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
                if (ConfigDataProvider.INSTANCE.getUserDetails().getData().get(0).getUseraccess() == 2) {
                    Intent intent = new Intent(getActivity(), SuperUserSetting.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), "You don't have access for this feature", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    public void showDialog() {
        DialogUtils.showCustomDialog(getContext(), new DialogUtils.DialogListener() {
            @Override
            public void onPositiveButtonClick() {
                Intent intent = new Intent(getActivity(), SubscriptionActivity.class);
                startActivity(intent);
                BusinessDetailsResponse businessDetailsResponse = ConfigDataProvider.INSTANCE.getBusinessDetailsResponse();
                if (businessDetailsResponse != null && businessDetailsResponse.getData() != null) {
                    BusinessDetails businessDetails = businessDetailsResponse.getData();
                    int userAccess = businessDetails.getMultiuser();
                    // Hide the super user setting if userAccess is 0
                    if (userAccess == 1) {
                        // Enable the toggle button when userAccess is 1
                        swMultiUser.setChecked(true);
                    } else {
                        // Disable the toggle button when userAccess is not 1
                        swMultiUser.setChecked(false);
                    }
                }
            }

            @Override
            public void onNegativeButtonClick() {
                BusinessDetailsResponse businessDetailsResponse = ConfigDataProvider.INSTANCE.getBusinessDetailsResponse();
                if (businessDetailsResponse != null && businessDetailsResponse.getData() != null) {
                    BusinessDetails businessDetails = businessDetailsResponse.getData();
                    int userAccess = businessDetails.getMultiuser();
                    // Hide the super user setting if userAccess is 0
                    if (userAccess == 1) {
                        // Enable the toggle button when userAccess is 1
                        swMultiUser.setChecked(true);
                    } else {
                        // Disable the toggle button when userAccess is not 1
                        swMultiUser.setChecked(false);
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Uri uri = data.getData();
//        ivProfile.setImageURI(uri);

        if (requestCode == ImagePicker.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Uri uri = data.getData();
                ivProfile.setImageURI(uri);
                Bitmap selectedImageBitmap = bitmapConversionMethod(uri);

                // Save the profile picture to the file
                saveProfilePictureToFile(selectedImageBitmap);
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            // Handle the error if needed
            Toast.makeText(getContext(), ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        }

    }


    private Bitmap bitmapConversionMethod(Uri uri) {
        try {
            InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
            return BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
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
        this.businessDetails = businessDetails;
    }
}