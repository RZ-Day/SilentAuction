package com.techelevator.dao;

import com.techelevator.model.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcItemDao implements ItemDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Item getItemById(int itemId) {
        String sql = "SELECT * FROM item WHERE item_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, itemId);

        Item foundItem = null;

        if (result.next()) {
            foundItem = mapRowToItem(result);
        }

        return foundItem;
    }

    private Item mapRowToItem(SqlRowSet rowSet) {
        Item item = new Item();
        item.setItemId(rowSet.getInt("item_id"));
        item.setItemName(rowSet.getString("item_name"));
        item.setDescription(rowSet.getString("description"));
        item.setInitialPrice(rowSet.getDouble("initial_price"));
        item.setCurrentPrice(rowSet.getDouble("current_price"));
        item.setUserId(rowSet.getInt("user_id"));
        return item;
    }

}
