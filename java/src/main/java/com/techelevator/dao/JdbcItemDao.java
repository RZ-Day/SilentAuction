package com.techelevator.dao;


import com.techelevator.model.Bid;
import com.techelevator.model.Item;
import com.techelevator.model.Watchlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcItemDao implements ItemDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcItemDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Item getItemById(int itemId) {
        String sql = "SELECT\n" +
                "    i.item_id,\n" +
                "    i.item_name,\n" +
                "    i.description,\n" +
                "    i.initial_price,\n" +
                "    i.current_price,\n" +
                "    b.bid_id,\n" +
                "    b.user_id AS bid_user_id,\n" +
                "    b.bid_amount,\n" +
                "    b.bid_time\n" +
                "FROM\n" +
                "    item i\n" +
                "LEFT JOIN\n" +
                "    bid b ON i.item_id = b.item_id\n" +
                "WHERE\n" +
                "    i.item_id = ?\n" +
                "ORDER BY\n" +
                "    i.item_id, b.bid_time;\n";

        RowMapper<Item> rowMapper = (resultSet, rowNum) -> createItemFromResultSet(resultSet);

        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, itemId);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    private Item createItemFromResultSet(ResultSet resultSet) throws SQLException {
        Item item = new Item();
        item.setItemId(resultSet.getInt("item_id"));
        item.setItemName(resultSet.getString("item_name"));
        item.setDescription(resultSet.getString("description"));
        item.setInitialPrice(resultSet.getDouble("initial_price"));
        item.setCurrentPrice(resultSet.getDouble("current_price"));


        List<Bid> bids = new ArrayList<>();
        do {
            Bid bid = mapRowToBid((SqlRowSet) resultSet);
            if (bid != null) {
                bids.add(bid);
            }
        } while (resultSet.next() && resultSet.getInt("item_id") == item.getItemId());
        item.setBids(bids);

        return item;
    }

    private Bid mapRowToBid(SqlRowSet rowSet) {
        Bid bid = new Bid();
        int bidId = rowSet.getInt("bid_id");
        if (bidId != 0) {
            bid.setBidId(bidId);
            bid.setUserId(rowSet.getInt("bid_user_id"));
            bid.setBidAmount(rowSet.getBigDecimal("bid_amount"));
            bid.setBidTime(rowSet.getDate("bid_time"));
            return bid;
        } else {
            return null;
        }
    }

    @Override
    public List<Item> getItemsByIdsFromWatchlist(List<Watchlist> watchlistEntries) {
        List<Integer> itemIds = new ArrayList<>();
        for (Watchlist entry : watchlistEntries) {
            itemIds.add(entry.getItemId());
        }

        String sql = "SELECT\n" +
                "    i.item_id,\n" +
                "    i.item_name,\n" +
                "    i.description,\n" +
                "    i.initial_price,\n" +
                "    i.current_price,\n" +
                "    b.bid_id,\n" +
                "    b.user_id AS bid_user_id,\n" +
                "    b.bid_amount,\n" +
                "    b.bid_time\n" +
                "FROM\n" +
                "    item i\n" +
                "LEFT JOIN\n" +
                "    bid b ON i.item_id = b.item_id\n" +
                "WHERE\n" +
                "    i.item_id IN (:itemIds)\n" +  // Using IN clause to retrieve items by multiple IDs
                "ORDER BY\n" +
                "    i.item_id, b.bid_time;\n";

        SqlParameterSource namedParameters = new MapSqlParameterSource("itemIds", itemIds);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

        RowMapper<Item> rowMapper = (resultSet, rowNum) -> createItemFromResultSet(resultSet);
        return namedParameterJdbcTemplate.query(sql, namedParameters, rowMapper);
    }
}
