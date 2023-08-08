package com.techelevator.controller;

import com.techelevator.model.Auction;
import com.techelevator.dao.AuctionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class AuctionController {

    private final AuctionDao auctionDao;

    @Autowired
    public AuctionController(AuctionDao auctionDao) {
        this.auctionDao = auctionDao;
    }


    @GetMapping("/auctions")
    public List<Auction> listAuctions() {
        return auctionDao.getAuctions();
    }
}

