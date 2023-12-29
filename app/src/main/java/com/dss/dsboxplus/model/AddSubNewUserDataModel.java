package com.dss.dsboxplus.model;

public class AddSubNewUserDataModel {
    private String username;
    private String contact;

    public AddSubNewUserDataModel(String username, String contact) {
        this.username = username;
        this.contact = contact;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
