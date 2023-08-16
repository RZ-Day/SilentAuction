package com.techelevator.dao;

import com.techelevator.model.Bid;
import com.techelevator.model.Item;
import com.techelevator.model.Auction;
import com.techelevator.model.User;

import java.math.BigDecimal;
import java.util.List;
public interface BidDao {
    Bid getBidById(int bidId);

    List<Bid> getBidsOfItem(Item item);
    List<Bid> getBidsOfItem(int itemId);
    List<Bid> getBidsOfUser(User user);

    List<Bid> getBidsOfAuction(int auctionId);

    Bid getHighestBidOfItem(Item item);
    Bid getHighestBidOfItem(int itemId);

    int getUserId(String username);
    Item getItemFromId(int itemId);

    Bid createBid(Bid bid);
    Bid createBid(int itemId, long userId, BigDecimal bidAmount);
}
