package com.techelevator.model;

public class UserContact {
    private String userContactId;

    public String getUserContactId() {
        return userContactId;
    }

    public void setUserContactId(String userContactId) {
        this.userContactId = userContactId;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    private String contactInformation;
}
