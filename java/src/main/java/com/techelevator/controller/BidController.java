package com.techelevator.controller;

import com.techelevator.dao.BidDao;
import com.techelevator.model.Auction;
import com.techelevator.model.NewBidDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.techelevator.model.Bid;

import java.util.List;

@RestController
@CrossOrigin (origins = "http://localhost:8080")
public class BidController {
    private final BidDao bidDao;

    @Autowired
    public BidController(BidDao bidDao) {
        this.bidDao = bidDao;
    }

    @GetMapping("/bids/{id}/highest")
    public Bid highestBid(@PathVariable int id) {
        return bidDao.getHighestBidOfItem(id);
    }

    @GetMapping("/auctions/{id}/currentbids")
    public List<Bid> getBidsOfAuction(@PathVariable int id) {
        return bidDao.getBidsOfAuction(id);
    }

    @GetMapping("/bids/{id}")
    public List<Bid> allBids(@PathVariable int id) {
        return bidDao.getBidsOfItem(id);
    }

    @ResponseStatus(HttpStatus.CREATED) // 201
    @PutMapping("/bids/create")
    public Bid createBid(@RequestBody NewBidDto bid) {
        return bidDao.createBid(bid.getItemId(), bidDao.getUserId(bid.getUsername()), bid.getBidAmount());
    }
}
