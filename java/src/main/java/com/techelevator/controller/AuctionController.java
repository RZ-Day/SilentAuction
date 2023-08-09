package com.techelevator.controller;

import com.techelevator.model.Auction;
import com.techelevator.dao.AuctionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AuctionController {

    private final AuctionDao auctionDao;

    @Autowired
    public AuctionController(AuctionDao auctionDao) {
        this.auctionDao = auctionDao;
    }

    @CrossOrigin
    @GetMapping("/auctions")
    public List<Auction> listAuctions() {
        return auctionDao.getAuctions();
    }

    @CrossOrigin
    @PostMapping("/create/auction")
    public Auction addAuction(@RequestBody Auction auction) {
        return auctionDao.createAuction(auction);
    }

}

