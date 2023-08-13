package com.techelevator.dao;

import com.techelevator.model.Bid;
import com.techelevator.model.Item;
import com.techelevator.model.Auction;
import com.techelevator.model.User;

import java.util.List;
public interface BidDao {
    Bid getBidById(int bidId);

    List<Bid> getBidsOfItem(Item item);
    List<Bid> getBidsOfUser(User user);

    Bid getHighestBidOfItem(Item item);

    Bid createBid(Bid bid);
    Bid createBid(int itemId, int userId, double bidAmount);
}
