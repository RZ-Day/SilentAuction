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
    public void updateUserContactInformation (String username, String contactInformation) {
        userDao.updateUserContactInformation(username, contactInformation);
    }

    @Override
    public void updateAllowAnonymous(String username, boolean allowAnonymous) {
        userDao.updateAllowAnonymous(username, allowAnonymous);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }
}
