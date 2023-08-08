package com.techelevator.model;

import java.util.Date;
import java.util.List;

public class Auction {
    private int auctionId;
    private String auctionName;
    private Date startTime;
    private Date endTime;
    private List<Item> items; // Assuming you have an Item class


    public int getAuctionId() {
        return auctionId;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
    }
}
