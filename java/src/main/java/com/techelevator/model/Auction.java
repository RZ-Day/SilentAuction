package com.techelevator.model;

import java.util.Date;
import java.util.List;

public class Auction {
    private int auctionId;
    private String auctionName;
    private Date startTime;
    private Date endTime;
    private boolean isPrivate;
    private int privateKey;
    private List<Item> items;



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

    public boolean isPrivate() {
        return isPrivate;
    }

    public int getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(int privateKey) {
        this.privateKey = privateKey;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
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
