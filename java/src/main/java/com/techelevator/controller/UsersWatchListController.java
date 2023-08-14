package com.techelevator.controller;


import com.techelevator.dao.UsersWatchListDao;
import com.techelevator.model.UsersWatchList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
public class UsersWatchListController {

    private UsersWatchListDao usersWatchListDao;

    @Autowired
    public UsersWatchListController(UsersWatchListDao usersWatchListDao){
        this.usersWatchListDao = usersWatchListDao;
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("permitAll")
    @RequestMapping(path = "/profile/userswatchlist", method = RequestMethod.GET)
    public List<UsersWatchList> getUsersWatchList(){
        return usersWatchListDao.getUsersWatchList();
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("permitAll")
    @PostMapping(path = "/profile/userswatchlist")
    public UsersWatchList addUsersWatchList(@RequestBody UsersWatchList newUsersWatchList){
        return usersWatchListDao.createUsersWatchList(newUsersWatchList);
    }

}
