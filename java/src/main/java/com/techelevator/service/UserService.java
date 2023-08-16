package com.techelevator.service;

import com.techelevator.model.User;

public interface UserService {
    void updateAllowAnonymous(String username, boolean allowAnonymous);

    User getUserByUsername(String username);

    void updateUserPhone(String username, String phone);
    void updateUserBillingAddress(String username, String billingAddress);
    void updateUserShippingAddress(String username, String shippingAddress);
    void updateUserEmail(String username, String email);

}
