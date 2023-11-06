package com.dss.dsboxplus.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Comparator;

public class ClientsDataModel implements Parcelable {
    String imageURL;
    String nameOfClient;
    String phoneNumberOfClient;
    String numberOfEstimatesOfClients;

    public ClientsDataModel(String imageURL, String nameOfClient, String phoneNumberOfClient, String numberOfEstimatesOfClients) {
        this.imageURL = imageURL;
        this.nameOfClient = nameOfClient;
        this.phoneNumberOfClient = phoneNumberOfClient;
        this.numberOfEstimatesOfClients = numberOfEstimatesOfClients;
    }

    protected ClientsDataModel(Parcel in) {
        imageURL = in.readString();
        nameOfClient = in.readString();
        phoneNumberOfClient = in.readString();
        numberOfEstimatesOfClients = in.readString();
    }

    public static final Creator<ClientsDataModel> CREATOR = new Creator<ClientsDataModel>() {
        @Override
        public ClientsDataModel createFromParcel(Parcel in) {
            return new ClientsDataModel(in);
        }

        @Override
        public ClientsDataModel[] newArray(int size) {
            return new ClientsDataModel[size];
        }
    };

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getNameOfClient() {
        return nameOfClient;
    }

    public void setNameOfClient(String nameOfClient) {
        this.nameOfClient = nameOfClient;
    }

    public String getPhoneNumberOfClient() {
        return phoneNumberOfClient;
    }

    public void setPhoneNumberOfClient(String phoneNumberOfClient) {
        this.phoneNumberOfClient = phoneNumberOfClient;
    }

    public String getNumberOfEstimatesOfClients() {
        return numberOfEstimatesOfClients;
    }

    public void setNumberOfEstimatesOfClients(String numberOfEstimatesOfClients) {
        this.numberOfEstimatesOfClients = numberOfEstimatesOfClients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(imageURL);
        parcel.writeString(nameOfClient);
        parcel.writeString(phoneNumberOfClient);
        parcel.writeString(numberOfEstimatesOfClients);
    }

  }
