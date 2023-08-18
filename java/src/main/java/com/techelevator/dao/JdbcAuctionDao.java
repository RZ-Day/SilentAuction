package com.techelevator.dao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Blob;
import java.util.*;

@Repository
public class JdbcAuctionDao implements AuctionDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcAuctionDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Auction> getAuctions() {
        Map<Long, Auction> auctionMap = new HashMap<>();
        String sql = "SELECT\n" +
                "    a.auction_id,\n" +
                "    a.auction_name,\n" +
                "    a.start_time,\n" +
                "    a.end_time,\n" +
                "    a.isPrivate,\n" +
                "    a.privateKey,\n" +
                "    i.item_id,\n" +
                "    i.auction_id AS item_auction_id,\n" +
                "    i.user_id AS item_user_id,\n" +
                "    i.item_name,\n" +
                "    i.description,\n" +
                "    i.initial_price,\n" +
                "    i.current_price,\n" +
                "    b.bid_id,\n" +
                "    b.user_id AS bid_user_id,\n" +
                "    b.bid_amount,\n" +
                "    b.bid_time\n" +
                "FROM\n" +
                "    auction a\n" +
                "LEFT JOIN\n" +
                "    item i ON a.auction_id = i.auction_id\n" +
                "LEFT JOIN\n" +
                "    bid b ON i.item_id = b.item_id\n" +
                "ORDER BY\n" +
                "    a.auction_id, i.item_id, b.bid_time;\n";
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
                Bid bid = mapRowToBid(results);
                if (bid != null) {
                    item.setBids(new ArrayList<>());
                    item.getBids().add(bid);
                }
                auction.getItems().add(item);
            }
        }

        return new ArrayList<>(auctionMap.values());
    }

    @Override
    public Auction createAuction(Auction auction) {
        String auctionSql = "INSERT INTO auction (auction_name, start_time, end_time, isPrivate, privateKey) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING auction_id;";

        try {
            Integer newAuctionId = jdbcTemplate.queryForObject(
                    auctionSql,
                    int.class,
                    auction.getAuctionName(),
                    auction.getStartTime(),
                    auction.getEndTime(),
                    auction.isPrivate(),
                    auction.getPrivateKey()

            );
            if (newAuctionId != null) {
                // insert  items
                for (Item item : auction.getItems()) {
                    String itemSql = "INSERT INTO item (auction_id, item_name, description, initial_price, current_price, user_id) " +
                            "VALUES (?, ?, ?, ?, ?, ?);";
                    jdbcTemplate.update(
                            itemSql,
                            newAuctionId,
                            item.getItemName(),
                            item.getDescription(),
                            item.getInitialPrice(),
                            item.getCurrentPrice(),
                            //Added userId ==========================
                            item.getUserId()
                            // ======================================
                    );
                }
            } else {
                System.out.println("Incoming auction was null! Failed to create auction");
                return null;
            }

            return getAuctionById(newAuctionId);
        } catch (CannotGetJdbcConnectionException e) {
            System.out.println("FUCK!" + e.getMessage());
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Fuck2" + e.getMessage());
            throw new DaoException("Data integrity violation", e);
        } catch (NullPointerException e) {
            throw new DaoException("NullPointer violation", e);

        }
    }





    @Override
    public Auction getAuctionById(int auctionId) {
        String auctionSql = "SELECT * FROM auction WHERE auction_id = ?;";

        try {
            Auction auction = jdbcTemplate.queryForObject(
                    auctionSql,
                    (rs, rowNum) -> {
                        Auction a = new Auction();
                        a.setAuctionId(rs.getInt("auction_id"));
                        a.setAuctionName(rs.getString("auction_name"));
                        a.setStartTime(rs.getTimestamp("start_time"));
                        a.setEndTime(rs.getTimestamp("end_time"));
                        return a;
                    },
                    auctionId
            );

            String itemsSql = "SELECT * FROM item WHERE auction_id = ?;";
            List<Item> items = jdbcTemplate.query(
                    itemsSql,
                    (rs, rowNum) -> {
                        Item item = new Item();
                        item.setItemId(rs.getInt("item_id"));
                        item.setItemName(rs.getString("item_name"));
                        item.setDescription(rs.getString("description"));
                        item.setInitialPrice(rs.getDouble("initial_price"));
                        item.setCurrentPrice(rs.getDouble("current_price"));

                        return item;
                    },
                    auctionId
            );

            assert auction != null;
            auction.setItems(items);

            return auction;
        } catch (EmptyResultDataAccessException e) {
            return null; // Auction not found
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
    }

    @Override
    public Auction getAuctionByPrivateKey(String privateKey) {
        String sql = "SELECT * FROM auction WHERE privatekey LIKE ?";
        String searchTerm = "%" + privateKey + "%";

        Auction auction = jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> {
                    Auction a = new Auction();
                    a.setAuctionId(rs.getInt("auction_id"));
                    a.setAuctionName(rs.getString("auction_name"));
                    a.setStartTime(rs.getTimestamp("start_time"));
                    a.setEndTime(rs.getTimestamp("end_time"));
                    a.setPrivate(rs.getBoolean("isprivate"));
                    a.setPrivateKey(rs.getString("privatekey"));
                    return a;
                },
                searchTerm
        );

        String itemsSql = "SELECT * FROM item WHERE auction_id = ?;";
        List<Item> items = jdbcTemplate.query(
                itemsSql,
                (rs, rowNum) -> {
                    Item item = new Item();
                    item.setItemId(rs.getInt("item_id"));
                    item.setItemName(rs.getString("item_name"));
                    item.setDescription(rs.getString("description"));
                    item.setInitialPrice(rs.getDouble("initial_price"));
                    item.setCurrentPrice(rs.getDouble("current_price"));

                    return item;
                },
                auction.getAuctionId()
        );

        assert auction != null;
        auction.setItems(items);

        return auction;

    }

    @Override
    public int deleteAuctionById(int auctionId) {
        String deleteBids = "DELETE FROM bid WHERE item_id IN (\n" +
                "    SELECT i.item_id\n" +
                "    FROM item i\n" +
                "    WHERE i.auction_id = ?\n" +
                ");";
        String deleteMessages = "DELETE FROM messages WHERE conversation_id IN (\n" +
                "SELECT c.conversation_id\n" +
                "FROM conversations c\n" +
                "JOIN item i ON c.item_id = i.item_id\n" +
                "WHERE i.auction_id = ?\n" +
                ");";
        String deleteConversations = "DELETE FROM conversations WHERE item_id IN (\n" +
                "SELECT i.item_id\n" +
                "FROM item i\n" +
                "WHERE i.auction_id = ?\n" +
                ");";
        String deleteWatchList = "DELETE FROM watchlist WHERE item_id IN (\n" +
                "SELECT item_id from item WHERE auction_id = ?\n" +
                ");";
        String deleteItems = "DELETE FROM item WHERE auction_id = ?;";
        String deleteAuction = "DELETE FROM auction WHERE auction_id = ?;";

        jdbcTemplate.update(deleteBids, auctionId);
        jdbcTemplate.update(deleteMessages, auctionId);
        jdbcTemplate.update(deleteConversations, auctionId);
        jdbcTemplate.update(deleteWatchList, auctionId);
        jdbcTemplate.update(deleteItems, auctionId);
        return jdbcTemplate.update(deleteAuction, auctionId);
    }


    private Auction mapRowToAuction(SqlRowSet rowSet) {
        Auction auction = new Auction();
        auction.setAuctionId(rowSet.getInt("auction_id"));
        auction.setAuctionName(rowSet.getString("auction_name"));
        auction.setStartTime(rowSet.getTimestamp("start_time"));
        auction.setEndTime(rowSet.getTimestamp("end_time"));
        auction.setPrivate(rowSet.getBoolean("isprivate"));
        auction.setPrivateKey(rowSet.getString("privatekey"));
        return auction;
    }

    private Item mapRowToItem(SqlRowSet rowSet) {
        Item item = new Item();
        item.setItemId(rowSet.getInt("item_id"));
        item.setItemName(rowSet.getString("item_name"));
        item.setDescription(rowSet.getString("description"));
        item.setInitialPrice(rowSet.getDouble("initial_price"));
        item.setCurrentPrice(rowSet.getDouble("current_price"));
        item.setUserId(rowSet.getInt("item_user_id"));
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
}


