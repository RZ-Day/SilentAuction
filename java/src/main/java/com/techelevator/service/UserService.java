package com.techelevator.service;

import com.techelevator.model.User;

public interface UserService {
    void updateAllowAnonymous(String username, boolean allowAnonymous);

    User getUserByUsername(String username);

    void updateUserPhone(String username, String phone);
    void updateUserAddress(String username, String address);
    void updateUserEmail(String username, String email);
}
