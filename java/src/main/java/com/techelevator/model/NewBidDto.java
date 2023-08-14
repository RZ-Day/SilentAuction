package com.techelevator.model;

import java.math.BigDecimal;

public class NewBidDto {
    private String username;
    private int itemId;
    private BigDecimal bidAmount;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int id) {
        this.itemId = id;
    }

    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(BigDecimal amount) {
        this.bidAmount = amount;
    }
}
