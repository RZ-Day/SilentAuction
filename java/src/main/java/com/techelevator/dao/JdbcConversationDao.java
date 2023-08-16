package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcConversationDao implements ConversationDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcConversationDao(DataSource datasource) {
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }
    @Override
    public List<Conversation> getConversationsByUserId(int userId) {
        List<Conversation> conversations = new ArrayList<>();

        String sql = "SELECT c.*, i.* FROM conversations c " +
                "LEFT JOIN item i ON c.item_id = i.item_id " +
                "WHERE c.buyer_id = ? OR c.seller_id = ?" +
                "ORDER BY c.conversation_id";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId, userId);

        while (result.next()) {
            conversations.add(mapRowToConversation(result));
        }

        return conversations;
    }

    @Override
    public Conversation createConversation(Conversation convo) {
        String sql = "INSERT INTO conversations (seller_id, buyer_id, item_id, item_name) " +
                "VALUES (?, ?, ?, ?) RETURNING conversation_id;";

        try {
            Integer newConvoId = jdbcTemplate.queryForObject(
                    sql,
                    Integer.class,
                    convo.getSellerId(),
                    convo.getBuyerId(),
                    convo.getItemId(),
                    convo.getItemName()
            );

            if (newConvoId != null) {
                convo.setConversationId(newConvoId);
                return convo;
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        } catch (NullPointerException e) {
            throw new DaoException("NullPointer violation", e);
        }

        return null;
    }

    public int getConversationId(Conversation convo) {
        String sql = "SELECT conversation_id FROM conversations WHERE buyer_id = ? " +
                "AND seller_id = ? " +
                "AND item_id = ?;";
        try {
            Integer convoId = this.jdbcTemplate.queryForObject(
                    sql,
                    Integer.class,
                    convo.getBuyerId(),
                    convo.getSellerId(),
                    convo.getItemId());

            if (convoId != null) {
                return convoId;
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        } catch (NullPointerException e) {
            throw new DaoException("NullPointer violation", e);
        }

        return 0;
    }

    public Conversation mapRowToConversation(SqlRowSet result) {
        Conversation convo = new Conversation();

        convo.setConversationId(result.getInt("conversation_id"));
        convo.setSellerId(result.getInt("seller_id"));
        convo.setBuyerId(result.getInt("buyer_id"));
        convo.setItemId(result.getInt("item_id"));
        convo.setItemName(result.getString("item_name"));

        return convo;
    }

}
