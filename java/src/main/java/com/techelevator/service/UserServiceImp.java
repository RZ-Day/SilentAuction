package com.techelevator.service;

import com.techelevator.dao.UserDao;
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
    public void updateContactInformation (String username, String contactInformation) {
        userDao.updateContactInformation(username, contactInformation);
    }

    @Override
    public void updateAllowAnonymous(String username, boolean allowAnonymous) {
        userDao.updateAllowAnonymous(username, allowAnonymous);
    }
}
