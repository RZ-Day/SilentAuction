package com.techelevator.service;

public interface UserService {
    void updateContactInformation(String username, String contactInformation);
    void updateAllowAnonymous(String username, boolean allowAnonymous);
}
