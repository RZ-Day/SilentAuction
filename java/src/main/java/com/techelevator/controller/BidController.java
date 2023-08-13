package com.techelevator.controller;

import com.techelevator.dao.BidDao;
import com.techelevator.model.Auction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.techelevator.model.Bid;

import java.util.List;

@RestController
public class BidController {
    private final BidDao bidDao;

    @Autowired
    public BidController(BidDao bidDao) {
        this.bidDao = bidDao;
    }

    @CrossOrigin
    @GetMapping("/bids/{id}/highest")
    public Bid highestBid(@PathVariable int id) {
        return bidDao.getHighestBidOfItem(id);
    }

    @CrossOrigin
    @GetMapping("/auctions/{id}/currentbids")
    public List<Bid> getBidsOfAuction(@PathVariable int id) {
        return bidDao.getBidsOfAuction(id);
    }

    @CrossOrigin
    @GetMapping("/bids/{id}")
    public List<Bid> allBids(@PathVariable int id) {
        return bidDao.getBidsOfItem(id);
    }
}
