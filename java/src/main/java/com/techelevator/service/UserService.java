package com.techelevator.service;

import com.techelevator.model.User;

public interface UserService {
    void updateUserContactInformation(String username, String contactInformation);
    void updateAllowAnonymous(String username, boolean allowAnonymous);

    User getUserByUsername(String username);
}
