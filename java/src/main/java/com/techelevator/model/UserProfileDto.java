package com.techelevator.model;

import javax.validation.constraints.NotEmpty;

public class UserProfileDto {
    private int userId;

    private String fullName;

    @NotEmpty
    private String username;

    private String email;

    private String phone;

    private String billingAddress;
    private String shippingAddress;

    private boolean allowAnonymous;

    public int getUserId() {
        return userId;
    }
    public void setUserId(int id) {
        this.userId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public boolean isAllowAnonymous() {
        return this.allowAnonymous;
    }

    public void setAllowAnonymous(boolean allow) {
        this.allowAnonymous = allow;
    }
}
