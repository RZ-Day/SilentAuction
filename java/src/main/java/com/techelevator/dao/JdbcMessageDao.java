package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcMessageDao implements MessageDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMessageDao(DataSource datasource) {
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public List<Message> getMessagesByUserId(int userId) {
        List<Message> messages = new ArrayList<Message>();

        String sql = "SELECT * FROM messages WHERE from_id = ? OR to_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);

        while(result.next()) {
            messages.add(mapRowToMessage(result));
        }

        return messages;
    }

    @Override
    public Message createMessage(Message message) {

        String sql = "INSERT INTO messages (from_id, to_id, item_id, message_body) " +
                        "VALUES (?, ?, ?, ?) RETURNING message_id;";

        try {
            Integer newMessageId = jdbcTemplate.queryForObject(
                    sql,
                    Integer.class,
                    message.getFromId(),
                    message.getToId(),
                    message.getItemId(),
                    message.getMessageBody()
            );

            if (newMessageId != null) {
                message.setMessageId(newMessageId);
                return message;
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

    public Message mapRowToMessage(SqlRowSet result) {
        Message message = new Message();
        message.setFromId(result.getInt("from_id"));
        message.setToId(result.getInt("to_id"));
        message.setItemId(result.getInt("item_id"));
        message.setMessageBody(result.getString("message_body"));

        return message;
    }
}
