package com.techelevator.dao;

import com.techelevator.model.Conversation;

import java.util.List;

public interface ConversationDao {
    List<Conversation> getConversationsByUserId(int userId);

    Conversation createConversation(Conversation convo);

    int getConversationId(Conversation convo);
}
