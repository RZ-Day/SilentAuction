package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UsersWatchList {
    @JsonIgnore
    private int user_id;
    private int item_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }
}
