package com.techelevator.controller;

import com.techelevator.dao.WatchlistDao;
import com.techelevator.model.Item;
import com.techelevator.model.User;
import com.techelevator.model.Watchlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WatchlistController {

    private final WatchlistDao watchlistDao;

    @Autowired
    public WatchlistController(WatchlistDao watchlistDao) {
        this.watchlistDao = watchlistDao;
    }

    @CrossOrigin
    @GetMapping("/profile/watchlist/{userId}")
    public List<Item> listWatchlist(@PathVariable int userId) {
        return watchlistDao.getWatchlist(userId);
    }

    @CrossOrigin
    @PostMapping("/profile/watchlist/add/{userId}/{itemId}")
    public int addToWatchlist(@PathVariable int userId, @PathVariable int itemId) {
        return watchlistDao.addToWatchlist(userId, itemId);
    }

    @CrossOrigin
    @DeleteMapping("/profile/watchlist/delete/{userId}/{itemId}")
    public int deleteFromWatchlist(@PathVariable int userId, @PathVariable int itemId) {
        return watchlistDao.removeFromWatchlist(userId, itemId);
    }

}
