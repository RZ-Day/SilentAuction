package com.techelevator.dao;

import com.techelevator.model.UsersWatchList;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUsersWatchListDao implements UsersWatchListDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcUsersWatchListDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UsersWatchList createUsersWatchList(UsersWatchList newUsersWatchList) {
        return null;
    }

    @Override
    public UsersWatchList createUsersWatchList(int userId, int itemId) {
        if(userId == 0) throw new IllegalArgumentException(("Registration is required."));

        String usersWatchListSql = "INSERT INTO user_auction_watchlist (user_id, item_id) VALUES (?, ?) RETURNING user_id";

        jdbcTemplate.update(usersWatchListSql, userId, itemId);

        UsersWatchList usersWatchList = new UsersWatchList();
        usersWatchList.setUser_id(userId);
        usersWatchList.setItem_id(itemId);
        return usersWatchList;
    }


    @Override
    public List <UsersWatchList> getUsersWatchList() {
        String sql = "SELECT user_id, item_id FROM user_auction_watchlist";
        List<UsersWatchList> usersWatchList = new ArrayList<>();

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            UsersWatchList watchList = mapRowToUsersWatchList(results);
            usersWatchList.add(watchList);
        }
        return usersWatchList;
    }


    private UsersWatchList mapRowToUsersWatchList(SqlRowSet rs){
        UsersWatchList usersWatchList = new UsersWatchList();
        usersWatchList.setUser_id(rs.getInt("user_id"));
        usersWatchList.setItem_id(rs.getInt("item_id"));
        return usersWatchList;

    }
}
