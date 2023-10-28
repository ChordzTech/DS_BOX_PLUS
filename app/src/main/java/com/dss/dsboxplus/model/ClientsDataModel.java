package com.dss.dsboxplus.model;

public class ClientsDataModel {
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
}
