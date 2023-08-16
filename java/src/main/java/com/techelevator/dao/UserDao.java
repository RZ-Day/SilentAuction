package com.techelevator.dao;

import com.techelevator.model.RegisterUserDto;
import com.techelevator.model.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User getUserById(int id);

    User getUserByUsername(String username);

    User createUser(RegisterUserDto user);

    void updateUserBillingAddress(String username, String billingAddress);
    void updateUserShippingAddress(String username, String shippingAddress);
    void updateUserEmail(String username, String email);
    void updateUserPhone(String username, String phone);

    void updateAllowAnonymous(String username, boolean allowAnonymous);
}
