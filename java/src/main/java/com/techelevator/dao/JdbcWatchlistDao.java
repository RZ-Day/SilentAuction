package com.techelevator.dao;

import com.techelevator.model.Item;
import com.techelevator.model.User;
import com.techelevator.model.Watchlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcWatchlistDao implements WatchlistDao {

    private JdbcTemplate jdbcTemplate;
    private ItemDao itemDao;

    @Autowired
    public JdbcWatchlistDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Item> getWatchlist(int userId) {
        String sql = "SELECT\n" +
                "    i.item_id,\n" +
                "    i.auction_id,\n" +
                "    i.user_id,\n" +
                "    i.item_name,\n" +
                "    i.description,\n" +
                "    i.initial_price,\n" +
                "    i.current_price\n" +
                "FROM\n" +
                "    item i\n" +
                "JOIN\n" +
                "    watchlist w ON i.item_id = w.item_id\n" +
                "WHERE\n" +
                "    w.user_id = ?;";

        List<Item> items = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Item item = new Item();
                    item.setItemId(rs.getInt("item_id"));
                    item.setItemName(rs.getString("item_name"));
                    item.setDescription(rs.getString("description"));
                    item.setInitialPrice(rs.getDouble("initial_price"));
                    item.setCurrentPrice(rs.getDouble("current_price"));

                    return item;
                },
                userId
        );


        return items;
    }



    @Override
    public int addToWatchlist(int userId, int itemId) {
        String sql = "INSERT INTO watchlist (user_id, item_id) VALUES (?, ?);";

        return jdbcTemplate.queryForObject(sql, Integer.class, userId, itemId);

    }

    @Override
    public Watchlist removeFromWatchlist() {
        return null;
    }



}
