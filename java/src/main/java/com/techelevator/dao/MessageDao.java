package com.techelevator.dao;

import com.techelevator.model.Message;

import java.util.List;

public interface MessageDao {
    List<Message> getMessagesByUserId(int convoId);

    Message createMessage(Message message);

//    List<Conversation> getConversationsByUserId(int userId);
//
//    Conversation createConversation(Conversation conversation);
}
