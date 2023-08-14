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
import java.math.BigDecimal;
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

    public List<Bid> getBidsOfItem(int itemId) {
        List<Bid> itemBids = new ArrayList<>();
        //String sql = "SELECT bid_id, item_id, user_id, bid_amount, bid_time " +
        //        "FROM bid WHERE item_id = ? ORDER BY bid_amount DESC LIMIT 50;";
        String sql = "SELECT b.bid_id, b.item_id, b.user_id, b.bid_amount, b.bid_time, u.username " +
                     "FROM bid b LEFT JOIN users u ON b.user_id = u.user_id " +
                     "WHERE b.item_id = ? ORDER BY bid_amount DESC LIMIT 50;";
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

    public List<Bid> getBidsOfAuction(int auctionId) {
        /*
        SELECT * FROM auction LEFT JOIN item i ON auction.auction_id = i.auction_id
        LEFT JOIN bid b ON i.item_id = b.item_id WHERE auction.auction_id = 1;
        */
        String sql = "SELECT * FROM auction LEFT JOIN item i ON auction.auction_id = i.auction_id " +
                     "LEFT JOIN bid b ON i.item_id = b.item_id LEFT JOIN users u ON b.user_id = u.user_id " +
                     "WHERE auction.auction_id = ?;";
        List <Bid> bids;
        Map<Integer, Bid> highestBidMap = new HashMap<>();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, auctionId);
            while (results.next()) {
                // bids.add(mapRowToBid(results)); // OLD don't use this pls thanks
                Bid b = mapRowToBid(results);
                if (!highestBidMap.containsKey(b.getBidItem().getItemId())) {
                    highestBidMap.put(b.getBidItem().getItemId(), b);
                } else {
                    // Only update if price is higher :)
                    // b.getBidAmount(); // this bid's price
                    // highestBidMap.get(b.getBidId()).getBidAmount(); // stored bid's price
                    if (b.getBidAmount().compareTo(highestBidMap.get(b.getBidItem().getItemId()).getBidAmount()) >= 0) {
                        // this bid's price is higher so replace
                        highestBidMap.put(b.getBidItem().getItemId(), b);
                    }
                }
            }
            /*
            for (Map.Entry<Integer, Bid> x : highestBidMap.entrySet()) {
                bids.add(x.getValue());
            }
            */
            bids = new ArrayList<>(highestBidMap.values());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return bids;
    }

    public int getUserId(String username) {
        int id = 0;
        String sql = "SELECT user_id FROM users WHERE username = ?;";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
            if (result.next()) {
                return result.getInt("user_id");
            }
        } catch (EmptyResultDataAccessException e) {
            return -1;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return 0;
    }

    public Item getItemFromId(int id) {
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

    public Bid getHighestBidOfItem(int itemId) {
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
        return createBid(bid.getBidItem().getItemId(), bid.getUserId(), bid.getBidAmount());
    }

    public Bid createBid(int itemId, long userId, BigDecimal bidAmount) {
        // TODO: Add validation cause I'm too lazy rn
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
        bid.setBidTime(rowSet.getDate("bid_time"));
        bid.setBidAmount(rowSet.getBigDecimal("bid_amount"));
        bid.setUserId(rowSet.getInt("user_id"));
        if (rowSet.getString("username") != null && !rowSet.getString("username").trim().isEmpty()) {
            bid.setUsername(rowSet.getString("username"));
        }

        return bid;
    }
}
