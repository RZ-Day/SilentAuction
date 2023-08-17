package com.techelevator.dao;

import com.techelevator.model.UserProfile;

public interface UserProfileDao {

    UserProfile getUserProfileById(int id);
    UserProfile getUserProfileByUsername(String username);
    UserProfile updateUserProfile(UserProfile userProfile);
}
