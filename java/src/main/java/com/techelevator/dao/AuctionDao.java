package com.techelevator.dao;

import com.techelevator.model.Auction;

import java.util.List;

public interface AuctionDao {

    List<Auction> getAuctions();

    Auction createAuction(Auction auction);

    Auction getAuctionById(int auctionId);

    Auction getAuctionByPrivateKey(String privateKey);

}
