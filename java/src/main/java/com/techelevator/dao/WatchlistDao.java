package com.techelevator.dao;

import com.techelevator.model.*;

import java.util.List;

public interface WatchlistDao {


    List<Item> getWatchlist(int userId);

    int addToWatchlist(int userId, int itemId);

    int removeFromWatchlist(int userId, int item_id);

}
