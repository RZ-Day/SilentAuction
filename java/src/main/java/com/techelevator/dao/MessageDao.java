package com.techelevator.dao;

import com.techelevator.model.Message;

import java.util.List;

public interface MessageDao {
    List<Message> getMessagesByUserId(int userId);

    Message createMessage(Message message);
}
