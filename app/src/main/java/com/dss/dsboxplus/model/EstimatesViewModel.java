package com.dss.dsboxplus.model;

public class EstimatesViewModel {
    String imageURL;
    String nameOfBox;
    String nameofClients;
    String dimensionOfBox;
    String costOfBox;

    public EstimatesViewModel(String imageURL, String nameOfBox, String nameofClients, String dimensionOfBox, String costOfBox) {
        this.imageURL = imageURL;
        this.nameOfBox = nameOfBox;
        this.nameofClients = nameofClients;
        this.dimensionOfBox = dimensionOfBox;
        this.costOfBox = costOfBox;
    }

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
}
