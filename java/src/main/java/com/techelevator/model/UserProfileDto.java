package com.techelevator.model;

import javax.validation.constraints.NotEmpty;

public class UserProfileDto {
    @NotEmpty
    private String username;

    private String email;

    private String phone;

    private String address;

    private boolean allowAnonymous;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAllowAnonymous() {
        return this.allowAnonymous;
    }

    public void setAllowAnonymous(boolean allow) {
        this.allowAnonymous = allow;
    }
}
