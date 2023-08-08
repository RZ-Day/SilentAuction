package com.techelevator.dao;
import com.techelevator.model.Auction;
import com.techelevator.model.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.lang.reflect.Array;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcAuctionDao implements AuctionDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcAuctionDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Auction> getAuctions() {
        Map<Long, Auction> auctionMap = new HashMap<>(); // Map to store auctions by ID
        String sql = "SELECT\n" +
                "    a.auction_id,\n" +
                "    a.auction_name,\n" +
                "    a.start_time,\n" +
                "    a.end_time,\n" +
                "    i.item_id,\n" +
                "    i.item_name,\n" +
                "    i.description,\n" +
                "    i.initial_price,\n" +
                "    i.current_price\n" +
                "FROM\n" +
                "    auction a\n" +
                "LEFT JOIN\n" +
                "    item i ON a.auction_id = i.auction_id\n" +
                "ORDER BY\n" +
                "    a.auction_id, i.item_id;\n";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while (results.next()) {
            long auctionId = results.getLong("auction_id");
            Auction auction = auctionMap.get(auctionId);
            if (auction == null) {
                auction = mapRowToAuction(results);
                auction.setItems(new ArrayList<>());
                auctionMap.put(auctionId, auction);
            }
            long itemId = results.getLong("item_id");
            if (itemId != 0) {
                Item item = mapRowToItem(results);
                auction.getItems().add(item);
            }
        }

        return new ArrayList<>(auctionMap.values());
    }

    private Auction mapRowToAuction(SqlRowSet rowSet) {
        Auction auction = new Auction();
        auction.setAuctionId(rowSet.getInt("auction_id"));
        auction.setAuctionName(rowSet.getString("auction_name"));
        auction.setStartTime(rowSet.getTimestamp("start_time"));
        auction.setEndTime(rowSet.getTimestamp("end_time"));
        return auction;
    }

    private Item mapRowToItem(SqlRowSet rowSet) {
        Item item = new Item();
        item.setItemId(rowSet.getInt("item_id"));
        item.setItemName(rowSet.getString("item_name"));
        item.setDescription(rowSet.getString("description"));
        item.setInitialPrice(rowSet.getDouble("initial_price"));
        item.setCurrentPrice(rowSet.getDouble("current_price"));
        return item;
    }
}
