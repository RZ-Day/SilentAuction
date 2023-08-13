package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Auction;
import com.techelevator.model.Item;
import com.techelevator.model.Bid;
import com.techelevator.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
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
public class JdbcBidDao implements BidDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcBidDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Bid getBidById(int bidId) {
        Bid bid = null;
        // SELECT bid_id, item_id, user_id, bid_amount, bid_time FROM bid WHERE bid_id = ?;
        String sql = "SELECT bid_id, item_id, user_id, bid_amount, bid_time FROM bid WHERE bid_id = ?;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, bidId);
            if (result.next()) {
                bid = mapRowToBid(result);
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return bid;
    }

    public List<Bid> getBidsOfItem(Item item) {
        return getBidsOfItem(item.getItemId());
    }

    private List<Bid> getBidsOfItem(int itemId) {
        List<Bid> itemBids = new ArrayList<>();
        String sql = "SELECT bid_id, item_id, user_id, bid_amount, bid_time " +
                "FROM bid WHERE item_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, itemId);
            while (results.next()) {
                itemBids.add(mapRowToBid(results));
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return itemBids;
    }

    public List<Bid> getBidsOfUser(User user) {
        return getBidsOfUserId(user.getId());
    }

    private List<Bid> getBidsOfUserId(int userId) {
        List<Bid> userBids = new ArrayList<>();
        String sql = "SELECT bid_id, item_id, user_id, bid_amount, bid_time " +
                "FROM bid WHERE user_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                userBids.add(mapRowToBid(results));
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return userBids;
    }

    private Item getItemFromId(int id) {
        Item item = null;

        // SELECT item_id, item_name, description, initial_price, current_price from item WHERE item_id = ?;
        String sql = "SELECT item_id, item_name, description, initial_price, current_price from item WHERE item_id = ?;";
        try {
            item = new Item();
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
            if (result.next()) {
                item.setItemId(result.getInt("item_id"));
                item.setItemName(result.getString("item_name"));
                item.setDescription(result.getString("description"));
                item.setInitialPrice(result.getDouble("initial_price"));
                item.setCurrentPrice(result.getDouble("current_price"));
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return item;
    }

    public Bid getHighestBidOfItem(Item item) {
        return getHighestBidOfItem(item.getItemId());
    }

    private Bid getHighestBidOfItem(int itemId) {
        Bid bid = new Bid();
        String sql = "SELECT bid_id, item_id, user_id, bid_amount, bid_time FROM bid " +
                     "WHERE item_id = ? ORDER BY bid_amount DESC LIMIT 1;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, itemId);
            if (result.next()) {
                bid = mapRowToBid(result);
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return bid;
    }

    public Bid createBid(Bid bid) {
        return createBid(bid.getItem().getItemId(), bid.getUserId(), bid.getBidAmount());
    }

    public Bid createBid(int itemId, int userId, double bidAmount) {
        Bid bid = null;
        String sql = "INSERT INTO bid (item_id, user_id, bid_amount, bid_time) " +
                     "VALUES (?, ?, ?, now()) RETURNING bid_id;";

        try {
            int resultId = jdbcTemplate.queryForObject(sql, int.class, itemId, userId, bidAmount);
            bid = getBidById(resultId);

            return bid;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(e.getMessage());
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException(e.getMessage());
        }
    }

    private Bid mapRowToBid(SqlRowSet rowSet) {
        Bid bid = new Bid();
        bid.setBidId(rowSet.getInt("bid_id"));
        bid.setBidItem(getItemFromId(rowSet.getInt("item_id")));
        bid.setBidTimestamp(rowSet.getTimestamp("bid_time"));
        bid.setBidAmount(rowSet.getDouble("bid_amount"));
        bid.setUserId(rowSet.getInt("user_id"));
        return bid;
    }
}
