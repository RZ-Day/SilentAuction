package com.techelevator.model;

import java.util.Date;
import java.util.List;

public class Bid {
    private int bidId;
    private Date bidTime;
    private double bidAmount;
    private Item bidItem;
    private int userId;

    public int getBidId() {
        return bidId;
    }

    public Date getBidTimestamp() {
        return bidTime;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public Item getItem() {
        return bidItem;
    }

    public int getUserId() {
        return userId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
    }

    public void setBidTimestamp(Date timestamp) {
        this.bidTime = timestamp;
    }

    public void setBidAmount(double amount) {
        this.bidAmount = amount;
    }

    public void setBidItem(Item item) {
        this.bidItem = item;
    }

    public void setUserId(int id) {
        this.userId = id;
    }
}
