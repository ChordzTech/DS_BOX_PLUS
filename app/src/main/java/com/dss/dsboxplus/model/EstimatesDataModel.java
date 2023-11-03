package com.dss.dsboxplus.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class EstimatesDataModel implements Parcelable {
    String imageURL;
    String nameOfBox;
    String nameofClients;
    String dimensionOfBox;
    String costOfBox;

    public EstimatesDataModel(String imageURL, String nameOfBox, String nameofClients, String dimensionOfBox, String costOfBox) {
        this.imageURL = imageURL;
        this.nameOfBox = nameOfBox;
        this.nameofClients = nameofClients;
        this.dimensionOfBox = dimensionOfBox;
        this.costOfBox = costOfBox;
    }

    protected EstimatesDataModel(Parcel in) {
        imageURL = in.readString();
        nameOfBox = in.readString();
        nameofClients = in.readString();
        dimensionOfBox = in.readString();
        costOfBox = in.readString();
    }

    public static final Creator<EstimatesDataModel> CREATOR = new Creator<EstimatesDataModel>() {
        @Override
        public EstimatesDataModel createFromParcel(Parcel in) {
            return new EstimatesDataModel(in);
        }

        @Override
        public EstimatesDataModel[] newArray(int size) {
            return new EstimatesDataModel[size];
        }
    };

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getNameOfBox() {
        return nameOfBox;
    }

    public void setNameOfBox(String nameOfBox) {
        this.nameOfBox = nameOfBox;
    }

    public String getNameofClients() {
        return nameofClients;
    }

    public void setNameofClients(String nameofClients) {
        this.nameofClients = nameofClients;
    }

    public String getDimensionOfBox() {
        return dimensionOfBox;
    }

    public void setDimensionOfBox(String dimensionOfBox) {
        this.dimensionOfBox = dimensionOfBox;
    }

    public String getCostOfBox() {
        return costOfBox;
    }

    public void setCostOfBox(String costOfBox) {
        this.costOfBox = costOfBox;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(imageURL);
        parcel.writeString(nameOfBox);
        parcel.writeString(nameofClients);
        parcel.writeString(dimensionOfBox);
        parcel.writeString(costOfBox);
    }
}
