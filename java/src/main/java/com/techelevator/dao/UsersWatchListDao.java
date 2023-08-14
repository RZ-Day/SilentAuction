package com.techelevator.dao;

import com.techelevator.model.UsersWatchList;

import java.util.List;

public interface UsersWatchListDao {
    UsersWatchList createUsersWatchList(UsersWatchList newUsersWatchList);

    UsersWatchList createUsersWatchList(int userId, int itemId);

    List<UsersWatchList> getUsersWatchList();
}
