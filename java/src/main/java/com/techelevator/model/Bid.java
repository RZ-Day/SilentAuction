package com.techelevator.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Bid {
    private long bidId;
    private long userId;
    private BigDecimal bidAmount;
    private Date bidTime;
    private Item bidItem;


    public Bid() {
        // Default constructor
    }

    public Bid(long bidId, long userId, BigDecimal bidAmount, Date bidTime) {
        this.bidId = bidId;
        this.userId = userId;
        this.bidAmount = bidAmount;
        this.bidTime = bidTime;
    }

    public long getBidId() {
        return bidId;
    }

    public void setBidId(long bidId) {
        this.bidId = bidId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    public Item getBidItem() {
        return bidItem;
    }

    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Date getBidTime() {
        return bidTime;
    }

    public void setBidTime(Date bidTime) {
        this.bidTime = bidTime;
    }

    public void setBidItem(Item item) {
        this.bidItem = item;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "bidId=" + bidId +
                ", userId=" + userId +
                ", bidAmount=" + bidAmount +
                ", bidTime=" + bidTime +
                '}';
    }
}
