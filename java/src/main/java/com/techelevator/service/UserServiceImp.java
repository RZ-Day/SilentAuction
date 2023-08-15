package com.techelevator.service;

import com.techelevator.dao.UserDao;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void updateAllowAnonymous(String username, boolean allowAnonymous) {
        userDao.updateAllowAnonymous(username, allowAnonymous);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public void updateUserBillingAddress(String username, String billingAddress) {
        userDao.updateUserBillingAddress(username, billingAddress);
    }

    @Override
    public void updateUserShippingAddress(String username, String shippingAddress) {
        userDao.updateUserShippingAddress(username, shippingAddress);
    }

    @Override
    public void updateUserEmail(String username, String email) {
        userDao.updateUserEmail(username, email);
    }

    @Override
    public void updateUserPhone(String username, String phone) {
        userDao.updateUserPhone(username, phone);
    }
}
